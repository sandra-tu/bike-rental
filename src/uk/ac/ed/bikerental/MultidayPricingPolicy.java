package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

public class MultidayPricingPolicy implements PricingPolicy{
    
    private int[] thresholds; //   
    private double[] discountRates;// 
    private Provider provider;
    
    public MultidayPricingPolicy(int[] thresholds, double[] discountRates, Provider p) {
        this.thresholds = thresholds;
        this.discountRates = discountRates;
        if(thresholds.length != discountRates.length) {
            throw new IllegalArgumentException("Threshold must contain same number of elements" +
                                               " as discount rates");
        }
        this.provider = p;
        p.setPP(this);
    }
    
    public int[] getThresholds(){
        return this.thresholds;
    }
    
    public double[] getDiscountRates() {
        return this.discountRates;
    }
    
    public double getDiscount(long days) {
        double discountRate = 1;
        for(int i = 0; i < this.thresholds.length; i++) {
            if(days > this.thresholds[i]) {
                discountRate = this.discountRates[i];
            }
        }
        return discountRate;
    }
    
    public void setThresholds(int[] t) {
        this.thresholds = t;
    }
    
    public void setDiscountRates(double[] t) {
        this.discountRates = t;
    }
    
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal price) {
        this.provider.setDailyRentalPrice(bikeType, price);
    }
    
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal rawTotal = BigDecimal.ZERO;
        for(Bike bike : bikes) {
            rawTotal = rawTotal.add(bike.getDailyRentalPrice());
        }
        
        double discountRate = this.getDiscount(duration.toDays());
        
        BigDecimal dayTotal = rawTotal.multiply(BigDecimal.valueOf(duration.toDays() + 1));
        BigDecimal total = dayTotal.multiply(BigDecimal.valueOf(discountRate));
        return total;
    }


}
