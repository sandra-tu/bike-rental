package uk.ac.ed.bikerental;

import java.util.Set;
import java.math.BigDecimal;

public class Quote {
    private Set<Bike> bikes;
    private Provider provider;
    private DateRange dateRange;
    private Location locationOfHire;
    private BigDecimal totalRentalPrice;
    private BigDecimal totalDepositPrice;
    private boolean deliveryToCustomer;
    private boolean isPaid = false;
    
    public Quote(Set<Bike> bikes, Provider provider, DateRange dateRange, Location locationOfHire) {
        this.bikes = bikes;
        this.provider = provider;
        this.dateRange = dateRange;
        this.locationOfHire = locationOfHire;
        this.totalRentalPrice = getTotalRentalPrice(this.bikes);
        this.totalDepositPrice = getTotalDepositPrice(this.bikes, this.provider);
    }

    //SETTERS
    public void setDeliveryToCustomer(boolean delivery) {
        this.deliveryToCustomer = delivery;
    }
    
    public void setIsPaid() {
        this.isPaid = true;
    }
    
    //GETTERS
    public Set<Bike> getBikes() {return bikes;}
    public Provider getProvider() {return provider;}
    public DateRange getDateRange() {return dateRange;}
    public Location getLocationOfHire() {return locationOfHire;}
    public BigDecimal getTotalRentalPrice() {return totalRentalPrice;} 
    public BigDecimal gettotalDepositPrice() {return totalDepositPrice;}
    public boolean getDeliveryToCustomer() {return deliveryToCustomer;}
    public boolean getIsPaid() {return isPaid;}
    
    public BigDecimal calculateDailyRentalPrice() {
        BigDecimal dailySum = new BigDecimal("00.00");
        for(Bike bike : bikes) {
            dailySum = dailySum.add(bike.getDailyRentalPrice());
        }
        return dailySum;
    }
    
    public BigDecimal getTotalRentalPrice(Set<Bike> bikeSet) {
        BigDecimal totalRentalPrice = new BigDecimal(0.00);
        for (Bike bike : bikeSet) {
            BigDecimal rentalPrice = new BigDecimal(0.00);
            rentalPrice = bike.getDailyRentalPrice();
            totalRentalPrice = totalRentalPrice.add(rentalPrice);
        }
        return totalRentalPrice;
    }
    
    private BigDecimal getTotalDepositPrice(Set<Bike> bikeSet, Provider provider) {
        BigDecimal totalDepositPrice = new BigDecimal(0.00);
        BigDecimal depositRate = provider.getDepositRate();
        for (Bike bike : bikeSet) {
            BigDecimal depositPrice = bike.getFullReplaceVal().multiply(depositRate);
            totalDepositPrice = totalDepositPrice.add(depositPrice);
        }
        return totalDepositPrice;
    }
 
}
