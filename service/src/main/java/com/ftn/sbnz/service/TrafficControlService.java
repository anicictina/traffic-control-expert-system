package com.ftn.sbnz.service;

import com.ftn.sbnz.model.*;
import com.ftn.sbnz.service.dto.OffenseDto;
import com.ftn.sbnz.service.dto.TrafficControlRequest;
import com.ftn.sbnz.service.dto.TrafficControlResponse;
import org.kie.api.KieBase;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TrafficControlService {

    private final KieBase kieBase;
    private final StatisticsService statisticsService;

    public TrafficControlService(KieBase kieBase, StatisticsService statisticsService) {
        this.kieBase = kieBase;
        this.statisticsService = statisticsService;
    }

    public TrafficControlResponse analyze(TrafficControlRequest req) {
        KieSession session = kieBase.newKieSession();
        List<String> firedRules = new ArrayList<>();
        session.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRules.add(event.getMatch().getRule().getName());
            }
        });
        try {
            Driver driver = new Driver(
                req.getFullName(), req.getPersonalId(), req.getStatus(),
                req.getPreviousOffenses(), req.getPreviousAlcoholOffenses(), req.isDrivingBan()
            );

            LocalDate expiryDate = (req.getLicenseExpiryDate() != null && !req.getLicenseExpiryDate().isBlank())
                ? LocalDate.parse(req.getLicenseExpiryDate()) : LocalDate.now().plusYears(2);

            DrivingLicense license = new DrivingLicense(
                req.getLicenseNumber() != null ? req.getLicenseNumber() : "N/A",
                expiryDate,
                req.getLicenseCategory() != null ? req.getLicenseCategory() : "B"
            );

            LocalDateTime controlTime = (req.getControlTime() != null && !req.getControlTime().isBlank())
                ? LocalDateTime.parse(req.getControlTime()) : LocalDateTime.now();

            Control control = new Control(
                controlTime, req.getLocation(), req.getControlType(),
                req.isNightControl(), req.isWeekendControl(), req.isSchoolZone(),
                req.isHighRiskZone(), req.isAfterAccident(), req.isAggressiveDriving(),
                req.isRedEyes(), req.isDisorientation(), req.isDelayedReaction(),
                req.isUnstableGait(), req.isSlurredSpeech()
            );

            AlcoTest alcoTest = new AlcoTest(req.getAlcoholResult(), req.isRefusedAlcoholTest());
            DrugTest drugTest = new DrugTest(req.isDrugTestPositive(), req.isRefusedDrugTest());
            SpeedingEvent speedingEvent = new SpeedingEvent(
                req.getPersonalId(), req.getSpeed(), req.getSpeedOverLimit(), controlTime
            );
            ControlDecision decision = new ControlDecision();

            session.insert(driver);
            session.insert(license);
            session.insert(control);
            session.insert(alcoTest);
            session.insert(drugTest);
            session.insert(speedingEvent);
            session.insert(decision);

            session.fireAllRules();

            TrafficControlResponse response = buildResponse(session, decision, req.getPersonalId(), firedRules);
            statisticsService.record(req, response);
            return response;
        } finally {
            session.dispose();
        }
    }

    private TrafficControlResponse buildResponse(KieSession session, ControlDecision decision, String personalId, List<String> firedRules) {
        TrafficControlResponse response = new TrafficControlResponse();
        response.setRiskLevel(decision.getRiskLevel());
        response.setRecommendation(decision.getRecommendation());

        List<OffenseDto> offenses = new ArrayList<>();
        List<String> derivedFacts = new ArrayList<>();

        for (Object fact : session.getObjects()) {
            if (fact instanceof Offense o) {
                offenses.add(new OffenseDto(o.getType(), o.getSeverity(), o.getDescription()));
            } else if (fact instanceof AlcoholState a) {
                derivedFacts.add("AlcoholState: " + a.getState());
            } else if (fact instanceof HighRiskDriver h) {
                derivedFacts.add("HighRiskDriver (nivo rizika: " + h.getRiskLevel() + ")");
            } else if (fact instanceof PossibleDrugInfluence p) {
                derivedFacts.add("PossibleDrugInfluence: " + p.getDescription());
            } else if (fact instanceof CriticalRiskDriver c) {
                derivedFacts.add("CriticalRiskDriver: " + c.getReason());
            } else if (fact instanceof HighPriorityControl hpc) {
                derivedFacts.add("HighPriorityControl: " + hpc.getReason());
            } else if (fact instanceof RepeatHighRiskDriver r) {
                derivedFacts.add("RepeatHighRiskDriver (broj ozbiljnih prekrsaja: " + r.getCount() + ")");
            } else if (fact instanceof AggressiveDrivingPattern adp) {
                derivedFacts.add("AggressiveDrivingPattern: " + adp.getDescription());
            } else if (fact instanceof CepAlarm alarm) {
                derivedFacts.add("CEP Alarm: " + alarm.getType());
            }
        }

        response.setOffenses(offenses);
        response.setDerivedFacts(derivedFacts);

        Map<String, Boolean> hypotheses = new LinkedHashMap<>();
        hypotheses.put("treba_iskljuciti_vozaca", hasQueryResult(session, "treba_iskljuciti_vozaca", personalId));
        hypotheses.put("potreban_hitan_pregled", hasQueryResult(session, "potreban_hitan_pregled", personalId));
        hypotheses.put("visokorizican_vozac", hasQueryResult(session, "visokorizican_vozac", personalId));
        hypotheses.put("potrebno_zadrzavanje", hasQueryResult(session, "potrebno_zadrzavanje", personalId));
        response.setHypotheses(hypotheses);

        Set<String> riskFactors = new TreeSet<>();
        QueryResults qr = session.getQueryResults("direktni_faktori_rizika", personalId, Variable.v);
        for (QueryResultsRow row : qr) {
            riskFactors.add((String) row.get("$factor"));
        }
        response.setRiskFactors(new ArrayList<>(riskFactors));
        response.setFiredRules(firedRules);

        return response;
    }

    private boolean hasQueryResult(KieSession session, String queryName, Object arg) {
        return session.getQueryResults(queryName, arg).iterator().hasNext();
    }
}
