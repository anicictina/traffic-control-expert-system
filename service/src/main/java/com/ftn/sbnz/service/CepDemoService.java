package com.ftn.sbnz.service;

import com.ftn.sbnz.model.*;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CepDemoService {

    private final KieBase kieBase;

    public CepDemoService(KieBase kieBase) {
        this.kieBase = kieBase;
    }

    /**
     * Demo 1: UCESTALA_AGRESIVNA_VOZNJA
     * U 15 minuta: 3+ prekoracenja brzine + 2+ nagla kocenja + 1 prelazak linije
     */
    public Map<String, Object> demoAggressiveDriving() {
        KieSession session = createPseudoClockSession();
        SessionPseudoClock clock = session.getSessionClock();
        List<String> timeline = new ArrayList<>();
        String pid = "DEMO_AGR_001";

        try {
            session.insert(new Driver("Demo Vozac Agresivni", pid, "obican", 5, 2, false));

            session.insert(new SpeedingEvent(pid, 85, 25, LocalDateTime.now()));
            timeline.add("T+00:00 — SpeedingEvent #1: 85 km/h (+25 iznad ogranicenja)");

            clock.advanceTime(3, TimeUnit.MINUTES);
            session.insert(new SuddenBrakingEvent(pid, LocalDateTime.now()));
            timeline.add("T+03:00 — SuddenBrakingEvent #1: naglo kocenje");

            clock.advanceTime(2, TimeUnit.MINUTES);
            session.insert(new SpeedingEvent(pid, 92, 32, LocalDateTime.now()));
            timeline.add("T+05:00 — SpeedingEvent #2: 92 km/h (+32 iznad ogranicenja)");

            clock.advanceTime(2, TimeUnit.MINUTES);
            session.insert(new SuddenBrakingEvent(pid, LocalDateTime.now()));
            timeline.add("T+07:00 — SuddenBrakingEvent #2: naglo kocenje");

            clock.advanceTime(2, TimeUnit.MINUTES);
            session.insert(new LaneCrossingEvent(pid, LocalDateTime.now()));
            timeline.add("T+09:00 — LaneCrossingEvent: prelazak pune linije");

            clock.advanceTime(1, TimeUnit.MINUTES);
            session.insert(new SpeedingEvent(pid, 78, 18, LocalDateTime.now()));
            timeline.add("T+10:00 — SpeedingEvent #3: 78 km/h (+18 iznad ogranicenja)");

            session.fireAllRules();
            return buildResult(session, timeline, "CEP-1: Ucestala agresivna voznja (prozor 15 min)");
        } finally {
            session.dispose();
        }
    }

    /**
     * Demo 2: POTENCIJALNI_BEG
     * U 10 minuta: odbijanje zaustavljanja + 2+ nagla ubrzanja + brzina >40 km/h iznad limita
     */
    public Map<String, Object> demoPotentialEscape() {
        KieSession session = createPseudoClockSession();
        SessionPseudoClock clock = session.getSessionClock();
        List<String> timeline = new ArrayList<>();
        String pid = "DEMO_BEG_002";

        try {
            session.insert(new Driver("Demo Vozac Beg", pid, "obican", 2, 0, false));

            session.insert(new RefusedStopEvent(pid, LocalDateTime.now()));
            timeline.add("T+00:00 — RefusedStopEvent: odbio zaustavljanje");

            clock.advanceTime(1, TimeUnit.MINUTES);
            session.insert(new AccelerationEvent(pid, 80, LocalDateTime.now()));
            timeline.add("T+01:00 — AccelerationEvent #1: naglo ubrzanje");

            clock.advanceTime(2, TimeUnit.MINUTES);
            session.insert(new SpeedingEvent(pid, 130, 50, LocalDateTime.now()));
            timeline.add("T+03:00 — SpeedingEvent: 130 km/h (+50 iznad ogranicenja)");

            clock.advanceTime(1, TimeUnit.MINUTES);
            session.insert(new AccelerationEvent(pid, 95, LocalDateTime.now()));
            timeline.add("T+04:00 — AccelerationEvent #2: naglo ubrzanje");

            session.fireAllRules();
            return buildResult(session, timeline, "CEP-2: Potencijalni pokusaj bega (prozor 10 min)");
        } finally {
            session.dispose();
        }
    }

    /**
     * Demo 3: VISOKORIZICNA_NOCNA_VOZNJA
     * AggressiveDrivingPattern + nocna kontrola + prethodni alkoholni prekrsaji
     */
    public Map<String, Object> demoHighRiskNight() {
        KieSession session = createPseudoClockSession();
        SessionPseudoClock clock = session.getSessionClock();
        List<String> timeline = new ArrayList<>();
        String pid = "DEMO_NOC_003";

        try {
            session.insert(new Driver("Demo Vozac Nocni", pid, "obican", 6, 3, false));
            session.insert(new Control(
                LocalDateTime.now(), "Autoput A1", "pojacana",
                true, true, false, true, false, true,
                true, true, false
            ));
            timeline.add("T+00:00 — Nocna kontrola pokrenuta (agresivna voznja, zona visokog rizika)");

            session.insert(new SpeedingEvent(pid, 95, 35, LocalDateTime.now()));
            timeline.add("T+00:30 — SpeedingEvent #1: 95 km/h (+35 iznad ogranicenja)");

            clock.advanceTime(2, TimeUnit.MINUTES);
            session.insert(new SuddenBrakingEvent(pid, LocalDateTime.now()));
            timeline.add("T+02:00 — SuddenBrakingEvent #1");

            clock.advanceTime(3, TimeUnit.MINUTES);
            session.insert(new SpeedingEvent(pid, 88, 28, LocalDateTime.now()));
            timeline.add("T+05:00 — SpeedingEvent #2: 88 km/h (+28 iznad ogranicenja)");

            clock.advanceTime(2, TimeUnit.MINUTES);
            session.insert(new SuddenBrakingEvent(pid, LocalDateTime.now()));
            timeline.add("T+07:00 — SuddenBrakingEvent #2");

            clock.advanceTime(2, TimeUnit.MINUTES);
            session.insert(new SpeedingEvent(pid, 102, 42, LocalDateTime.now()));
            timeline.add("T+09:00 — SpeedingEvent #3: 102 km/h (+42 iznad ogranicenja)");

            clock.advanceTime(1, TimeUnit.MINUTES);
            session.insert(new LaneCrossingEvent(pid, LocalDateTime.now()));
            timeline.add("T+10:00 — LaneCrossingEvent: prelazak pune linije");

            session.fireAllRules();
            return buildResult(session, timeline, "CEP-3: Visokorizicna nocna voznja (kombinovani scenario)");
        } finally {
            session.dispose();
        }
    }

    private KieSession createPseudoClockSession() {
        KieSessionConfiguration conf = KieServices.Factory.get().newKieSessionConfiguration();
        conf.setOption(ClockTypeOption.get("pseudo"));
        return kieBase.newKieSession(conf, null);
    }

    private Map<String, Object> buildResult(KieSession session, List<String> timeline, String scenarioName) {
        List<String> alarms = new ArrayList<>();
        List<String> generatedFacts = new ArrayList<>();

        for (Object fact : session.getObjects()) {
            if (fact instanceof CepAlarm a) {
                alarms.add(a.getType());
            } else if (fact instanceof AggressiveDrivingPattern adp) {
                generatedFacts.add("AggressiveDrivingPattern: " + adp.getDescription());
            } else if (fact instanceof PotentialEscapeAttempt pea) {
                generatedFacts.add("PotentialEscapeAttempt: " + pea.getDescription());
            } else if (fact instanceof HighPriorityControl hpc) {
                generatedFacts.add("HighPriorityControl: " + hpc.getReason());
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("scenarij", scenarioName);
        result.put("vremenkaLinija", timeline);
        result.put("alarmi", alarms);
        result.put("generisaneCinjenice", generatedFacts);
        result.put("alarmDetektovan", !alarms.isEmpty());
        return result;
    }
}
