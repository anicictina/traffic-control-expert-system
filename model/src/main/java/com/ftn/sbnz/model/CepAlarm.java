package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class CepAlarm {
    private String type;
    private LocalDateTime timestamp;

    public CepAlarm() {}

    public CepAlarm(String type, LocalDateTime timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "CepAlarm{" +
                "type='" + type + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
