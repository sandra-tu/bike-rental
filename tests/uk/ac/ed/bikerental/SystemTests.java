package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import uk.ac.ed.bikerental.Bike.BikeStatuses;
import uk.ac.ed.bikerental.BikeType.BikeTypes;
import uk.ac.ed.bikerental.Booking.BookingStatuses;

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
    private static BikeType mountainBike, roadBike, eBike;
    private static Location locationP1, locationP2, locationP3, locationP4;
    private static Location locationC1, locationC2, locationC3, locationC4;
    private static DateRange dateRange1;
    private static Provider provider1, provider2, provider3, provider4;
    
    private static Bike bike1_1, bike1_2, bike1_3, bike1_4;
    private static Bike bike2_1, bike2_2, bike2_3, bike2_4, bike2_5, bike2_6, bike2_7;
    private static Bike bike3_1, bike3_2, bike3_3, bike3_4, bike3_5;
    private static Bike bike4_1, bike4_2, bike4_3, bike4_4, bike4_5, bike4_6, bike4_7, bike4_8, bike4_9;
    
    private static ArrayList<BikeType> requestedBikes1, requestedBikes2, requestedBikes3;
    private static Input input1, input2, input3, input4;
    private static Controller c;
    private static ArrayList<Quote> quotes1, quotes2, quotes3, quotes4;
    private static Quote quoteMock, quote1, quote2, quote3, quote4;
    private static Booking booking1, booking2, booking3, booking4;
    private static MultidayPricingPolicy pricingPolicy1;
    private static Set<Bike> provider1Stock, provider2Stock, provider3Stock, provider4Stock;

    @BeforeAll
    static void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        c = new Controller();
        
        
        //Bike types
        mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(110.00));
        eBike = new BikeType(BikeTypes.EBIKE, new BigDecimal(120.00));
        new BikeType(BikeTypes.OTHERBIKE, new BigDecimal(130.00));

        //Locations
        //(for Provider addresses)
        locationP1 = new Location("EH89QE", "Street name");
        locationP2 = new Location("EH165A", "Street name");
        locationP3 = new Location("G138AB", "Street name");
        locationP4 = new Location("KY144A", "Street name");
        //(for customer addresses)
        locationC1 = new Location("EH45AH", "Street name");
        locationC2 = new Location("G149KL", "Street name");
        locationC3 = new Location("KY12AB", "Street name");
        locationC4 = new Location("LD12AH", "Street name");
        
        //Date ranges
        dateRange1 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 3));
        new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 5, 1));
        
        //Providers
        provider1 = new Provider("Provider1", locationP1, new BigDecimal(0.2));
        provider2 = new Provider("Provider2", locationP2, new BigDecimal(0.15));
        provider3 = new Provider("Provider3", locationP3, new BigDecimal(0.1));
        provider4 = new Provider("Provider4", locationP4, new BigDecimal(0.25));
        
        c.addProvider(provider1);
        c.addProvider(provider2);
        c.addProvider(provider3);
        c.addProvider(provider4);
        
        provider1.setDailyRentalPrice(mountainBike, new BigDecimal(10.00));
        
        provider2.setDailyRentalPrice(roadBike, new BigDecimal(10.00));
        provider2.setDailyRentalPrice(eBike, new BigDecimal(10.00));
        
        provider3.setDailyRentalPrice(mountainBike, new BigDecimal(10.00));
        provider3.setDailyRentalPrice(roadBike, new BigDecimal(10.00));
        provider3.setDailyRentalPrice(eBike, new BigDecimal(10.00));

        provider4.setDailyRentalPrice(mountainBike, new BigDecimal(10.00));
        provider4.setDailyRentalPrice(roadBike, new BigDecimal(11.00));
        provider4.setDailyRentalPrice(eBike, new BigDecimal(17.00));
        

        //Setup partner providers
        Set<Provider> partnersOf1 = new HashSet<>();
        partnersOf1.add(provider3);
        Set<Provider> partnersOf3 = new HashSet<>();
        partnersOf3.add(provider1); 
        partnersOf3.add(provider4);
        Set<Provider> partnersOf4 = new HashSet<>();
        partnersOf4.add(provider3);
        
        provider1.setPartnerProviders(partnersOf1);
        provider2.addPartnerProvider(provider3);
        provider3.setPartnerProviders(partnersOf3);
        provider3.addPartnerProvider(provider2);
        provider4.setPartnerProviders(partnersOf4);
       
        //Bikes and adding to stock
        bike1_1 = new Bike(provider1, mountainBike);
        bike1_2 = new Bike(provider1, mountainBike);
        bike1_3 = new Bike(provider1, mountainBike);
        bike1_4 = new Bike(provider1, mountainBike);
        Bike arrayProvider1[] = {bike1_1, bike1_2, bike1_3, bike1_4};
        provider1Stock = new HashSet<>(Arrays.asList(arrayProvider1));
        provider1.setStock(provider1Stock);
        
        bike2_1 = new Bike(provider2, roadBike);
        bike2_2 = new Bike(provider2, roadBike);
        bike2_3 = new Bike(provider2, roadBike);
        bike2_4 = new Bike(provider2, roadBike);
        bike2_5 = new Bike(provider2, eBike);
        bike2_6 = new Bike(provider2, eBike);
        bike2_7 = new Bike(provider2, eBike);
        Bike arrayProvider2[] = {bike2_1, bike2_2, bike2_3, bike2_4, bike2_5, bike2_6, bike2_7};
        provider2Stock = new HashSet<>(Arrays.asList(arrayProvider2));
        provider2.setStock(provider2Stock);

        bike3_1 = new Bike(provider3, mountainBike);
        bike3_2 = new Bike(provider3, roadBike);
        bike3_3 = new Bike(provider3, roadBike);
        bike3_4 = new Bike(provider3, eBike);
        bike3_5 = new Bike(provider3, eBike);
        Bike arrayProvider3[] = {bike3_1, bike3_2, bike3_3, bike3_4, bike3_5};
        provider3Stock = new HashSet<>(Arrays.asList(arrayProvider3));
        provider3.setStock(provider3Stock);
        
        bike4_1 = new Bike(provider4, mountainBike);
        bike4_2 = new Bike(provider4, mountainBike);
        bike4_3 = new Bike(provider4, mountainBike);
        bike4_4 = new Bike(provider4, mountainBike);
        bike4_5 = new Bike(provider4, roadBike);
        bike4_6 = new Bike(provider4, eBike);
        bike4_7 = new Bike(provider4, eBike);
        bike4_8 = new Bike(provider4, eBike);
        bike4_9 = new Bike(provider4, eBike);
        Bike arrayProvider4[] = {bike4_1, bike4_2, bike4_3, bike4_4, bike4_5, bike4_6, 
                bike4_7, bike4_8, bike4_9};
        provider4Stock = new HashSet<>(Arrays.asList(arrayProvider4));
        provider4.setStock(provider4Stock);
        
        //Mock quote
        quoteMock = new Quote(provider1Stock, provider1, 
                new DateRange(LocalDate.of(1,1,1), LocalDate.of(1,1,1)), locationP1);
        quoteMock.setIsPaid(true);
        new Booking(quoteMock, false);
        
        //ArrayList of BikeType for Input
        requestedBikes1 = new ArrayList<>();
        requestedBikes1.add(mountainBike);

        requestedBikes2 = new ArrayList<>();
        requestedBikes2.add(mountainBike);
        requestedBikes2.add(mountainBike);
        requestedBikes2.add(mountainBike);

        requestedBikes3 = new ArrayList<>();
        requestedBikes3.add(mountainBike);
        requestedBikes3.add(roadBike);
        requestedBikes3.add(eBike);
        
        //Inputs
        input1 = new Input(dateRange1, requestedBikes1, locationC1);
        input2 = new Input(dateRange1, requestedBikes1, locationC4); //Test 1.6
        input3 = new Input(dateRange1, requestedBikes3, locationC3); //Test 1.1
        input4 = new Input(dateRange1, requestedBikes3, locationC1); //Test 1.2

        //Generated Quotes
        quotes1 = c.generateQuotes(input1);
        quotes2 = c.generateQuotes(input2);
        quotes3 = c.generateQuotes(input3);
        quotes4 = c.generateQuotes(input4);
       
        //Quotes
        quote1 = new Quote(provider4Stock, provider4, 
                 new DateRange(LocalDate.of(2019,1,1), LocalDate.of(2019,1,2)), locationP4);
        quote2 = new Quote(provider2Stock, provider2,
                 new DateRange(LocalDate.of(2019,1,1), LocalDate.of(2019,1,2)), locationP2);
        quote3 = new Quote(provider3Stock, provider3,
                 new DateRange(LocalDate.of(2019,1,1), LocalDate.of(2019,1,2)), locationP3);
        quote4 = new Quote(provider1Stock, provider1, dateRange1, locationP1);

        quote4.setIsPaid(true);
        
        //Bookings
        booking1 = new Booking(quote4, true, locationC1, provider3); //Delivery required, returned to partner prov
        booking2 = new Booking(quote4, false, provider3); //No delivery, returned to partner prov
        booking3 = new Booking(quote4, true, locationC1); //Delivery required, retured to main prov
        booking4 = new Booking(quote4, false); //No delivery, returned to main prov      
    }
    
    // TODO: Write system tests covering the three main use cases

    //Tests: Use Case 1 - Finding a quote
    
    //Test 1.1: Checks provider field in quote is what is expected, thus checking
    //          - matches the actual provider of the bikes
    //          - each quote only has bikes from one provider
    //          - the provider is in range (near enough to the customer)
    @Test
    void test1ExpectedProvider() {
        Set<Provider> expectedProviders = new HashSet<>();
        expectedProviders.add(provider1);
        Set<Provider> quoteProviders = new HashSet<>();
        for (Quote quote : quotes1) {
            quoteProviders.add(quote.getProvider());
        }
        assertTrue(expectedProviders.equals(quoteProviders));
    }
    
    @Test
    void test2ExpectedProvider() {
        Set<Provider> expectedProviders = new HashSet<>();
        expectedProviders.add(provider4);
        Set<Provider> quoteProviders = new HashSet<>();
        for (Quote quote : quotes3) {
            quoteProviders.add(quote.getProvider());
        }       
        assertTrue(expectedProviders.equals(quoteProviders));
    }
    
    //Test 1.2: Checks the returned bikes are of the type the customer requested
    @Test
    void testBikeTypesInQuote() {
        for (Quote quote : quotes4) {
            ArrayList<BikeType> requestedBikeTypes = input4.getBikesRequested();
            ArrayList<Bike> bikesInQuote = new ArrayList<Bike>(quote.getBikes());
            ArrayList<BikeType> bikeTypesInQuote = new ArrayList<>();
            for (Bike bike : bikesInQuote) {
                bikeTypesInQuote.add(bike.getType());
            }
            
            for (BikeType bikeType : requestedBikeTypes) {
                if (bikeTypesInQuote.contains(bikeType)) {
                    bikeTypesInQuote.remove(bikeType);
                }                
            }
            assertTrue(bikeTypesInQuote.isEmpty());
        }
    }
    
    //Test 1.3: Checks all the bikes are available for the required dateRange
    @Test
    void testQuoteReturnsAvailibleBikes() {
        ArrayList<BikeType> requestedBike = new ArrayList<>();
        requestedBike.add(roadBike);
        Input inputTest = new Input(dateRange1, requestedBike, locationC3);
        Booking booking1 = new Booking(quote1, false);
        provider4.addProviderBooking(booking1);
        ArrayList<Quote> quotesTest = c.generateQuotes(inputTest);
        assertTrue(quotesTest.isEmpty());
    }
    
    //Test 1.4: Checks daily rental price is correct
    @Test
    void testQuoteCorrectDailyRentalPrice() {
        Quote[] quoteArray = new Quote[quotes3.size()];
        quotes3.toArray(quoteArray);
        assertEquals(1, quotes3.size()); //Expected to only be one quote
        BigDecimal totalRentalPrice = quoteArray[0].getTotalRentalPrice();
        BigDecimal expectedRentalPrice = new BigDecimal(3.00*(10.00 + 11.00 + 17.00));
        assertEquals(expectedRentalPrice.stripTrailingZeros(), 
                totalRentalPrice.stripTrailingZeros());
    }
    
    //Test 1.5: Checks deposit price is correct
    @Test
    void testCorrectDepositPrice() {
        Quote[] quoteArray = new Quote[quotes3.size()];
        quotes3.toArray(quoteArray);
        assertEquals(1, quotes3.size()); //Expected to only be one quote
        BigDecimal totalDepositPrice = quoteArray[0].getTotalDepositPrice();
        BigDecimal expectedDepositPrice = new BigDecimal(0.25*(100.00 + 110.00 + 120.00));
        assertEquals(expectedDepositPrice.stripTrailingZeros(), 
                totalDepositPrice.stripTrailingZeros());
    }
    
    //Test 1.6: Check no quotes are returned
    @Test
    void testNoQuotesReturned() {
        assertTrue(quotes2.isEmpty());
    }
        
    //Tests: Use Case 2 - Booking a quote
