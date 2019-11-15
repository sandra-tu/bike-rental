package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Set;
import java.util.HashMap;

public class Provider {
    
    private int providerID;
    private String providerName;
    private Location providerAddress;
    private Set<Provider> partnerProviders;
    private Set<Bike> stock;
    private HashMap<BikeType, BigDecimal> dailyRentalPrice; //change from dailyPrice to dailyRentalPrice in UML cwk2
    
    public Provider() {
        
    }
    
    public int getProviderID() {
        return this.providerID;
    }
    
    public String getProviderName() {
        return this.providerName;
    }
    
    public Location getProviderAddress() {
        return this.providerAddress;
    }
    
    public HashMap<BikeType, BigDecimal> getDailyRentalPrice(){
        return this.dailyRentalPrice;
    }
}
    
