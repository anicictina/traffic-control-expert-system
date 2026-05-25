package com.ftn.sbnz.model;

public class HighPriorityControl {
    private String personalId;
    private String reason;

    public HighPriorityControl() {}

    public HighPriorityControl(String personalId, String reason) {
        this.personalId = personalId;
        this.reason = reason;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    @Override
    public String toString() {
        return "HighPriorityControl{" +
                "personalId='" + personalId + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
