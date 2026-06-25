package com.ftn.sbnz.service.dto;

public class OffenseDto {
    private String type;
    private String severity;
    private String description;

    public OffenseDto() {}

    public OffenseDto(String type, String severity, String description) {
        this.type = type;
        this.severity = severity;
        this.description = description;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
