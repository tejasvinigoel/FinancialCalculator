package com.example.FinancialCalculator.parameters;

public class NAPSParameters {
    private double totalAssets;
    private double totalLiabilities;
    private int numberOfShares;

    

    /**
     * @return double return the totalAssets
     */
    public double getTotalAssets() {
        return totalAssets;
    }

    /**
     * @param totalAssets the totalAssets to set
     */
    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }

    /**
     * @return double return the totalLiabilities
     */
    public double getTotalLiabilities() {
        return totalLiabilities;
    }

    /**
     * @param totalLiabilities the totalLiabilities to set
     */
    public void setTotalLiabilities(double totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    /**
     * @return int return the numberOfShares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * @param numberOfShares the numberOfShares to set
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

}
