package uk.ac.ed.bikerental;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

import java.util.HashSet;

import java.util.HashMap;
import java.util.Objects;

public class Provider {
    
    private final Integer providerID;
    private String providerName;
    private Location providerAddress;
    private BigDecimal depositRate;
    private HashMap<BikeType, BigDecimal> dailyRentalPrice = new HashMap<>(); //change from dailyPrice to dailyRentalPrice in UML cwk2

    private Set<Provider> partnerProviders = new HashSet<Provider>();
    private Set<Bike> stock = new HashSet<Bike>();
    private Set<Booking> providerBookings = new HashSet<Booking>();
    private Location address = new Location("EH165AY", "test");

    private static AtomicLong idCounter = new AtomicLong();
    
    //Recheck constructor given rental price constructor
    public Provider(String providerName, Location providerAddress, BigDecimal depositRate) {
        this.providerID = createProviderID();
        this.providerName = providerName;
        this.providerAddress = providerAddress;
        this.depositRate = depositRate;
    }
    
    public Integer createProviderID() { //make private?
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
    
    public Set<BikeType> getStockedBikeTypes() {
        return dailyRentalPrice.keySet();
    }
    
    public Set<Bike> getProviderStock() {
        return this.stock;
    }
    
    public Set<Booking> getProviderBookings() {
        return this.providerBookings;
    }
    
    public Location getAddress() {
        return this.address;
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
    
    public void printSummary() {
        System.out.println("Name:     " + getProviderName());
        System.out.println("ID:       " + getProviderID());
        System.out.println("Address:  " + getProviderAddress().formatAddress());
        System.out.println("DepositR: " + getDepositRate() + "\n");
    }

    public static void main(String[] args) {
        Location loc = new Location("EH165AY", "Street");
        Provider prov = new Provider("name", loc, new BigDecimal(0.2));
        BikeType eBike = new BikeType(BikeTypes.EBIKE, new BigDecimal(100.00));
        prov.setDailyRentalPrice(eBike, new BigDecimal(100.00));
        System.out.println(prov.getDailyRentalPrice(eBike));
    }
    
}
    
