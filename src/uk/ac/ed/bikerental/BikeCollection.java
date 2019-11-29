package uk.ac.ed.bikerental;

import uk.ac.ed.bikerental.Booking.BookingStatuses;

public class BikeCollection implements Deliverable {
    private Booking booking;
    
    public BikeCollection(Booking b) {
        this.booking = b;
    }
    
    @Override
    public void onPickup() {
        this.booking.setBookingStatus(BookingStatuses.OUT_FOR_DELIVERY);
    }
    
    @Override
    public void onDropoff() {
        this.booking.setBookingStatus(BookingStatuses.IN_USE);
    }
}
