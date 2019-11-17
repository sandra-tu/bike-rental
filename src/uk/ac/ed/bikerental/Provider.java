package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.HashMap;

public class Provider {
    
    private Integer providerID;
    private String providerName;
    private Location providerAddress;
    private BigDecimal depositRate;
    private HashMap<BikeType, BigDecimal> dailyRentalPrice; //change from dailyPrice to dailyRentalPrice in UML cwk2
    private Set<Provider> partnerProviders;
    private Set<Bike> stock;
    private static AtomicLong idCounter = new AtomicLong();
    
    public Provider(String providerName, Location providerAddress, BigDecimal depositRate,
            HashMap<BikeType, BigDecimal> dailyRentalPrice, Set<Provider> partnerProviders) {
        this.providerID = createProviderID();
        this.providerName = providerName;
        this.providerAddress = providerAddress;
        this.depositRate = depositRate;
        this.dailyRentalPrice = dailyRentalPrice;
        this.partnerProviders = partnerProviders;
    }
    
    public Integer createProviderID() {
        return Integer.valueOf(String.valueOf(idCounter.getAndIncrement()));
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
    
    public BigDecimal getDepositRate() {
        return depositRate;
    }
    
    //Returns the daily rental price for a given bike type
    public BigDecimal getDailyRentalPrice(BikeType bikeType){
        return (this.dailyRentalPrice).get(bikeType);
    }
    
    public void printSummary() {
        System.out.println("Name:     " + getProviderName());
        System.out.println("ID:       " + getProviderID());
        System.out.println("Address:  " + getProviderAddress());
        System.out.println("DepositR: " + getDepositRate() + "\n");
    }

    
}
    
