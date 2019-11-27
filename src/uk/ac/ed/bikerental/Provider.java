package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

import java.util.HashMap;
import java.util.Objects;

public class Provider {
    
    private final Integer providerID;
    private String providerName;
    private Location providerAddress;
    private BigDecimal depositRate;
    private HashMap<BikeType, BigDecimal> dailyRentalPrice; //change from dailyPrice to dailyRentalPrice in UML cwk2
    private Set<Provider> partnerProviders;
    private Set<Bike> stock;
    private Set<Booking> providerBookings;
    private Location address;
    private static AtomicLong idCounter = new AtomicLong();
    
    //Recheck constructor given rental price constructor
    public Provider(String providerName, Location providerAddress, BigDecimal depositRate, Set<Provider> partnerProviders) {
        this.providerID = createProviderID();
        this.providerName = providerName;
        this.providerAddress = providerAddress;
        this.depositRate = depositRate;
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
    
    public void setPartnerProviders(Set<Provider> partners) {
        this.partnerProviders = partners;
    }
    
    public void setStock(Set<Bike> bikeStock) {
        this.stock = bikeStock;
    }
   
    public HashMap<BikeType, BigDecimal> setDailyRentalPrice(BikeType bikeType, BigDecimal price) {
        HashMap<BikeType, BigDecimal> dailyRentalPrice = new HashMap<>();
               
        //Is there a way to automatically iterate through all values of enum class?
        //If BikeType no longer needs RentalPrice attribute then this chunk can be deleted
        if(!dailyRentalPrice.containsKey(bikeType)){
            dailyRentalPrice.put(bikeType, price);
        } else {
            dailyRentalPrice.replace(bikeType, price);
        }
        return dailyRentalPrice;
    }
    
    public void addBooking(Booking b) {
        this.providerBookings.add(b);
    }
    
    //Returns the daily rental price for a given bike type
    public BigDecimal getDailyRentalPrice(BikeType bikeType){
        return (this.dailyRentalPrice).get(bikeType);
    }
    
    public Set<BikeType> getStockedBikeTypes() {
        return dailyRentalPrice.keySet();
    }
    
    public Set<Bike> getProviderStock() {
        return this.stock;
    }
    
    public Location getAddress() {
        return this.address;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(providerID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Provider other = (Provider) obj;
        return Objects.equals(providerID, other.providerID);
    }
    
    public void printSummary() {
        System.out.println("Name:     " + getProviderName());
        System.out.println("ID:       " + getProviderID());
        System.out.println("Address:  " + getProviderAddress().formatAddress());
        System.out.println("DepositR: " + getDepositRate() + "\n");
    }

    
}
    
