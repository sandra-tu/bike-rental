package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

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
    
    //Recheck constructor given rental price constructor
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
    
  //Should be final only if not null (how do we do that???)
    public HashMap<BikeType, BigDecimal> setDailyRentalPrices(BigDecimal roadBRentalPrice,
        BigDecimal mountainBRentalPrice, 
        BigDecimal hybridBRentalPrice, 
        BigDecimal eBRentalPrice,
        BigDecimal otherBRentalPrice) {
        
        HashMap<BikeType, BigDecimal> DailyRentalPrices = new HashMap<>();
        
        
        //Is there a way to automatically iterate through all values of enum class?
        //If BikeType no longer needs RentalPrice attribute then this chunk can be deleted
        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, roadBRentalPrice);
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, mountainBRentalPrice);
        BikeType hybridBike = new BikeType(BikeTypes.HYBRIDBIKE, hybridBRentalPrice);
        BikeType eBike = new BikeType(BikeTypes.EBIKE, eBRentalPrice);
        BikeType otherBike = new BikeType(BikeTypes.OTHERBIKE, otherBRentalPrice);
        
        DailyRentalPrices.put(mountainBike, mountainBike.getDailyRentalPrice());
        DailyRentalPrices.put(roadBike, roadBike.getDailyRentalPrice());
        DailyRentalPrices.put(hybridBike, hybridBike.getDailyRentalPrice());
        DailyRentalPrices.put(eBike, eBike.getDailyRentalPrice());
        DailyRentalPrices.put(otherBike, otherBike.getDailyRentalPrice());

        return DailyRentalPrices;
    }
    
    //Returns the daily rental price for a given bike type
    public BigDecimal getDailyRentalPrice(BikeType bikeType){
        return (this.dailyRentalPrice).get(bikeType);
    }
    
    public void printSummary() {
        System.out.println("Name:     " + getProviderName());
        System.out.println("ID:       " + getProviderID());
        System.out.println("Address:  " + getProviderAddress().formatAddress());
        System.out.println("DepositR: " + getDepositRate() + "\n");
    }

    
}
    
