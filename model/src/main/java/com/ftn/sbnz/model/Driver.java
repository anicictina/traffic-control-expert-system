package com.ftn.sbnz.model;

public class Driver {

    private String fullName;
    private String personalId;
    private String status;
    private int previousOffenses;
    private int previousAlcoholOffenses;
    private boolean drivingBan;

    public Driver() {
    }

    public Driver(String fullName, String personalId, String status, int previousOffenses, int previousAlcoholOffenses, boolean drivingBan) {
        this.fullName = fullName;
        this.personalId = personalId;
        this.status = status;
        this.previousOffenses = previousOffenses;
        this.previousAlcoholOffenses = previousAlcoholOffenses;
        this.drivingBan = drivingBan;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPreviousOffenses() {
        return previousOffenses;
    }

    public void setPreviousOffenses(int previousOffenses) {
        this.previousOffenses = previousOffenses;
    }

    public int getPreviousAlcoholOffenses() {
        return previousAlcoholOffenses;
    }

    public void setPreviousAlcoholOffenses(int previousAlcoholOffenses) {
        this.previousAlcoholOffenses = previousAlcoholOffenses;
    }

    public boolean isDrivingBan() {
        return drivingBan;
    }

    public void setDrivingBan(boolean drivingBan) {
        this.drivingBan = drivingBan;
    }

    @Override
    public String toString() {
        return "Driver{" +
            "fullName='" + fullName + '\'' +
            ", personalId='" + personalId + '\'' +
            ", status='" + status + '\'' +
            ", previousOffenses=" + previousOffenses +
            ", previousAlcoholOffenses=" + previousAlcoholOffenses +
            ", drivingBan=" + drivingBan +
            '}';
    }
}
