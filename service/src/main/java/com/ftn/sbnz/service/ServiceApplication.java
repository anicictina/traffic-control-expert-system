package com.ftn.sbnz.service;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		// Drools 7.x koristi ECJ koji zahteva java.lang.Compiler (uklonjen u Java 17+).
		// NATIVE kompajler koristi javax.tools.JavaCompiler iz JDK-a umesto ECJ-a.
		System.setProperty("drools.dialect.java.compiler", "NATIVE");
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean
	public KieBase kieBase() {
		KieServices ks = KieServices.Factory.get();
		// getKieClasspathContainer skenira sve JAR-ove na classpath-u i trazi
		// META-INF/kmodule.xml — ne koristi Maven resolver, kompatibilno sa Java 17+
		KieContainer kContainer = ks.getKieClasspathContainer();
		return kContainer.getKieBase("kontrolaKBase");
	}

}
