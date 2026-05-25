package com.ftn.sbnz.service;

import org.kie.api.KieServices;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.builder.Message;
import org.kie.internal.utils.KieHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean
	public KieBase kieBase() {
		KieServices ks = KieServices.Factory.get();
		try {
			KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
			try {
				Object kScanner = ks.getClass().getMethod("newKieScanner", KieContainer.class).invoke(ks, kContainer);
				kScanner.getClass().getMethod("start", long.class).invoke(kScanner, 1000L);
			} catch (Exception ignored) {
			}
			return kContainer.getKieBase("kontrolaKBase");
		} catch (Exception e) {
			// fallback: build kie base programmatically from DRL resources
			KieHelper kieHelper = new KieHelper();
			kieHelper.addResource(ks.getResources().newClassPathResource("com/ftn/sbnz/kjar/rules/traffic-rules.drl"), ResourceType.DRL);
			Results results = kieHelper.verify();
			for (Message m : results.getMessages()) {
				if (m.getLevel() == Message.Level.ERROR) {
					throw new IllegalStateException("DRL error: " + m.getText());
				}
			}
			return kieHelper.build();
		}
	}

}
