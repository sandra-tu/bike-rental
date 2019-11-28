package uk.ac.ed.bikerental;

import uk.ac.ed.bikerental.Booking.BookingStatuses;

public class BikeCollectionFromPartnerToMainProv implements Deliverable {
    private Booking booking;
    
    public BikeCollectionFromPartnerToMainProv(Booking b) {
        this.booking = b;
    }
    
    
    @Override
    public void onPickup() {
        this.booking.setBookingStatus(BookingStatuses.OVERNIGHT_RETURN);
    }

    @Override
    public void onDropoff() {
        this.booking.setBookingStatus(BookingStatuses.COMPLETE);
    }
    
}
