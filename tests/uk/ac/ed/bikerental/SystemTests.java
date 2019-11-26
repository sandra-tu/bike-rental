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
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
    // You can add attributes here

    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        // Put your test setup here
        
        //BikeType
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(110.00));
        BikeType eBike = new BikeType(BikeTypes.EBIKE, new BigDecimal(120.00));
        BikeType otherBike = new BikeType(BikeTypes.OTHERBIKE, new BigDecimal(130.00));

        //Locations
        Location location1 = new Location("EH89QE", "Street name");
        Location location2 = new Location("EH165AY", "Street name");
        Location location3 = new Location("G138AB", "Street name");
        Location location4 = new Location("KY144AA", "Street name");
        
        //Providers
        Provider provider1 = new Provider("Provider1", location1, new BigDecimal(0.2), null);
        Provider provider2 = new Provider("Provider2", location2, new BigDecimal(0.15), null);
        Provider provider3 = new Provider("Provider3", location3, new BigDecimal(0.1), null);
        Provider provider4 = new Provider("Provider4", location4, new BigDecimal(0.25), null);

        //Setup partner providers
        Set<Provider> partnersOf1 = new HashSet<>();
        partnersOf1.add(provider3);
        Set<Provider> partnersOf3 = new HashSet<>();
        partnersOf3.add(provider1); 
        partnersOf3.add(provider4);
        Set<Provider> partnersOf4 = new HashSet<>();
        partnersOf4.add(provider3);
        
        provider1.setPartnerProviders(partnersOf1);
        provider3.setPartnerProviders(partnersOf3);
        provider4.setPartnerProviders(partnersOf4);

        //Bikes and adding to stock
        Bike bike1_1 = new Bike(provider1, mountainBike);
        Bike bike1_2 = new Bike(provider1, mountainBike);
        Bike bike1_3 = new Bike(provider1, mountainBike);
        Bike bike1_4 = new Bike(provider1, mountainBike);
        Bike arrayProvider1[] = {bike1_1, bike1_2, bike1_3, bike1_4};
        Set<Bike> provider1Stock = new HashSet<>(Arrays.asList(arrayProvider1));
        provider1.setStock(provider1Stock);
        
        Bike bike2_1 = new Bike(provider2, roadBike);
        Bike bike2_2 = new Bike(provider2, roadBike);
        Bike bike2_3 = new Bike(provider2, roadBike);
        Bike bike2_4 = new Bike(provider2, roadBike);
        Bike bike2_5 = new Bike(provider2, eBike);
        Bike bike2_6 = new Bike(provider2, eBike);
        Bike bike2_7 = new Bike(provider2, eBike);
        Bike arrayProvider2[] = {bike2_1, bike2_2, bike2_3, bike2_4, bike2_5, bike2_6, bike2_7};
        Set<Bike> provider2Stock = new HashSet<>(Arrays.asList(arrayProvider2));
        provider2.setStock(provider2Stock);

        Bike bike3_1 = new Bike(provider3, mountainBike);
        Bike bike3_2 = new Bike(provider3, roadBike);
        Bike bike3_3 = new Bike(provider3, roadBike);
        Bike bike3_4 = new Bike(provider3, eBike);
        Bike bike3_5 = new Bike(provider3, eBike);
        Bike arrayProvider3[] = {bike3_1, bike3_2, bike3_3, bike3_4, bike3_5};
        Set<Bike> provider3Stock = new HashSet<>(Arrays.asList(arrayProvider3));
        provider3.setStock(provider3Stock);
        
        Bike bike4_1 = new Bike(provider4, mountainBike);
        Bike bike4_2 = new Bike(provider4, mountainBike);
        Bike bike4_3 = new Bike(provider4, mountainBike);
        Bike bike4_4 = new Bike(provider4, mountainBike);
        Bike bike4_5 = new Bike(provider4, eBike);
        Bike bike4_6 = new Bike(provider4, eBike);
        Bike bike4_7 = new Bike(provider4, eBike);
        Bike bike4_8 = new Bike(provider4, eBike);
        Bike arrayProvider4[] = {bike4_1, bike4_2, bike4_3, bike4_4, bike4_5, bike4_6, 
                bike4_7, bike4_8};
        Set<Bike> provider4Stock = new HashSet<>(Arrays.asList(arrayProvider4));
        provider4.setStock(provider4Stock);

        Input input1 = new Input();
        
        
    }
    
    // TODO: Write system tests covering the three main use cases

    @Test
    void myFirstTest() {
        // JUnit tests look like this
        assertEquals("The moon", "cheese"); // Should fail
    }
}
