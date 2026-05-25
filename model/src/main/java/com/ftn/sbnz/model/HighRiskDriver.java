package com.ftn.sbnz.model;

public class HighRiskDriver {
    private String personalId;
    private int riskLevel;

    public HighRiskDriver() {}

    public HighRiskDriver(String personalId, int riskLevel) {
        this.personalId = personalId;
        this.riskLevel = riskLevel;
    }
    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public int getRiskLevel() { return riskLevel; }
    public void setRiskLevel(int riskLevel) { this.riskLevel = riskLevel; }

    @Override
    public String toString() {
        return "HighRiskDriver{" +
            "personalId='" + personalId + '\'' +
            ", riskLevel=" + riskLevel +
            '}';
    }
}
