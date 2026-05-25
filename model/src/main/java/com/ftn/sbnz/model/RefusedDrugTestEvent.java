package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class RefusedDrugTestEvent {
    private String personalId;
    private LocalDateTime timestamp;

    public RefusedDrugTestEvent() {}

    public RefusedDrugTestEvent(String personalId, LocalDateTime timestamp) {
        this.personalId = personalId;
        this.timestamp = timestamp;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "RefusedDrugTestEvent{" +
            "personalId='" + personalId + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
