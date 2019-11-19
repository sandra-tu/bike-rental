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
    private boolean isPaid;
    
    
    //GETTERS
    public Set<Bike> getBikes() {return bikes;}
    public Provider getProvider() {return provider;}
    public DateRange getDateRange() {return dateRange;}
    public Location getLocationOfHire() {return locationOfHire;}
    public BigDecimal getTotalRentalPrice() {return totalRentalPrice;} 
    public BigDecimal gettotalDepositPrice() {return totalDepositPrice;}
    public boolean getDeliveryToCustomer() {return deliveryToCustomer;}
    public boolean getIsPaid() {return isPaid;}
}
