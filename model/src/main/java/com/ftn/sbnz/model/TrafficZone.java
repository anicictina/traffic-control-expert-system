package com.ftn.sbnz.model;

public class TrafficZone {
    private String name;
    private String type;

    public TrafficZone() {}

    public TrafficZone(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "TrafficZone{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            '}';
    }
}
