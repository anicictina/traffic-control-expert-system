package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class SuddenBrakingEvent {
    private String personalId;
    private LocalDateTime timestamp;

    public SuddenBrakingEvent() {}

    public SuddenBrakingEvent(String personalId, LocalDateTime timestamp) {
        this.personalId = personalId;
        this.timestamp = timestamp;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "SuddenBrakingEvent{" +
            "personalId='" + personalId + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
