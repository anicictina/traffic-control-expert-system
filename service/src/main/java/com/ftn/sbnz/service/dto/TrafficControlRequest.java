package com.ftn.sbnz.service.dto;

public class TrafficControlRequest {

    // Podaci o vozacu
    private String fullName;
    private String personalId;
    private String status; // "obican", "pocetnik", "profesionalni"
    private int previousOffenses;
    private int previousAlcoholOffenses;
    private boolean drivingBan;

    // Podaci o vozackoj dozvoli
    private String licenseNumber;
    private String licenseExpiryDate; // "yyyy-MM-dd"
    private String licenseCategory;

    // Podaci o kontroli
    private String controlTime; // "yyyy-MM-ddTHH:mm:ss"
    private String location;
    private String controlType; // "redovna", "pojacana", "kontrola nakon nezgode"
    private boolean nightControl;
    private boolean weekendControl;
    private boolean schoolZone;
    private boolean highRiskZone;
    private boolean afterAccident;
    private boolean aggressiveDriving;
    private boolean redEyes;
    private boolean disorientation;
    private boolean delayedReaction;
    private boolean unstableGait;
    private boolean slurredSpeech;

    // Alkotest
    private double alcoholResult;
    private boolean refusedAlcoholTest;

    // Droga test
    private boolean drugTestPositive;
    private boolean refusedDrugTest;

    // Podaci o voznji
    private int speed;
    private int speedOverLimit;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPersonalId() { return personalId; }
    public void setPersonalId(String personalId) { this.personalId = personalId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getPreviousOffenses() { return previousOffenses; }
    public void setPreviousOffenses(int previousOffenses) { this.previousOffenses = previousOffenses; }
    public int getPreviousAlcoholOffenses() { return previousAlcoholOffenses; }
    public void setPreviousAlcoholOffenses(int previousAlcoholOffenses) { this.previousAlcoholOffenses = previousAlcoholOffenses; }
    public boolean isDrivingBan() { return drivingBan; }
    public void setDrivingBan(boolean drivingBan) { this.drivingBan = drivingBan; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public String getLicenseExpiryDate() { return licenseExpiryDate; }
    public void setLicenseExpiryDate(String licenseExpiryDate) { this.licenseExpiryDate = licenseExpiryDate; }
    public String getLicenseCategory() { return licenseCategory; }
    public void setLicenseCategory(String licenseCategory) { this.licenseCategory = licenseCategory; }
    public String getControlTime() { return controlTime; }
    public void setControlTime(String controlTime) { this.controlTime = controlTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getControlType() { return controlType; }
    public void setControlType(String controlType) { this.controlType = controlType; }
    public boolean isNightControl() { return nightControl; }
    public void setNightControl(boolean nightControl) { this.nightControl = nightControl; }
    public boolean isWeekendControl() { return weekendControl; }
    public void setWeekendControl(boolean weekendControl) { this.weekendControl = weekendControl; }
    public boolean isSchoolZone() { return schoolZone; }
    public void setSchoolZone(boolean schoolZone) { this.schoolZone = schoolZone; }
    public boolean isHighRiskZone() { return highRiskZone; }
    public void setHighRiskZone(boolean highRiskZone) { this.highRiskZone = highRiskZone; }
    public boolean isAfterAccident() { return afterAccident; }
    public void setAfterAccident(boolean afterAccident) { this.afterAccident = afterAccident; }
    public boolean isAggressiveDriving() { return aggressiveDriving; }
    public void setAggressiveDriving(boolean aggressiveDriving) { this.aggressiveDriving = aggressiveDriving; }
    public boolean isRedEyes() { return redEyes; }
    public void setRedEyes(boolean redEyes) { this.redEyes = redEyes; }
    public boolean isDisorientation() { return disorientation; }
    public void setDisorientation(boolean disorientation) { this.disorientation = disorientation; }
    public boolean isDelayedReaction() { return delayedReaction; }
    public void setDelayedReaction(boolean delayedReaction) { this.delayedReaction = delayedReaction; }
    public boolean isUnstableGait() { return unstableGait; }
    public void setUnstableGait(boolean unstableGait) { this.unstableGait = unstableGait; }
    public boolean isSlurredSpeech() { return slurredSpeech; }
    public void setSlurredSpeech(boolean slurredSpeech) { this.slurredSpeech = slurredSpeech; }
    public double getAlcoholResult() { return alcoholResult; }
    public void setAlcoholResult(double alcoholResult) { this.alcoholResult = alcoholResult; }
    public boolean isRefusedAlcoholTest() { return refusedAlcoholTest; }
    public void setRefusedAlcoholTest(boolean refusedAlcoholTest) { this.refusedAlcoholTest = refusedAlcoholTest; }
    public boolean isDrugTestPositive() { return drugTestPositive; }
    public void setDrugTestPositive(boolean drugTestPositive) { this.drugTestPositive = drugTestPositive; }
    public boolean isRefusedDrugTest() { return refusedDrugTest; }
    public void setRefusedDrugTest(boolean refusedDrugTest) { this.refusedDrugTest = refusedDrugTest; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public int getSpeedOverLimit() { return speedOverLimit; }
    public void setSpeedOverLimit(int speedOverLimit) { this.speedOverLimit = speedOverLimit; }
}
