package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

public class MultidayPricingPolicy implements PricingPolicy{
    
    private int[] thresholds; //   [2, 6, 13]
    private double[] discountRates;// [0.05, 0.1, 0,15]
    private Provider provider;
    
    public MultidayPricingPolicy(int[] thresholds, double[] discountRates, Provider p) {
        this.thresholds = thresholds;
        this.discountRates = discountRates;
        this.provider = p;
    }
    
    public int[] getThresholds(){
        return this.thresholds;
    }
    
    public double[] getDiscountRates() {
        return this.discountRates;
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
        
        double discountRate = 0;
        for(int i = 0; i < this.thresholds.length; i++) {
            if(duration.toDays() < this.thresholds[i]) {
                discountRate = this.discountRates[i];
                break;
            }
        }
        BigDecimal total = rawTotal.multiply(BigDecimal.valueOf(discountRate));
        return total;
    }


}
