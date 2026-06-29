package com.ftn.sbnz.service;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		System.setProperty("drools.dialect.java.compiler", "NATIVE");
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean
	public KieBase kieBase(TemplateService templateService) {
		KieHelper helper = new KieHelper();

		// Staticki DRL fajlovi citaju se iz kjar JAR-a preko classpath resursa
		String[] drlPaths = {
			"/com/ftn/sbnz/kjar/rules/traffic-rules.drl",
			"/com/ftn/sbnz/kjar/rules/traffic-backward-rules.drl",
			"/com/ftn/sbnz/kjar/rules/traffic-cep-rules.drl"
		};
		for (String path : drlPaths) {
			try (InputStream is = getClass().getResourceAsStream(path)) {
				if (is == null) throw new RuntimeException("DRL fajl nije pronadjen: " + path);
				helper.addContent(new String(is.readAllBytes(), StandardCharsets.UTF_8), ResourceType.DRL);
			} catch (Exception e) {
				throw new RuntimeException("Greska pri ucitavanju " + path, e);
			}
		}

		// Pravila generisana iz sablona ucitavaju se pri svakom pokretanju
		helper.addContent(templateService.generisiPravilaIstekaDozvole(), ResourceType.DRL);
		helper.addContent(templateService.generisiPravilaPocetnikaVozaca(), ResourceType.DRL);
		helper.addContent(templateService.generisiPravilaProfesionalnihVozaca(), ResourceType.DRL);
		helper.addContent(templateService.generisiPravilaKontekstRizika(), ResourceType.DRL);

		// STREAM mod je neophodan za CEP pravila
		KieBaseConfiguration conf = KieServices.Factory.get().newKieBaseConfiguration();
		conf.setOption(EventProcessingOption.STREAM);

		return helper.build(conf);
	}

}