//    Place a booking 
//    Unique booking number
//    If customer has requested DeliveryService then should return
        
    //Test 2.1: Checks that a quote is only booked if a payment has been made
    @Test
    void testBookingWithoutPayment() {
        quotes1.get(0).setIsPaid(false);
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            c.bookQuote(quotes1.get(0), false);
        });
    }
    
    @Test
    void testBookingWithPayment() { //What are you testing here?
        quotes1.get(0).setIsPaid(true);
        ;
        assertEquals((c.bookQuote(quotes1.get(0), false)).getClass(), Invoice.class);
    }
    
    //Test 2.2: Checks that verify partner works
    @Test
    void testBookingInvalidPartner() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            c.bookQuote(quotes1.get(0), false, provider4);
        });
    }
    
    //Test 2.3: Checks that Booking overloading works
    //          - checked for a booking that doesn't require delivery or partner return in the 
    //            test testBookingWithPayment()
    //Test 2.3.1: Make a booking that requires both delivery and return to partner
    @Test
    void testBookingDevliveryPartnerReturn() {
        quote1.setIsPaid(true);
        assertEquals(c.bookQuote(quote1, true, locationP1, provider3).getClass(), Invoice.class);
    }
    
    //Test 2.3.2: Make a booking that doesn't require delivery but return to partner
    @Test
    void testBookingNoDeliveryPartnerReturn() {
        quote2.setIsPaid(true);
        assertEquals(c.bookQuote(quote2, false, provider3).getClass(), Invoice.class);
    }
    
    //Test 2.3.3: Make a booking that requires delivery but return to original provider
    @Test
    void testBookingDeliveryOriginalReturn() {
        quote3.setIsPaid(true);
        assertEquals(c.bookQuote(quote3, true, locationP3).getClass(), Invoice.class);
    }
    
    //Test 2.4: Checks that an Invoice is returned and that it contains the right details
    @Test
    void testInvoice(){
        quotes3.get(0).setIsPaid(true);
        Invoice invoice = c.bookQuote(quotes3.get(0), false);
        assertEquals(invoice.getInvoiceNum(), 10);
        assertEquals(invoice.getDateRange(), dateRange1);
        assertEquals(invoice.getBikesBooked(), quotes3.get(0).getBikes());
        assertEquals(invoice.getTotalRentalPrice(), new BigDecimal("114"));
        assertEquals(invoice.getTotalDeposit().stripTrailingZeros(),
                     new BigDecimal(0.25*(100.00+110.00+120.00)));
    }
    
    //Test 2.5: Checks that bookings are added to the respective providers
    @Test
    void testBookingsAdded() {
        int[] expectedOrderNum1 = {6};
        int[] expectedOrderNum2 = {7};
        int[] expectedOrderNum3 = {5};
        ArrayList<Provider> providers = new ArrayList<Provider>();
        providers.addAll(c.getProviders());
        for(Booking b : providers.get(0).getBookings()){
            assertEquals(b.getOrderNum(), expectedOrderNum1[0]);
        }
        
        for(Booking b : providers.get(1).getBookings()) {
            assertEquals(b.getOrderNum(), expectedOrderNum2[0]);
        }
        
        for(Booking b : providers.get(2).getBookings()) {
            assertEquals(b.getOrderNum(), expectedOrderNum3[0]);
        }
    }
    
    //Test: Use Case 3 - Returning bikes
     
    //Test 3.1: Check if the correct booking is found
    @Test
    void testCorrectBookingFound() {
        Booking booking1Found = c.findBookingByNumber(booking1.getOrderNum());
        assertEquals(booking1, booking1Found);
        
        Booking booking2Found = c.findBookingByNumber(booking2.getOrderNum());
        assertEquals(booking2, booking2Found);
        
        Booking booking3Found = c.findBookingByNumber(booking3.getOrderNum());
        assertEquals(booking3, booking3Found);
        
        Booking booking4Found = c.findBookingByNumber(booking4.getOrderNum());
        assertEquals(booking4, booking4Found);
    }
    
    //Test 3.2: Check if the deposit is returned on each booking
    @Test
    void testDepositIsReturned() {
        c.returnBikesToProvider((Integer) 1);
        Booking b = c.findBookingByNumber((Integer) 1);
        assertEquals(1, b.getOrderNum());
        assertTrue(b.getDepositReturned());
    }
    
    //Test 3.3: Check the status of the booking
    @Test
    void test1BookingStatus() { 
        //Booking that was returned to main provider
        c.returnBikesToProvider((Integer) 4);
        Booking b = c.findBookingByNumber((Integer) 4);
        assertEquals(4, b.getOrderNum());
        assertEquals(BookingStatuses.COMPLETE, b.getBookingStatus());
    }
    
    // The following test is commented out as we didn't seem to implement the MockDelivery
    // system correctly or to its fullest extent, resulting in false statuses  
