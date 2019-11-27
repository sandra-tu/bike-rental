package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class Bike{

    private final Integer id;
    private Provider provider;
    private BikeType bikeType;
    private BigDecimal replacementValue;
    private BigDecimal dailyRentalPrice;
    private BikeStatuses bikeStatus;
    private Set<Booking> bookings = new HashSet<>();
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
        return this.id;
    }

    public Integer getProviderID() {
        return this.provider.getProviderID();
    }

    //Should this method not perhaps be private?
    //Or maybe it doesn't need to be if the id field is final
    public Integer createBikeID() {
        return Integer.valueOf(String.valueOf(idCounter.getAndIncrement()));
    }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return this.bikeType;
    }
    
    public BigDecimal getFullReplaceVal() {
        return this.replacementValue;
    }
    
    public BigDecimal getDailyRentalPrice() {
        return this.dailyRentalPrice;
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
        this.bookings.addAll(bookings);
        if (bookings != null) {
            assert(this.bookings != null);
        }
    }
    
    public void addBikeBooking(Booking booking) {
        this.bookings.add(booking);
        if (booking != null) {
            assert(this.bookings != null);
        }
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


    public void printSummary() {
        System.out.println("ID:          " + getBikeID());
        System.out.println("Provided by: " + getProviderID());
        System.out.println("Full replacement value: " + getFullReplaceVal());
        System.out.println("Daily rental Price:     " + getDailyRentalPrice());
        System.out.println("Bookings: " + getBookings() + "\n");

    }
    
    public static void main(String[] args) {
        Location loc = new Location("EH165AY", "Street");
        BikeType bikeType = new BikeType(BikeTypes.EBIKE, new BigDecimal(100.00));
        Provider prov = new Provider("name", loc, new BigDecimal(0.2));
        prov.setDailyRentalPrice(bikeType, new BigDecimal(2.00));
        Bike bike = new Bike(prov, bikeType);
        Set<Bike> bikeSet = new HashSet<>();
        bikeSet.add(bike);
        Quote quoteMock = new Quote(bikeSet, prov, 
                new DateRange(LocalDate.of(1,1,1), LocalDate.of(1,1,1)), loc);
        Booking bookingMock = new Booking(quoteMock, false);
        System.out.println(bookingMock);
        bike.addBikeBooking(bookingMock);
        
        bike.printSummary();
    }
}
