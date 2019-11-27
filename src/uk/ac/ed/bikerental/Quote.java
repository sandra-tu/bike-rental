package uk.ac.ed.bikerental;

import java.util.Objects;
import java.util.Set;
import java.math.BigDecimal;

public class Quote {
    private final Set<Bike> bikes;
    private final Provider provider;
    private final DateRange dateRange;
    private final Location locationOfHire;
    private BigDecimal totalRentalPrice;
    private BigDecimal totalDepositPrice;
    private boolean deliveryToCustomer;
    private boolean isPaid = false;
    
    public Quote(Set<Bike> bikes, Provider provider, DateRange dateRange, Location locationOfHire) {
        this.bikes = bikes;
        this.provider = provider;
        this.dateRange = dateRange;
        this.locationOfHire = locationOfHire;
        this.setTotalRentalPrice(this.bikes);
        this.setTotalDepositPrice(this.bikes, this.provider);
    }

    //SETTERS
    public void setDeliveryToCustomer(boolean delivery) {
        this.deliveryToCustomer = delivery;
    }
    
    public void setIsPaid() { //Should this now not rather be called setToPaid()
        this.isPaid = true;
    }
    
    public void setTotalRentalPrice(Set<Bike> bikeSet) {
        BigDecimal totalRentalPrice = new BigDecimal(0.00);
        for (Bike bike : bikeSet) {
            BigDecimal rentalPrice = new BigDecimal(0.00);
            rentalPrice = bike.getDailyRentalPrice();
            totalRentalPrice = totalRentalPrice.add(rentalPrice);
        }
        this.totalRentalPrice = totalRentalPrice;
    }
    
    private void setTotalDepositPrice(Set<Bike> bikeSet, Provider provider) {
        BigDecimal totalDepositPrice = new BigDecimal(0.00);
        BigDecimal depositRate = provider.getDepositRate();
        for (Bike bike : bikeSet) {
            BigDecimal depositPrice = bike.getFullReplaceVal().multiply(depositRate);
            totalDepositPrice = totalDepositPrice.add(depositPrice);
        }
        this.totalDepositPrice = totalDepositPrice;
    }
    
    //GETTERS
    public Set<Bike> getBikes() {return this.bikes;}
    public Provider getProvider() {return this.provider;}
    public DateRange getDateRange() {return this.dateRange;}
    public Location getLocationOfHire() {return this.locationOfHire;}
    public BigDecimal getTotalRentalPrice() {return this.totalRentalPrice;} 
    public BigDecimal getTotalDepositPrice() {return this.totalDepositPrice;}
    public boolean getDeliveryToCustomer() {return this.deliveryToCustomer;}
    public boolean getIsPaid() {return this.isPaid;}
    
    public BigDecimal calculateDailyRentalPrice() {
        BigDecimal dailySum = new BigDecimal("00.00");
        for(Bike bike : bikes) {
            dailySum = dailySum.add(bike.getDailyRentalPrice());
        }
        return dailySum;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(bikes, provider, dateRange, locationOfHire);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quote other = (Quote) obj;
        return Objects.equals(bikes, other.bikes) 
                && Objects.equals(provider, other.provider)
                && Objects.equals(dateRange, other.dateRange)
                && Objects.equals(locationOfHire, other.locationOfHire);
    }
 
}
