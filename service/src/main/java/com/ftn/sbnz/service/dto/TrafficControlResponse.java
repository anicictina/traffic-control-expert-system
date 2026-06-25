package com.ftn.sbnz.service.dto;

import java.util.List;
import java.util.Map;

public class TrafficControlResponse {

    private int riskLevel;
    private String riskLevelLabel;
    private String recommendation;
    private List<OffenseDto> offenses;
    private List<String> derivedFacts;
    private Map<String, Boolean> hypotheses;
    private List<String> riskFactors;

    public int getRiskLevel() { return riskLevel; }
    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
        this.riskLevelLabel = switch (riskLevel) {
            case 0, 1 -> "Nizak";
            case 2 -> "Srednji";
            case 3 -> "Visok";
            case 4 -> "Kritican";
            default -> "Nepoznat";
        };
    }
    public String getRiskLevelLabel() { return riskLevelLabel; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    public List<OffenseDto> getOffenses() { return offenses; }
    public void setOffenses(List<OffenseDto> offenses) { this.offenses = offenses; }
    public List<String> getDerivedFacts() { return derivedFacts; }
    public void setDerivedFacts(List<String> derivedFacts) { this.derivedFacts = derivedFacts; }
    public Map<String, Boolean> getHypotheses() { return hypotheses; }
    public void setHypotheses(Map<String, Boolean> hypotheses) { this.hypotheses = hypotheses; }
    public List<String> getRiskFactors() { return riskFactors; }
    public void setRiskFactors(List<String> riskFactors) { this.riskFactors = riskFactors; }
}
