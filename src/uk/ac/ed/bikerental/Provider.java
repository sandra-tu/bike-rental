package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import java.util.HashSet;

import java.util.HashMap;
import java.util.Objects;

public class Provider {
    
    private final Integer providerID;
    private String providerName;
    private Location providerAddress;
    private BigDecimal depositRate;
    private HashMap<BikeType, BigDecimal> dailyRentalPrice = new HashMap<>(); 

    private Set<Provider> partnerProviders = new HashSet<Provider>();
    private Set<Bike> stock = new HashSet<Bike>();
    private Set<Booking> providerBookings = new HashSet<Booking>();
    private boolean hasPricingPolicy;
    private MultidayPricingPolicy pricingPolicy;

    private static AtomicLong idCounter = new AtomicLong();

    //Recheck constructor given rental price constructor
    public Provider(String providerName, Location providerAddress, BigDecimal depositRate) {
        this.providerID = createProviderID();
        this.providerName = providerName;
        this.providerAddress = providerAddress;
        this.depositRate = depositRate;
    }
    
    private Integer createProviderID() { 
        return Integer.valueOf(String.valueOf(idCounter.getAndIncrement()));
      }
    
    public Integer getProviderID() {
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
    
    public Set<Booking> getBookings(){
        return this.providerBookings;
    }
    
    public MultidayPricingPolicy getPP() {
        return this.pricingPolicy;
    }
    
    public void setHasPP(boolean b) {
        this.hasPricingPolicy = b;
    }
    
    public void setPartnerProviders(Set<Provider> partners) {
        this.partnerProviders = partners;
    }
    
    public void setStock(Set<Bike> bikeStock) {
        this.stock = bikeStock;
    }
   
    public HashMap<BikeType, BigDecimal> setDailyRentalPrice(BikeType bikeType, BigDecimal price) {               
        if(!this.dailyRentalPrice.containsKey(bikeType)){
            this.dailyRentalPrice.put(bikeType, price);
        } else {
            this.dailyRentalPrice.replace(bikeType, price);
        }
        return this.dailyRentalPrice;
    }
    
    public void setPP(MultidayPricingPolicy pp) {
        this.pricingPolicy = pp;
        this.setHasPP(true);
    }
    
    public boolean hasPP() {
        return this.hasPricingPolicy;
    }
    
    public void addProviderBooking(Booking booking) {
        if (!this.equals(booking.getProvider())) {
            throw new IllegalArgumentException("This booking is not with this provider");
        }
        //Add booking to the provider
        this.providerBookings.add(booking); 
        Set<Bike> bikesInBooking = booking.getBikeCollection();
        //Add the booking to the relevant bikes
        for (Bike bike : bikesInBooking) {
            bike.addBikeBooking(booking);
        }                
    }
    
    //Returns the daily rental price for a given bike type
    public BigDecimal getDailyRentalPrice(BikeType bikeType){
        return (this.dailyRentalPrice).get(bikeType);
    }
    
    public HashMap<BikeType, BigDecimal> getDailyRentalPriceList(){
        return this.dailyRentalPrice;
    }
    
    public Set<BikeType> getStockedBikeTypes() {
        return dailyRentalPrice.keySet();
    }
    
    public Set<Bike> getProviderStock() {
        return this.stock;
    }
    
    public Set<Booking> getProviderBookings() {
        return this.providerBookings;
    }
    
    public void addPartnerProvider(Provider p) {
        this.partnerProviders.add(p);
    }
    
    public boolean verifyPartner(Provider p) {
        if(this.partnerProviders.contains(p) && p.partnerProviders.contains(this)){
            return true;
        } else {
            return false;
        }
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
}
    
