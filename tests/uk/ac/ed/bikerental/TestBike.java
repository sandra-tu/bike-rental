package uk.ac.ed.bikerental;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class TestBike {
    
    private Bike bike1, bike2, bike3;
    private Bike[] bikeArr;
    private Set<Bike> bikeset;
    private Booking booking1;
    private Quote quote1;
    private Provider provider1;
    
    @BeforeEach
    void setUp() {
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        this.bike1 = new Bike(provider1, mountainBike);
        this.bike2 = new Bike(provider1, mountainBike);
        this.bike3 = new Bike(provider1, mountainBike);

        this.provider1 = new Provider("Provider", new Location("EH20", "street"), 
                                       new BigDecimal("1.0"));
        this.bikeArr = new Bike[]{bike1, bike2, bike3};
        this.bikeset = new HashSet<>(Arrays.asList(bikeArr));
        
        this.quote1 = new Quote(bikeset, provider1, new DateRange(LocalDate.of(2000, 01, 01),
                      LocalDate.of(2000, 02, 02)), new Location("EH2020", "quoteLoc"));
        
        this.booking1 = new Booking(quote1, false);
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
        System.out.println(this.booking1 == null);
        this.bike1.setBookings(bookingSet);
        assertEquals(bookingSet, this.bike1.getBookings());
    }
    
    @Test
    public void testAddBooking() {
        Set<Booking> bookingSet = new HashSet<>();
        bookingSet.add(booking1);
        this.bike2.addBikeBooking(booking1);
        assertEquals(bookingSet, this.bike2.getBookings());
    }
    
}
