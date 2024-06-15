package com.example.FinancialCalculator.parameters;

public class LumpsumParameters {
    private long principal;
    private double roi;
    private int time;
    private int frequency;



    /**
     * @return long return the principal
     */
    public long getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(long principal) {
        this.principal = principal;
    }

    /**
     * @return double return the roi
     */
    public double getRoi() {
        return roi;
    }

    /**
     * @param roi the roi to set
     */
    public void setRoi(double roi) {
        this.roi = roi;
    }

    /**
     * @return int return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * @return int return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
