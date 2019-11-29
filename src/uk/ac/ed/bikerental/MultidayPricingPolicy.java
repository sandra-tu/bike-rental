package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MultidayPricingPolicy implements PricingPolicy{
    
    private int[] thresholds;       // Bottom of the day range (lower bound)    
    private double[] discountRates; // The discount rates per category
    private Provider provider;
    
    public MultidayPricingPolicy(int[] thresholds, double[] discountRates, Provider p) {
        this.thresholds = thresholds;
        for (int threshold : this.thresholds) {
            if (threshold < 0 ) {
                throw new IllegalArgumentException("The threshold of a day range "
                    + "cannot be negative");
            }
        }
        this.discountRates = discountRates;
        for (double discountRate : this.discountRates) {
            if (discountRate < 0 || discountRate > 1) {
                throw new IllegalArgumentException("The discount should not be a negative "
                    + "value or more than 1");
            }
        }
        if (thresholds.length != discountRates.length) {
            throw new IllegalArgumentException("Threshold must contain same number of elements "
                    + "as discount rates");
        }
        int thresholdsCopy[] = Arrays.copyOf(thresholds, thresholds.length);
        Arrays.sort(thresholdsCopy);
        if (Arrays.equals(thresholdsCopy, thresholds)) {
            throw new IllegalArgumentException("The days in threshold argument must be in "
                    + "order, and correspond to the values in the discount array");
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
        double discountRate = 0.00; //If the number of days is less than the first 
        //threshold in the given array
        for(int i = 0; i < this.thresholds.length; i++) {
            if(days >= this.thresholds[i]) {
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
    
    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal price) {
        this.provider.setDailyRentalPrice(bikeType, price);
    }
    
    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal costPerDay = BigDecimal.ZERO; //Cost of renting the bikes per day
        for(Bike bike : bikes) {
            costPerDay = costPerDay.add(bike.getDailyRentalPrice());
        }
        //Cost of renting the bikes for the duration before discount is applied
        BigDecimal totalBeforeDiscount = costPerDay.multiply(BigDecimal.valueOf(duration.toDays() + 1));
        //Discount recieved for given duration
        BigDecimal discountRate = BigDecimal.valueOf(1 - this.getDiscount(duration.toDays()));
        //The total rental price of the bike for the given duration after discount
        BigDecimal total = totalBeforeDiscount.multiply(discountRate);
        return total;
    }


}
