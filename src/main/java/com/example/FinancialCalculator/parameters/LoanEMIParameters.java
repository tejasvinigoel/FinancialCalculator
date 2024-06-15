package com.example.FinancialCalculator.parameters;

public class LoanEMIParameters {
    private double loanAmount;
    private double annualInterestRate;
    private int loanTenure;



    /**
     * @return double return the loanAmount
     */
    public double getLoanAmount() {
        return loanAmount;
    }

    /**
     * @param loanAmount the loanAmount to set
     */
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
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
     * @return int return the loanTenure
     */
    public int getLoanTenure() {
        return loanTenure;
    }

    /**
     * @param loanTenure the loanTenure to set
     */
    public void setLoanTenure(int loanTenure) {
        this.loanTenure = loanTenure;
    }

}
