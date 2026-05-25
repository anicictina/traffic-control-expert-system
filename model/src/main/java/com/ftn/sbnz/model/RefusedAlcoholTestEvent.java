package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class RefusedAlcoholTestEvent {
    private String personalId;
    private LocalDateTime timestamp;

    public RefusedAlcoholTestEvent() {}

    public RefusedAlcoholTestEvent(String personalId, LocalDateTime timestamp) {
        this.personalId = personalId;
        this.timestamp = timestamp;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "RefusedAlcoholTestEvent{" +
            "personalId='" + personalId + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
