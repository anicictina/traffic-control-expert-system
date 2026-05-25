package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class SpeedingEvent {
    private String personalId;
    private int speed;
    private int speedOverLimit;
    private LocalDateTime timestamp;

    public SpeedingEvent() {}

    public SpeedingEvent(String personalId, int speed, int speedOverLimit, LocalDateTime timestamp) {
        this.personalId = personalId;
        this.speed = speed;
        this.speedOverLimit = speedOverLimit;
        this.timestamp = timestamp;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public int getSpeedOverLimit() { return speedOverLimit; }
    public void setSpeedOverLimit(int speedOverLimit) { this.speedOverLimit = speedOverLimit; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "SpeedingEvent{" +
            "personalId='" + personalId + '\'' +
            ", speed=" + speed +
            ", speedOverLimit=" + speedOverLimit +
            ", timestamp=" + timestamp +
            '}';
    }
}