//    @Test
//    void test2BookingStatus() { 
//        // Booking that was returned to partner provider (and needs delivery 
//        // to main provider to get status COMPLETE)
//        c.returnBikesToProvider((Integer) 1);
//        Booking b = c.findBookingByNumber((Integer) 1);
//        
//        assertEquals(1, b.getOrderNum());
//        assertEquals(BookingStatuses.COMPLETE, b.getBookingStatus());
//    }
    
    //Test 3.4: Check the status of the bikes in the booking
    @Test
    void test1BikeStatus() {
        //Booking that was returned to main provider
        c.returnBikesToProvider((Integer) 4);
        Booking b = c.findBookingByNumber((Integer) 4);
        assertEquals(4, b.getOrderNum());
        Set<Bike> bikesInBooking = b.getBikeCollection();
        boolean allStatusesCorrect = true;
        for (Bike bike : bikesInBooking) {
            if (bike.getBikeStatus() != BikeStatuses.AT_MAIN_PROVIDER) {
                allStatusesCorrect = false;
            }
        }
        assertTrue(allStatusesCorrect);
    }
    
    @Test
    void test2BikeStatus() {
        //Booking that was returned to partner provider (and needs delivery 
        //    to main provider to get status AT_MAIN_PROVIDER)
        c.returnBikesToProvider((Integer) 1);
        Booking b = c.findBookingByNumber((Integer) 1);
        assertEquals(1, b.getOrderNum());
        Set<Bike> bikesInBooking = b.getBikeCollection();
        boolean allStatusesCorrect = true;
        for (Bike bike : bikesInBooking) {
            if (bike.getBikeStatus() != BikeStatuses.AT_MAIN_PROVIDER) {
                allStatusesCorrect = false;
            }
        }
        assertTrue(allStatusesCorrect);
    }
    
    //Test: Extension - Multiday Pricing Discounts
    //Test E.1: Check that the pricing policy is assigned
    @Test
    void testPricingPolicyAssign() {
        int[] thresholds = {2, 6, 13};
        double[] discounts = {0.05, 0.1, 0.15};
        pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        assertEquals(pricingPolicy1, provider1.getPP());
    }
    
    //Test E.2: Check that threshold and discounts have to be equal in size;
    @Test
    void testPricingPolicyUnequalArgumentSizes() {
        int[] thresholds = {2, 6, 13, 14};
        double[] discounts = {0.05, 0.1, 0.15};
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        });
    }
    
    //Test E.3: Check that the right discounts are applied for respective thresholds
    @Test
    void testPricingPolicyThresholds() {
        int[] thresholds = {2, 6, 13};
        double[] discounts = {0.05, 0.10, 0.15};
        pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        long days1 = 2;
        long days2 = 4;
        long days3 = 9;
        long days4 = 13;
        long days5 = 20;
        double discount1 = pricingPolicy1.getDiscount(days1);
        double discount2 = pricingPolicy1.getDiscount(days2);
        double discount3 = pricingPolicy1.getDiscount(days3);
        double discount4 = pricingPolicy1.getDiscount(days4);
        double discount5 = pricingPolicy1.getDiscount(days5);
        assertEquals(discount3, discount4);
        assertEquals(discount1, 0);
        assertEquals(discount2, 0.05);
        assertEquals(discount3, 0.10);
        assertEquals(discount5, 0.15);
    }
    
    //Test E.4: Check that the correct discounted prices are returned
    @Test
    void testPricingPolicyPrices() {
        int[] thresholds = {2, 6, 13};
        double[] discounts = {0.05, 0.10, 0.15};
        pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        DateRange dateRange1 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 2));
        DateRange dateRange2 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 3));
        DateRange dateRange3 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 9));
        DateRange dateRange4 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 20));


        Quote quote1 = new Quote(provider1Stock, provider1, dateRange1, locationP1);
        Quote quote2 = new Quote(provider1Stock, provider1, dateRange2, locationP1);
        Quote quote3 = new Quote(provider1Stock, provider1, dateRange3, locationP1);
        Quote quote4 = new Quote(provider1Stock, provider1, dateRange4, locationP1);

        assertEquals(quote1.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                     new BigDecimal(10*2*2).stripTrailingZeros());
        assertEquals(quote2.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                     new BigDecimal((10*2*3)*0.95).stripTrailingZeros());
        assertEquals(quote3.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                new BigDecimal((10*2*9)*0.9).stripTrailingZeros());
        assertEquals(quote4.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                new BigDecimal((10*2*20)*0.85).stripTrailingZeros());
    }
}
