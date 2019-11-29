package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Quote {
    private final Set<Bike> bikes = new HashSet<>();
    private final Provider provider;
    private final DateRange dateRange;
    private final Location locationOfHire;
    private BigDecimal totalRentalPrice;
    private BigDecimal totalDepositPrice;
    private boolean deliveryToCustomer;
    private boolean isPaid = false;
    
    public Quote(Set<Bike> bikes, Provider provider, DateRange dateRange, Location locationOfHire) {
        this.bikes.addAll(bikes);
        this.provider = provider;
        this.dateRange = dateRange;
        this.locationOfHire = locationOfHire;
        this.totalRentalPrice = this.calculateTotalDailyRentalPrice();
        this.totalDepositPrice = this.calculateTotalDepositPrice();
    }

    //SETTERS
    public void setDeliveryToCustomer(boolean delivery) {
        this.deliveryToCustomer = delivery;
    }
    
    public void setIsPaid(boolean b) {
        this.isPaid = b;
    }
    
    public BigDecimal calculateTotalDailyRentalPrice() {

        BigDecimal sum = BigDecimal.ZERO;
        Long numDaysLong = this.getDateRange().toDays() + 1;
        BigDecimal numDays = new BigDecimal(numDaysLong);
        
        for(Bike bike : this.bikes) {
            sum = sum.add(bike.getDailyRentalPrice());
        }
        if(this.provider.hasPP()) {
            sum = sum.multiply(numDays);
            sum = sum.multiply(BigDecimal.valueOf(this.provider.getPP().getDiscount(numDaysLong)));
            return sum;
        } else {
            return sum.multiply(numDays);
        }
    }
    
    private BigDecimal calculateTotalDepositPrice() {
        BigDecimal totalDepositPrice = BigDecimal.ZERO;
        BigDecimal depositRate = this.provider.getDepositRate();
        for (Bike bike : this.bikes) {
            BigDecimal depositPrice = bike.getFullReplaceVal().multiply(depositRate);
            totalDepositPrice = totalDepositPrice.add(depositPrice);
        }
        return totalDepositPrice;
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
    
    public void printSummary() {
        System.out.println("Bikes:                " + bikes);
        System.out.println("Provider:             " + provider);
        System.out.println("DateRange:            " + dateRange);
        System.out.println("locationOfHire:       " + locationOfHire);
        System.out.println("Total rental price:   " + totalRentalPrice);
        System.out.println("Total deposit price:  " + totalDepositPrice);
        System.out.println("Delivery to customer: " + deliveryToCustomer);
        System.out.println("Quote is paid for:    " + isPaid + "\n");
    }
 
}
