package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class Bike implements Deliverable{

    private Integer id;
    private Provider provider;
    private BikeType bikeType;
    private BigDecimal replacementValue;
    private BigDecimal dailyRentalPrice;
    private BikeStatuses bikeStatus;
    private Set<Booking> bookings;
    private static AtomicLong idCounter = new AtomicLong();

    public Bike(Provider provider, BikeType bikeType) {
        this.id = createBikeID();
        this.provider = provider;
        this.bikeType = bikeType;
        this.replacementValue = bikeType.getReplacementValue();
        this.dailyRentalPrice = provider.getDailyRentalPrice(bikeType);
        this.bikeStatus = BikeStatuses.AT_MAIN_PROVIDER;
    }

    public Integer getBikeID() {
        return id;
    }

    public int getProviderID() {
        return this.provider.getProviderID();
    }

    public Integer createBikeID() {
        return Integer.valueOf(String.valueOf(idCounter.getAndIncrement()));
    }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return this.bikeType;
    }
    
    public BigDecimal getFullReplaceVal() {
        return replacementValue;
    }
    
    public BigDecimal getDailyRentalPrice() {
        return dailyRentalPrice;
    }
    
    public Set<Booking> getBookings() {
        return this.bookings;
    }
    
    public enum BikeStatuses {
        AT_MAIN_PROVIDER,
        AT_PARTNER,
        IN_USE,
        OUT_FOR_DELIVERY,
        OVERNIGHT_TRANSIT
    }
    
    public void setBikeStatus(BikeStatuses status) {
        this.bikeStatus = status;
    }
    
    public BikeStatuses getBikeStatus() {
        return this.bikeStatus;
    }
    
    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
    
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
    
    public void onPickup() {
        
    }
    
    public void onDropoff() {
        
    }
    
    public ArrayList<DateRange> getDateRangesBooked() {
        ArrayList<DateRange> bookedDates = new ArrayList<>();
        for (Booking booking : bookings) {
            bookedDates.add(booking.getBookingDateRange());
        }
        return bookedDates;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bike other = (Bike) obj;
        return Objects.equals(id, other.id);
    }


//    public void printSummary() {
//        System.out.println("ID:          " + getBikeID());
//        System.out.println("Provided by: " + getProviderID());
//        System.out.println("Full replacement value: " + getFullReplaceVal());
//        //System.out.println("Daily rental Price:     " + getDailyRentalPrice() + "\n");
//    }
    
//    public static void main(String[] args) {
//        Location location1 = new Location("EH165AY", "22 Holyrood Park Road");
//        HashMap<BikeType, BigDecimal> DailyRentalPrices = new HashMap<>();
//        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE);
//        //BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(30.0));
//        
//        //DailyRentalPrices.put(mountainBike, new BigDecimal(5.00));
//        //DailyRentalPrices.put(roadBike, new BigDecimal(4.00));
//        
//        Provider provider1 = new Provider("Provider1", location1, new BigDecimal(0.2), null);
//        //Provider provider2 = new Provider("Provider2", location1, new BigDecimal(0.3), 
//        //        DailyRentalPrices, null);
//        //provider1.printSummary();
//        //provider2.printSummary();
//        
//        Bike bike1 = new Bike(provider1, mountainBike);
//        Bike bike2 = new Bike(provider1, mountainBike);
//        Bike bike3 = new Bike(provider1, mountainBike);
//        bike1.printSummary();
//        bike2.printSummary();
//        bike3.printSummary();        
//    }
}
