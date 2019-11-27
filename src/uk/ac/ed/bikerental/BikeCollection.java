package uk.ac.ed.bikerental;

import java.util.Set;

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
        
    }
    
    public void onDropoff() {
        
    }
}
