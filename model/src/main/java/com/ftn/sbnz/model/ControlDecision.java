package com.ftn.sbnz.model;

public class ControlDecision {
    private int riskLevel;
    private String recommendation;

    public ControlDecision() {}

    public int getRiskLevel() { return riskLevel; }
    public void setRiskLevel(int riskLevel) { this.riskLevel = riskLevel; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    @Override
    public String toString() {
        return "ControlDecision{" +
            "riskLevel=" + riskLevel +
            ", recommendation='" + recommendation + '\'' +
            '}';
    }
}
