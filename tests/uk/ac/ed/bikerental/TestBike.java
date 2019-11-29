package uk.ac.ed.bikerental;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class TestBike {
    
    private static Bike bike1, bike2, bike3;
    private static Bike[] bikeArr;
    private static Set<Bike> bikeset;
    private static Booking booking1;
    private static Quote quote1;
    private static Provider provider1;
    private static BikeType mountainBike;
    private static int boop = 1;
    
    @BeforeEach
    public static void setUp() {
        System.out.println("boo");
        provider1 = new Provider("Provider", new Location("EH20lklkj", "street"), 
                new BigDecimal("1.0"));

        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        System.out.println("boo");

        bike1 = new Bike(provider1, mountainBike);
        bike2 = new Bike(provider1, mountainBike);
        bike3 = new Bike(provider1, mountainBike);
        
        System.out.println(bike1.getBikeID());
        
        bikeArr = new Bike[]{bike1, bike2, bike3};
        bikeset = new HashSet<>(Arrays.asList(bikeArr));
        
        quote1 = new Quote(bikeset, provider1, new DateRange(LocalDate.of(2000, 01, 01),
                      LocalDate.of(2000, 02, 02)), new Location("EH2020", "quoteLoc"));
        quote1.setIsPaid(true);
        booking1 = new Booking(quote1, false);
        
        //provider1.addProviderBooking(booking1);
        boop++;
    }
    
    @Test
    public void testsRunnging() {
        System.out.println("Tests running");
        System.out.println(boop);

        //bike1 = new Bike(provider1, mountainBike);
        //System.out.println("Tests running");
        System.out.print("Quote null: ");
        System.out.println(quote1 == null);
        
        System.out.print("Bike null: ");
        System.out.println(bike1 == null);
        
        System.out.print("BikeType null: ");
        System.out.println(mountainBike == null);
        System.out.println(mountainBike);
        
        System.out.print("Provider null: ");
        System.out.println(provider1 == null);
        System.out.println(provider1.getProviderID());
        
        assertTrue(true);
    }
    
    //Test that the createBikeID method is working correctly
    @Test
    public void testBikeID() {
        assertEquals((Integer) 1, bike1.getBikeID());
        assertEquals((Integer) 2, bike2.getBikeID());
        assertEquals((Integer) 3, bike3.getBikeID());
    }
    
    @Test
    public void testSetBookings() {
//        Set<Booking> bookingSet = new HashSet<>();
//        bookingSet.add(booking1);
//        System.out.println(" " + booking1 == null);
//        bike1.setBookings(bookingSet);
        bike1.addBikeBooking(booking1);
        assertEquals(booking1, bike1.getBookings());
    }
    
    @Test
    public void testAddBooking() {
        Set<Booking> bookingSet = new HashSet<>();
        bookingSet.add(booking1);
        bike2.addBikeBooking(booking1);
        assertEquals(bookingSet, bike2.getBookings());
    }
    
}
