package uk.ac.ed.bikerental;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

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
        assertNotNull(provider.getDailyRentalPrice(bikeType));
        this.dailyRentalPrice = provider.getDailyRentalPrice(bikeType);
        assertNotNull(this.dailyRentalPrice);
        this.bikeStatus = BikeStatuses.AT_MAIN_PROVIDER;
    }

    public Integer getBikeID() {
        return this.id;
    }

    public Integer getProviderID() {
        return this.provider.getProviderID();
    }

    private Integer createBikeID() {
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
    
    //This method should only be called by provider
    public void addBikeBooking(Booking booking) {
        assertEquals(booking.getProvider(), this.provider);
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
}
