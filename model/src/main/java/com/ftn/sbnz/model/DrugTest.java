package com.ftn.sbnz.model;

public class DrugTest {
    private boolean positive;
    private boolean refused;

    public DrugTest() {}

    public DrugTest(boolean positive, boolean refused) {
        this.positive = positive;
        this.refused = refused;
    }

    public boolean isPositive() { return positive; }
    public void setPositive(boolean positive) { this.positive = positive; }
    public boolean isRefused() { return refused; }
    public void setRefused(boolean refused) { this.refused = refused; }

    @Override
    public String toString() {
        return "DrugTest{" +
            "positive=" + positive +
            ", refused=" + refused +
            '}';
    }
}
