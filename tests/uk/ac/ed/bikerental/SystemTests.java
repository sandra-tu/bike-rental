package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
    // You can add attributes here

    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        // Put your test setup here
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(110.00));
        BikeType eBike = new BikeType(BikeTypes.EBIKE, new BigDecimal(120.00));
        BikeType otherBike = new BikeType(BikeTypes.OTHERBIKE, new BigDecimal(130.00));

        Location location1 = new Location("EH89QE", "Street name");
        Location location2 = new Location("EH165AY", "Street name");
        Location location3 = new Location("G138AB", "Street name");
        Location location4 = new Location("KY144AA", "Street name");
        
        Provider provider2 = new Provider();
        Provider provider1 = new Provider("Provider1", location1, new BigDecimal(0.2), null);
        
        Bike bike1 = new Bike();
        
    }
    
    // TODO: Write system tests covering the three main use cases

    @Test
    void myFirstTest() {
        // JUnit tests look like this
        assertEquals("The moon", "cheese"); // Should fail
    }
}
