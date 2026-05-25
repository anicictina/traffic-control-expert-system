package com.ftn.sbnz.model;

public class PossibleDrugInfluence {
    private String personalId;
    private String description;

    public PossibleDrugInfluence() {}

    public PossibleDrugInfluence(String personalId, String description) {
        this.personalId = personalId;
        this.description = description;
    }

    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "PossibleDrugInfluence{" +
            "personalId='" + personalId + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
