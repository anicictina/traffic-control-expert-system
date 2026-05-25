package com.ftn.sbnz.model;

public class RiskFactor {
    private String name;
    private String description;

    public RiskFactor() {}

    public RiskFactor(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "RiskFactor{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
