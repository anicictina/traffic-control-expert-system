package com.ftn.sbnz.model;

public class AlcoholState {
    private String personalId;
    private String state;

    public AlcoholState() {}

    public AlcoholState(String personalId, String state) {
        this.personalId = personalId;
        this.state = state;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    @Override
    public String toString() {
        return "AlcoholState{" +
            "personalId='" + personalId + '\'' +
            ", state='" + state + '\'' +
            '}';
    }
}