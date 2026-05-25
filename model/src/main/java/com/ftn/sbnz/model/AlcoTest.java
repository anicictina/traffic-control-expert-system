package com.ftn.sbnz.model;

public class AlcoTest {
    private double result;
    private boolean refused;

    public AlcoTest() {}

    public AlcoTest(double result, boolean refused) {
        this.result = result;
        this.refused = refused;
    }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }
    public boolean isRefused() { return refused; }
    public void setRefused(boolean refused) { this.refused = refused; }

    @Override
    public String toString() {
        return "AlcoTest{" +
            "result=" + result +
            ", refused=" + refused +
            '}';
    }
}
