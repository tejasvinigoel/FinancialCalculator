package com.example.FinancialCalculator.parameters;

public class SIPParameters {
    private double monthlyInvestment;
    private double annualInterestRate;
    private int durationInYears;

    

    /**
     * @return double return the monthlyInvestment
     */
    public double getMonthlyInvestment() {
        return monthlyInvestment;
    }

    /**
     * @param monthlyInvestment the monthlyInvestment to set
     */
    public void setMonthlyInvestment(double monthlyInvestment) {
        this.monthlyInvestment = monthlyInvestment;
    }

    /**
     * @return double return the annualInterestRate
     */
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    /**
     * @param annualInterestRate the annualInterestRate to set
     */
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    /**
     * @return int return the durationInYears
     */
    public int getDurationInYears() {
        return durationInYears;
    }

    /**
     * @param durationInYears the durationInYears to set
     */
    public void setDurationInYears(int durationInYears) {
        this.durationInYears = durationInYears;
    }

}
