package com.ftn.sbnz.model;

import java.time.LocalDate;

public class DrivingLicense {

    private String number;
    private LocalDate expiryDate;
    private String category;

    public DrivingLicense() {
    }

    public DrivingLicense(String number, LocalDate expiryDate, String category) {
        this.number = number;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "DrivingLicense{" +
            "number='" + number + '\'' +
            ", expiryDate=" + expiryDate +
            ", category='" + category + '\'' +
            '}';
    }
}
