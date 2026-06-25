package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class AccelerationEvent {
    private String personalId;
    private int acceleration;
    private LocalDateTime timestamp;

    public AccelerationEvent() {}

    public AccelerationEvent(String personalId, int acceleration, LocalDateTime timestamp) {
        this.personalId = personalId;
        this.acceleration = acceleration;
        this.timestamp = timestamp;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public int getAcceleration() { return acceleration; }
    public void setAcceleration(int acceleration) { this.acceleration = acceleration; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "AccelerationEvent{" +
            "personalId='" + personalId + '\'' +
            ", acceleration=" + acceleration +
            ", timestamp=" + timestamp +
            '}';
    }
}
