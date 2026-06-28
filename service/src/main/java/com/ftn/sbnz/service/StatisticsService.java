package com.ftn.sbnz.service;

import com.ftn.sbnz.service.dto.TrafficControlRequest;
import com.ftn.sbnz.service.dto.TrafficControlResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private static final int MAX_HISTORY = 200;

    private final List<ControlSummary> history = new CopyOnWriteArrayList<>();

    public void record(TrafficControlRequest req, TrafficControlResponse res) {
        List<String> offenseTypes = res.getOffenses() == null ? List.of() :
            res.getOffenses().stream().map(o -> o.getType()).collect(Collectors.toList());

        history.add(new ControlSummary(
            req.getPersonalId(),
            req.getFullName(),
            req.getLocation() != null ? req.getLocation() : "",
            res.getRiskLevel(),
            res.getRecommendation(),
            offenseTypes,
            LocalDateTime.now()
        ));

        if (history.size() > MAX_HISTORY) {
            history.remove(0);
        }
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("ukupno", history.size());

        Map<String, Long> nivoiRizika = new LinkedHashMap<>();
        nivoiRizika.put("Nizak (0-1)", history.stream().filter(c -> c.riskLevel <= 1).count());
        nivoiRizika.put("Srednji (2)", history.stream().filter(c -> c.riskLevel == 2).count());
        nivoiRizika.put("Visok (3)", history.stream().filter(c -> c.riskLevel == 3).count());
        nivoiRizika.put("Kritican (4)", history.stream().filter(c -> c.riskLevel == 4).count());
        stats.put("nivoiRizika", nivoiRizika);

        Map<String, Long> preporuke = history.stream()
            .filter(c -> c.recommendation != null)
            .collect(Collectors.groupingBy(c -> c.recommendation, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (a, b) -> a, LinkedHashMap::new));
        stats.put("preporuke", preporuke);

        Map<String, Long> lokacije = history.stream()
            .filter(c -> !c.location.isBlank())
            .collect(Collectors.groupingBy(c -> c.location, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (a, b) -> a, LinkedHashMap::new));
        stats.put("lokacije", lokacije);

        Map<String, Long> prekrsaji = history.stream()
            .flatMap(c -> c.offenseTypes.stream())
            .collect(Collectors.groupingBy(t -> t, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (a, b) -> a, LinkedHashMap::new));
        stats.put("prekrsaji", prekrsaji);

        return stats;
    }

    public List<Map<String, Object>> getHistory() {
        return history.stream().map(c -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("personalId", c.personalId);
            m.put("fullName", c.fullName);
            m.put("location", c.location);
            m.put("riskLevel", c.riskLevel);
            m.put("recommendation", c.recommendation);
            m.put("offenseTypes", c.offenseTypes);
            m.put("time", c.time.toString());
            return m;
        }).collect(Collectors.toList());
    }

    private record ControlSummary(
        String personalId,
        String fullName,
        String location,
        int riskLevel,
        String recommendation,
        List<String> offenseTypes,
        LocalDateTime time
    ) {}
}
