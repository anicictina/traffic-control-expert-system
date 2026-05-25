package com.ftn.sbnz.model;

import java.time.LocalDate;

public class Offense {
    private String driverPersonalId;
    private String type;
    private String severity;
    private String description;
    private LocalDate date;

    public Offense() {}

    public Offense(String driverPersonalId, String type, String severity, String description) {
        this.driverPersonalId = driverPersonalId;
        this.type = type;
        this.severity = severity;
        this.description = description;
        this.date = java.time.LocalDate.now();
    }

    public String getDriverPersonalId() { return driverPersonalId; }
    public void setDriverPersonalId(String driverPersonalId) { this.driverPersonalId = driverPersonalId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Offense{" +
            "driverPersonalId='" + driverPersonalId + '\'' +
            ", type='" + type + '\'' +
            ", severity='" + severity + '\'' +
            ", description='" + description + '\'' +
            ", date=" + date +
            '}';
    }
}
