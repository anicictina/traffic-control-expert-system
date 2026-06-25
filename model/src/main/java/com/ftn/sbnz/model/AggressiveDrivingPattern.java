package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class AggressiveDrivingPattern {
    private String personalId;
    private String description;
    private LocalDateTime detectedAt;

    public AggressiveDrivingPattern() {}

    public AggressiveDrivingPattern(String personalId, String description, LocalDateTime detectedAt) {
        this.personalId = personalId;
        this.description = description;
        this.detectedAt = detectedAt;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }

    @Override
    public String toString() {
        return "AggressiveDrivingPattern{" +
            "personalId='" + personalId + '\'' +
            ", description='" + description + '\'' +
            ", detectedAt=" + detectedAt +
            '}';
    }
}
