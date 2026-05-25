package com.ftn.sbnz.service;

import com.ftn.sbnz.model.AlcoTest;
import com.ftn.sbnz.model.DrugTest;
import com.ftn.sbnz.model.Control;
import com.ftn.sbnz.model.ControlDecision;
import com.ftn.sbnz.model.Offense;
import com.ftn.sbnz.model.Driver;
import com.ftn.sbnz.model.DrivingLicense;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class KontrolaDemoRunner implements CommandLineRunner {

	private final KieBase kieBase;

	public KontrolaDemoRunner(KieBase kieBase) {
		this.kieBase = kieBase;
	}

	@Override
	public void run(String... args) {
		System.out.println("=== Demo izvrsavanje pravila iz ekspert sistema ===");
		runScenario("Scenario 1 - istekla dozvola", new Driver("Marko Markovic", "0101990123456", "obican", 4, 1, false),
				new DrivingLicense("B12345", LocalDate.now().minusDays(1), "B"),
				new Control(LocalDateTime.now(), "Novi Sad", "redovna", false, false, false, false, false, false),
				new AlcoTest(0.0, false),
				new DrugTest(false, false));

		runScenario("Scenario 2 - pocetnik i alkohol", new Driver("Ivan Ilic", "0202990123456", "pocetnik", 1, 0, false),
				new DrivingLicense("B67890", LocalDate.now().plusYears(2), "B"),
				new Control(LocalDateTime.now(), "Beograd", "pojacana", true, true, false, false, false, true),
				new AlcoTest(0.72, false),
				new DrugTest(false, true));

		runScenario("Scenario 3 - profesionalni vozac i odbijanje alkotesta", new Driver("Petar Petrovic", "0303990123456", "profesionalni", 2, 1, false),
				new DrivingLicense("C54321", LocalDate.now().plusYears(1), "C"),
				new Control(LocalDateTime.now(), "Nis", "kontrola nakon nezgode", false, false, true, true, true, false),
				new AlcoTest(0.0, true),
				new DrugTest(false, false));
	}

	private void runScenario(String title, Driver vozac, DrivingLicense dozvola, Control kontrola, AlcoTest alkotest, DrugTest drogaTest) {
		KieSession session = kieBase.newKieSession();
		ControlDecision odluka = new ControlDecision();

		session.insert(vozac);
		session.insert(dozvola);
		session.insert(kontrola);
		session.insert(alkotest);
		session.insert(drogaTest);
		session.insert(odluka);

		session.fireAllRules();

		List<Offense> prekrsaji = new ArrayList<>();
		for (Object fact : session.getObjects()) {
			if (fact instanceof Offense) {
				prekrsaji.add((Offense) fact);
			}
		}

		System.out.println();
		System.out.println(title);
		System.out.println("Vozac: " + vozac);
		System.out.println("Dozvola: " + dozvola);
		System.out.println("Kontrola: " + kontrola);
		System.out.println("Alkotest: " + alkotest);
		System.out.println("Droga test: " + drogaTest);
		System.out.println("Odluka: " + odluka);
		System.out.println("Prekrsaji:");
		for (Offense prekrsaj : prekrsaji) {
			System.out.println("  - " + prekrsaj);
		}

		session.dispose();
	}

}