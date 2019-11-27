package uk.ac.ed.bikerental;

import java.util.Set;

import uk.ac.ed.bikerental.Booking.BookingStatuses;

public class BikeCollection implements Deliverable{
    private Booking booking;
    private Set<Bike> bikeSet;
    private Provider provider;
    
    public BikeCollection(Booking b) {
        this.booking = b;
        this.bikeSet = b.getBikeCollection();
        this.provider = b.getProvider();
    }
    
    
    public void onPickup() {
        this.booking.setBookingStatus(BookingStatuses.OUT_FOR_DELIVERY);
    }
    
    public void onDropoff() {
        this.booking.setBookingStatus(BookingStatuses.IN_USE);
    }
}
