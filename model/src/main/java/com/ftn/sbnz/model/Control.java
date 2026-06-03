package com.ftn.sbnz.model;

import java.time.LocalDateTime;

public class Control {

    private LocalDateTime time;
    private String location;
    private String type;
    private boolean nightControl;
    private boolean weekendControl;
    private boolean schoolZone;
    private boolean highRiskZone;
    private boolean afterAccident;
    private boolean aggressiveDriving;
    private boolean redEyes;
    private boolean disorientation;
    private boolean delayedReaction;

    public Control() {}

    public Control(LocalDateTime time, String location, String type, boolean nightControl, boolean weekendControl, boolean schoolZone, boolean highRiskZone, boolean afterAccident, boolean aggressiveDriving) {
        this(time, location, type, nightControl, weekendControl, schoolZone, highRiskZone, afterAccident, aggressiveDriving, false, false, false);
    }

    public Control(LocalDateTime time, String location, String type, boolean nightControl, boolean weekendControl, boolean schoolZone, boolean highRiskZone, boolean afterAccident, boolean aggressiveDriving, boolean redEyes, boolean disorientation, boolean delayedReaction) {
        this.time = time;
        this.location = location;
        this.type = type;
        this.nightControl = nightControl;
        this.weekendControl = weekendControl;
        this.schoolZone = schoolZone;
        this.highRiskZone = highRiskZone;
        this.afterAccident = afterAccident;
        this.aggressiveDriving = aggressiveDriving;
        this.redEyes = redEyes;
        this.disorientation = disorientation;
        this.delayedReaction = delayedReaction;
    }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
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

    @Override
    public String toString() {
        return "Control{" +
            "time=" + time +
            ", location='" + location + '\'' +
            ", type='" + type + '\'' +
            ", nightControl=" + nightControl +
            ", weekendControl=" + weekendControl +
            ", schoolZone=" + schoolZone +
            ", highRiskZone=" + highRiskZone +
            ", afterAccident=" + afterAccident +
            ", aggressiveDriving=" + aggressiveDriving +
            ", redEyes=" + redEyes +
            ", disorientation=" + disorientation +
            ", delayedReaction=" + delayedReaction +
            '}';
    }
}
