package com.ftn.sbnz.service;

import com.ftn.sbnz.model.AlcoTest;
import com.ftn.sbnz.model.AlcoholState;
import com.ftn.sbnz.model.CriticalRiskDriver;
import com.ftn.sbnz.model.DrugTest;
import com.ftn.sbnz.model.Control;
import com.ftn.sbnz.model.ControlDecision;
import com.ftn.sbnz.model.HighPriorityControl;
import com.ftn.sbnz.model.HighRiskDriver;
import com.ftn.sbnz.model.Offense;
import com.ftn.sbnz.model.Driver;
import com.ftn.sbnz.model.DrivingLicense;
import com.ftn.sbnz.model.PossibleDrugInfluence;
import com.ftn.sbnz.model.RefusedAlcoholTestEvent;
import com.ftn.sbnz.model.RefusedDrugTestEvent;
import com.ftn.sbnz.model.RepeatHighRiskDriver;
import com.ftn.sbnz.model.SpeedingEvent;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.TreeSet;

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
				new Control(LocalDateTime.now(), "Novi Sad", "redovna", false, false, false, false, false, false, false, false, false),
				new AlcoTest(0.0, false),
				new DrugTest(false, false),
				new SpeedingEvent("0101990123456", 50, 0, LocalDateTime.now()));

		runScenario("Scenario 2 - pocetnik i alkohol", new Driver("Ivan Ilic", "0202990123456", "pocetnik", 4, 2, true),
				new DrivingLicense("B67890", LocalDate.now().plusYears(2), "B"),
				new Control(LocalDateTime.now(), "Beograd", "pojacana", true, true, false, false, false, true, true, true, true),
				new AlcoTest(0.72, false),
				new DrugTest(false, true),
				new SpeedingEvent("0202990123456", 92, 32, LocalDateTime.now()));

		runScenario("Scenario 3 - profesionalni vozac i odbijanje alkotesta", new Driver("Petar Petrovic", "0303990123456", "profesionalni", 2, 1, false),
				new DrivingLicense("C54321", LocalDate.now().plusYears(1), "C"),
				new Control(LocalDateTime.now(), "Nis", "kontrola nakon nezgode", false, false, true, true, true, false, true, true, true),
				new AlcoTest(0.0, true),
				new DrugTest(false, false),
				new SpeedingEvent("0303990123456", 61, 0, LocalDateTime.now()));
	}

	private void runScenario(String title, Driver vozac, DrivingLicense dozvola, Control kontrola, AlcoTest alkotest, DrugTest drogaTest, SpeedingEvent speedingEvent) {
		KieSession session = kieBase.newKieSession();
		ControlDecision odluka = new ControlDecision();

		session.insert(vozac);
		session.insert(dozvola);
		session.insert(kontrola);
		session.insert(alkotest);
		session.insert(drogaTest);
		session.insert(speedingEvent);
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
		System.out.println("Brzina: " + speedingEvent);
		System.out.println("Odluka: " + odluka);
		System.out.println("Prekrsaji:");
		for (Offense prekrsaj : prekrsaji) {
			System.out.println("  - " + prekrsaj);
		}
		printDerivedFacts(session);

		printBackwardChainingResult(session, vozac.getPersonalId());

		session.dispose();
	}

	private void printDerivedFacts(KieSession session) {
		System.out.println("Izvedene cinjenice:");
		for (Object fact : session.getObjects()) {
			if (fact instanceof AlcoholState || fact instanceof HighRiskDriver || fact instanceof PossibleDrugInfluence
					|| fact instanceof CriticalRiskDriver || fact instanceof HighPriorityControl || fact instanceof RepeatHighRiskDriver
					|| fact instanceof RefusedAlcoholTestEvent || fact instanceof RefusedDrugTestEvent) {
				System.out.println("  - " + fact);
			}
		}
	}

	private void printBackwardChainingResult(KieSession session, String personalId) {
		boolean trebaIskljuciti = hasResult(session, "treba_iskljuciti_vozaca", personalId);
		boolean potrebanHitanPregled = hasResult(session, "potreban_hitan_pregled", personalId);
		boolean visokorizicanVozac = hasResult(session, "visokorizican_vozac", personalId);
		boolean potrebnoZadrzavanje = hasResult(session, "potrebno_zadrzavanje", personalId);

		System.out.println("Backward chaining hipoteze:");
		System.out.println("  Hipoteza: treba_iskljuciti_vozaca -> " + trebaIskljuciti);
		System.out.println("  Hipoteza: potreban_hitan_pregled -> " + potrebanHitanPregled);
		System.out.println("  Hipoteza: visokorizican_vozac -> " + visokorizicanVozac);
		System.out.println("  Hipoteza: potrebno_zadrzavanje -> " + potrebnoZadrzavanje);
		System.out.println("  Dokazni faktori: " + directRiskFactors(session, personalId));
	}

	private boolean hasResult(KieSession session, String queryName, Object argument) {
		return session.getQueryResults(queryName, argument).iterator().hasNext();
	}

	private Set<String> directRiskFactors(KieSession session, String personalId) {
		Set<String> factors = new TreeSet<>();
		QueryResults results = session.getQueryResults("direktni_faktori_rizika", personalId, Variable.v);
		for (QueryResultsRow row : results) {
			factors.add((String) row.get("$factor"));
		}
		return factors;
	}

}
