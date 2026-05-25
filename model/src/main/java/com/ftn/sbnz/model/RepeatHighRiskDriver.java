package com.ftn.sbnz.model;

public class RepeatHighRiskDriver {
    private String personalId;
    private int count;

    public RepeatHighRiskDriver() {}

    public RepeatHighRiskDriver(String personalId, int count) {
        this.personalId = personalId;
        this.count = count;
    }
    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    @Override
    public String toString() {
        return "RepeatHighRiskDriver{" +
            "personalId='" + personalId + '\'' +
            ", count=" + count +
            '}';
    }
}
