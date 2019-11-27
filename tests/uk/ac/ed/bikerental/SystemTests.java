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
    private BikeType mountainBike, roadBike, eBike, otherBike;
    private Location locationP1, locationP2, locationP3, locationP4;
    private Location locationC1, locationC2, locationC3, locationC4;
    private DateRange dateRange1, dateRange2;
    private Provider provider1, provider2, provider3, provider4;
    
    private Bike bike1_1, bike1_2, bike1_3, bike1_4;
    private Bike bike2_1, bike2_2, bike2_3, bike2_4, bike2_5, bike2_6, bike2_7;
    private Bike bike3_1, bike3_2, bike3_3, bike3_4, bike3_5;
    private Bike bike4_1, bike4_2, bike4_3, bike4_4, bike4_5, bike4_6, bike4_7, bike4_8;
    
    private ArrayList<BikeType> requestedBikes1, requestedBikes2, requestedBikes3;
    private Input input1, input2, input3, input4, input5, input6;
    private ArrayList<Quote> quotes1;
    

    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        // Put your test setup here
        
        //Bike types
        this.mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        this.roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(110.00));
        this.eBike = new BikeType(BikeTypes.EBIKE, new BigDecimal(120.00));
        this.otherBike = new BikeType(BikeTypes.OTHERBIKE, new BigDecimal(130.00));

        //Locations
        //(for Provider addresses)
        this.locationP1 = new Location("EH89QE", "Street name");
        this.locationP2 = new Location("EH165AY", "Street name");
        this.locationP3 = new Location("G138AB", "Street name");
        this.locationP4 = new Location("KY144AA", "Street name");
        //(for customer searches)
        this.locationC1 = new Location("EH45AH", "Street name");
        this.locationC2 = new Location("G149KL", "Street name");
        this.locationC3 = new Location("KY12AB", "Street name");
        this.locationC4 = new Location("LD12AH", "Street name");
        
        //Date ranges
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 3));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 5, 1));
        
        //Providers
        this.provider1 = new Provider("Provider1", locationP1, new BigDecimal(0.2), null);
        this.provider2 = new Provider("Provider2", locationP2, new BigDecimal(0.15), null);
        this.provider3 = new Provider("Provider3", locationP3, new BigDecimal(0.1), null);
        this.provider4 = new Provider("Provider4", locationP4, new BigDecimal(0.25), null);

        //Setup partner providers
        Set<Provider> partnersOf1 = new HashSet<>();
        partnersOf1.add(this.provider3);
        Set<Provider> partnersOf3 = new HashSet<>();
        partnersOf3.add(this.provider1); 
        partnersOf3.add(this.provider4);
        Set<Provider> partnersOf4 = new HashSet<>();
        partnersOf4.add(this.provider3);
        
        this.provider1.setPartnerProviders(partnersOf1);
        this.provider3.setPartnerProviders(partnersOf3);
        this.provider4.setPartnerProviders(partnersOf4);

        //Bikes and adding to stock
        this.bike1_1 = new Bike(provider1, mountainBike);
        this.bike1_2 = new Bike(provider1, mountainBike);
        this.bike1_3 = new Bike(provider1, mountainBike);
        this.bike1_4 = new Bike(provider1, mountainBike);
        Bike arrayProvider1[] = {bike1_1, bike1_2, bike1_3, bike1_4};
        Set<Bike> provider1Stock = new HashSet<>(Arrays.asList(arrayProvider1));
        this.provider1.setStock(provider1Stock);
        
        this.bike2_1 = new Bike(provider2, roadBike);
        this.bike2_2 = new Bike(provider2, roadBike);
        this.bike2_3 = new Bike(provider2, roadBike);
        this.bike2_4 = new Bike(provider2, roadBike);
        this.bike2_5 = new Bike(provider2, eBike);
        this.bike2_6 = new Bike(provider2, eBike);
        this.bike2_7 = new Bike(provider2, eBike);
        Bike arrayProvider2[] = {bike2_1, bike2_2, bike2_3, bike2_4, bike2_5, bike2_6, bike2_7};
        Set<Bike> provider2Stock = new HashSet<>(Arrays.asList(arrayProvider2));
        this.provider2.setStock(provider2Stock);

        this.bike3_1 = new Bike(provider3, mountainBike);
        this.bike3_2 = new Bike(provider3, roadBike);
        this.bike3_3 = new Bike(provider3, roadBike);
        this.bike3_4 = new Bike(provider3, eBike);
        this.bike3_5 = new Bike(provider3, eBike);
        Bike arrayProvider3[] = {bike3_1, bike3_2, bike3_3, bike3_4, bike3_5};
        Set<Bike> provider3Stock = new HashSet<>(Arrays.asList(arrayProvider3));
        this.provider3.setStock(provider3Stock);
        
        this.bike4_1 = new Bike(provider4, mountainBike);
        this.bike4_2 = new Bike(provider4, mountainBike);
        this.bike4_3 = new Bike(provider4, mountainBike);
        this.bike4_4 = new Bike(provider4, mountainBike);
        this.bike4_5 = new Bike(provider4, eBike);
        this.bike4_6 = new Bike(provider4, eBike);
        this.bike4_7 = new Bike(provider4, eBike);
        this.bike4_8 = new Bike(provider4, eBike);
        Bike arrayProvider4[] = {bike4_1, bike4_2, bike4_3, bike4_4, bike4_5, bike4_6, 
                bike4_7, bike4_8};
        Set<Bike> provider4Stock = new HashSet<>(Arrays.asList(arrayProvider4));
        this.provider4.setStock(provider4Stock);
        
        //ArrayList of BikeType for Input
        this.requestedBikes1 = new ArrayList<>();
        this.requestedBikes1.add(mountainBike);
        
        this.requestedBikes2 = new ArrayList<>();
        this.requestedBikes2.add(mountainBike);
        this.requestedBikes2.add(mountainBike);
        this.requestedBikes2.add(mountainBike);

        this.requestedBikes3 = new ArrayList<>();
        this.requestedBikes3.add(mountainBike);
        this.requestedBikes3.add(roadBike);
        this.requestedBikes3.add(eBike);
        
        //Inputs
        this.input1 = new Input(dateRange1, this.requestedBikes1, locationC1);
        this.input2 = new Input(dateRange1, this.requestedBikes1, locationC4); //Should return no quotes
        this.input3 = new Input(dateRange2, this.requestedBikes2, locationC1);
        this.input4 = new Input(dateRange1, this.requestedBikes3, locationC1);
        this.input5 = new Input(dateRange1, this.requestedBikes2, locationC2);
        this.input6 = new Input(dateRange1, this.requestedBikes3, locationC3);
        
        //Generated Quotes
        Controller c = new Controller();
        this.quotes1 = c.generateQuotes(input1);
        
        //Quotes
        

    }
    
    // TODO: Write system tests covering the three main use cases

    @Test
    void myFirstTest() {
        // JUnit tests look like this
        assertEquals("The moon", "cheese"); // Should fail
        
    }
    
    //Tests: Use Case 1 - Finding a quote
    
    //Test 1.1: Checks each quote only has bikes from one provider
    
    //Test 1.2: Checks provider field in quote mathces the actual provider of the bikes
    
    //Test 1.3: Checks the quoted providers are in range
    
    //Test 1.4: Checks the returned bikes are of the type
    
    //Test 1.5: Checks all the bikes are availible for the required dateRange
    
    //Test 1.6: Checks daily rental price is correct
    
    //Test 1.7: Checks desposit price is correct
    
    
    
    
    
    //Tests: Use Case 2 - Booking a quote
//    Place a booking 
//    Unique booking number
//    If customer has requested DeliveryService then should return
    
    //Test invoice details
    
    
    
    
    //Test: Use Case 3 - Returning bikes
}
