package com.ftn.sbnz.model;

public class DecisionPhase {
    private String phase;
    private String comment;

    public DecisionPhase() {}

    public DecisionPhase(String phase, String comment) {
        this.phase = phase;
        this.comment = comment;
    }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "DecisionPhase{" +
            "phase='" + phase + '\'' +
            ", comment='" + comment + '\'' +
            '}';
    }
}
