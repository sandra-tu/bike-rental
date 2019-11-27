package uk.ac.ed.bikerental;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class TestBike {
    
    private Bike bike1, bike2, bike3;
    private Booking booking1;
    private Quote quote1;
    private Provider provider1;
    
    @BeforeEach
    void setUp() {
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        this.bike1 = new Bike(null, mountainBike);
        this.bike2 = new Bike(null, mountainBike);
        this.bike3 = new Bike(null, mountainBike);
        
        this.booking1 = new Booking(null);
    }
    
    //Test that the createBikeID method is working correctly
    @Test
    public void testBikeID() {
        assertEquals((Integer) 1, this.bike1.getBikeID());
        assertEquals((Integer) 2, this.bike2.getBikeID());
        assertEquals((Integer) 3, this.bike3.getBikeID());
    }
    
    @Test
    public void testSetBookings() {
        Set<Booking> bookingSet = new HashSet<>();
        bookingSet.add(booking1);
        System.out.println(this.booking1 == null); //Why is the whole object null?!?!?!?!?! // HMMMMMM
        this.bike1.setBookings(bookingSet);
        assertEquals(bookingSet, this.bike1.getBookings());
    }
    
    @Test
    public void testAddBooking() {
        Set<Booking> bookingSet = new HashSet<>();
        bookingSet.add(booking1);
        this.bike2.addBooking(booking1);
        assertEquals(bookingSet, this.bike2.getBookings());
    }
}
