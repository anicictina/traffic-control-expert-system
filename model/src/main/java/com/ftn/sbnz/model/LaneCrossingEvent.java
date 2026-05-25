package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class LaneCrossingEvent {
    private String personalId;
    private LocalDateTime timestamp;

    public LaneCrossingEvent() {}

    public LaneCrossingEvent(String personalId, LocalDateTime timestamp) {
        this.personalId = personalId;
        this.timestamp = timestamp;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "LaneCrossingEvent{" +
            "personalId='" + personalId + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
