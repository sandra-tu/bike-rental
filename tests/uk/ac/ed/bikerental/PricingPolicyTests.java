package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.*;

import uk.ac.ed.bikerental.BikeType.BikeTypes;


public class PricingPolicyTests {
    private DateRange dateRange1;
    private Provider provider1;
    private BikeType hybridBike;
    private Bike bike1_1, bike1_2, bike1_3, bike1_4;
    private MultidayPricingPolicy pricingPolicy1;
    private Set<Bike> provider1Stock;
    private Location locationP1;
    
    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
        this.hybridBike = new BikeType(BikeTypes.HYBRIDBIKE, new BigDecimal(100.00));
        this.locationP1 = new Location("EH89QE", "Street name");
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 3));
        this.provider1 = new Provider("Provider1", locationP1, new BigDecimal(0.2));
        //c.addProvider(provider1);
        //provider1.setDailyRentalPrice(mountainBike, new BigDecimal(10.00));
        this.bike1_1 = new Bike(provider1, hybridBike);
        this.bike1_2 = new Bike(provider1, hybridBike);
        this.bike1_3 = new Bike(provider1, hybridBike);
        this.bike1_4 = new Bike(provider1, hybridBike);
        Bike arrayProvider1[] = {bike1_1, bike1_2, bike1_3, bike1_4};
        this.provider1Stock = new HashSet<>(Arrays.asList(arrayProvider1));
        provider1.setStock(provider1Stock);
        System.out.println(provider1Stock.size());
    }
    
    //Test E.1: Check that the pricing policy is assigned
    @Test
    void testPricingPolicyAssign() {
        int[] thresholds = {2, 6, 13};
        double[] discounts = {0.05, 0.1, 0.15};
        pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        assertEquals(pricingPolicy1, provider1.getPP());
    }
    
    //Test: Check that the daily rental price is updated correctly / is assigned as expected
    @Test
    void testDailyRentalPrice() {
        pricingPolicy1.setDailyRentalPrice(hybridBike, new BigDecimal(7.00));
        assertEquals(new BigDecimal(7.00), provider1.getDailyRentalPrice(hybridBike));
    }
    
    //Test: Check that exceptions are thrown when any values in the argument arrays are negative
    @Test 
    void test1ExceptionsNegativeValues() {
        //Negative threshold
        int[] thresholds = {-1, 6, 13, 14};
        double[] discounts = {0.95, 0.9, 0.85};
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        });
    }
    
    @Test 
    void test2ExceptionsNegativeValues() {
        //Negative discount
        int[] thresholds = {2, 6, 13, 14};
        double[] discounts = {-1, 0.9, 0.85};
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        });
    }
    
    //Test: Check that exception is thrown when threshold array is not sorted
    @Test 
    void testExceptionsThresholdsNotSorted() {
        //Threshold array not sorted
        int[] thresholds = {6, 2, 13, 14};
        double[] discounts = {0.95, 0.9, 0.85};
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        });
    }    
    
    //Test E.2: Check that error is thrown when threshold and discounts are unequal in length;
    @Test
    void testPricingPolicyUnequalArgumentSizes() {
        int[] thresholds = {2, 6, 13, 14};
        double[] discounts = {0.95, 0.9, 0.85};
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        });
    }
    
    //Test E.3: Check that the right discounts are applied for respective thresholds
    @Test
    void testPricingPolicyThresholds() {
        int[] thresholds = {2, 6, 13};
        double[] discounts = {0.05, 0.10, 0.25};
        pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        
        double discount1 = pricingPolicy1.getDiscount((long) 1);
        double discount2 = pricingPolicy1.getDiscount((long) 2);
        double discount3 = pricingPolicy1.getDiscount((long) 4);
        double discount4 = pricingPolicy1.getDiscount((long) 9);
        double discount5 = pricingPolicy1.getDiscount((long) 13);
        double discount6 = pricingPolicy1.getDiscount((long) 20);
        assertEquals(discount1, 0.00);
        assertEquals(discount2, 0.05);
        assertEquals(discount3, 0.05);
        assertEquals(discount4, 0.10);
        assertEquals(discount5, 0.25);
        assertEquals(discount6, 0.25);
    }
    
  //Test E.4: Check that the correct discounted prices are returned
    @Test
    void testPricingPolicyPrices() {
        int[] thresholds = {2, 6, 13};
        double[] discounts = {0.05, 0.10, 0.25};
        pricingPolicy1 = new MultidayPricingPolicy(thresholds, discounts, provider1);
        pricingPolicy1.setDailyRentalPrice(hybridBike, new BigDecimal(7.00));
        LocalDate start = LocalDate.of(2019, 1, 1);
        DateRange dateRange1 = new DateRange(start, LocalDate.of(2019, 1, 1)); //1 day
        DateRange dateRange2 = new DateRange(start, LocalDate.of(2019, 1, 2)); //2 days
        DateRange dateRange3 = new DateRange(start, LocalDate.of(2019, 1, 3)); //3 days
        DateRange dateRange4 = new DateRange(start, LocalDate.of(2019, 1, 9)); //9 days
        DateRange dateRange5 = new DateRange(start, LocalDate.of(2019, 1, 20));//20 days

        Quote quote1 = new Quote(provider1Stock, provider1, dateRange1, locationP1);
        Quote quote2 = new Quote(provider1Stock, provider1, dateRange2, locationP1);
        Quote quote3 = new Quote(provider1Stock, provider1, dateRange3, locationP1);
        Quote quote4 = new Quote(provider1Stock, provider1, dateRange4, locationP1);
        Quote quote5 = new Quote(provider1Stock, provider1, dateRange5, locationP1);
        
        assertEquals(quote1.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                     new BigDecimal(28.00).stripTrailingZeros());
        assertEquals(quote2.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                     new BigDecimal(53.20).stripTrailingZeros());
        assertEquals(quote3.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                new BigDecimal(79.80).stripTrailingZeros());
        assertEquals(quote4.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                new BigDecimal(226.80).stripTrailingZeros());
        assertEquals(quote5.calculateTotalDailyRentalPrice().stripTrailingZeros(),
                new BigDecimal(476.00).stripTrailingZeros());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}