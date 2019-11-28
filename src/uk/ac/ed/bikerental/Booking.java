package uk.ac.ed.bikerental;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import java.time.LocalDate;
import java.math.BigDecimal;

import uk.ac.ed.bikerental.Bike.BikeStatuses;
import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class Booking {
        
    private final Integer orderNum;
    private Set<Bike> bikeCollection;
    private DateRange dateRange;
    private Provider provider;
    private Provider returnProvider;
    private boolean depositPaid = false;
    private boolean depositReturned = false;
    private boolean deliveryRequired = false; //ADD TO CWK2
    private BigDecimal totalRentalPrice;
    private BigDecimal totalDeposit;
    private BookingStatuses bookingStatus;
    private static AtomicLong orderNumCounter = new AtomicLong();
    
    //CONSTRUCTOR
    //Constructor overloading for optional delivery and returning to a partner
    public Booking(Quote quote, boolean deliveryRequired, Location customerLocation, 
                  Provider returnProvider) {
        this.bikeCollection = quote.getBikes();
        this.dateRange = quote.getDateRange();
        this.provider = quote.getProvider();
        this.bookingStatus = BookingStatuses.PRECOMMENCEMENT;
        this.totalRentalPrice = quote.getTotalRentalPrice();
        this.totalDeposit = quote.getTotalDepositPrice();
        if(deliveryRequired) {
            this.setDeliveryRequired(customerLocation); // maybe should be another catch in here, make sure thatdeliveryRquired is true
        }
        this.setReturnProvider(returnProvider);
        if (this == null) {
            throw new IllegalArgumentException("This booking is null for some reason!");
        }
        this.provider.addProviderBooking(this);
        this.orderNum = createOrderNum();
    }
    
    //Delivery not required but requested return to partner
    public Booking(Quote quote, boolean deliveryRequired, Provider returnProvider) {
        this.bikeCollection = quote.getBikes();
        this.dateRange = quote.getDateRange();
        this.provider = quote.getProvider();
        this.bookingStatus = BookingStatuses.PRECOMMENCEMENT;
        this.totalRentalPrice = quote.getTotalRentalPrice();
        this.totalDeposit = quote.getTotalDepositPrice();
        if(deliveryRequired) {
            throw new IllegalArgumentException("Please pass customer location");
        }
        this.setReturnProvider(returnProvider);
        this.provider.addProviderBooking(this);
        this.orderNum = createOrderNum();
    }
    
    //Delivery required but return to original provider
    public Booking(Quote quote, boolean deliveryRequired, Location customerLocation) {
        this.bikeCollection = quote.getBikes();
        this.dateRange = quote.getDateRange();
        this.provider = quote.getProvider();
        this.bookingStatus = BookingStatuses.PRECOMMENCEMENT;
        this.totalRentalPrice = quote.getTotalRentalPrice();
        this.totalDeposit = quote.getTotalDepositPrice();
        this.returnProvider = this.provider;
        if(deliveryRequired) {
            this.setDeliveryRequired(customerLocation);;
        }
        this.provider.addProviderBooking(this);
        this.orderNum = createOrderNum();
    }
    
    //Neither is required
    public Booking(Quote quote, boolean deliveryRequired) {
        this.bikeCollection = quote.getBikes();
        this.dateRange = quote.getDateRange();
        this.provider = quote.getProvider();
        this.bookingStatus = BookingStatuses.PRECOMMENCEMENT;
        this.totalRentalPrice = quote.getTotalRentalPrice();
        this.totalDeposit = quote.getTotalDepositPrice();
        this.returnProvider = this.provider;
        if(deliveryRequired) {
            throw new IllegalArgumentException("Please pass customer location");
        }
        if (this == null) {
            throw new IllegalArgumentException("This booking is null for some reason!");
        }
        this.provider.addProviderBooking(this);
        this.orderNum = createOrderNum();
    }
    
    public Integer createOrderNum() {
        return Integer.valueOf(String.valueOf(orderNumCounter.getAndIncrement()));
    }
    
    //GETTERS
    public int getOrderNum() {return this.orderNum;} 
    public Set<Bike> getBikeCollection(){return this.bikeCollection;} 
    public DateRange getBookingDateRange() {return this.dateRange;}
    public Provider getProvider() {return this.provider;}
    public Provider getReturnProvider() {return this.returnProvider;}
    public boolean getDepositIsPaid() {return this.depositPaid;}
    public boolean getDepositReturned() {return this.depositReturned;}
    public boolean getDeliveryReturned() {return this.deliveryRequired;}
    public BigDecimal getTotalRentalPrice() {return this.totalRentalPrice;}
    public BigDecimal getTotalDeposit() {return this.totalDeposit;}
    public BookingStatuses getBookingStatus() {return this.bookingStatus;}
    
    //SETTERS
    public void setBookingStatus(BookingStatuses bookingStatus) {
        this.bookingStatus = bookingStatus;
        switch(bookingStatus) {
            case OUT_FOR_DELIVERY:
                for(Bike bike : bikeCollection) {
                    bike.setBikeStatus(BikeStatuses.OUT_FOR_DELIVERY);
                }
                
            case IN_USE:
                for(Bike bike : bikeCollection) {
                    bike.setBikeStatus(BikeStatuses.IN_USE);
                }
                
            case AT_PARTNER:
                for(Bike bike : bikeCollection) {
                    bike.setBikeStatus(BikeStatuses.AT_PARTNER);
                }
                
            case OVERNIGHT_RETURN:
                for(Bike bike : bikeCollection) {
                    bike.setBikeStatus(BikeStatuses.OVERNIGHT_TRANSIT);
                }
            case COMPLETE:
                for(Bike bike : bikeCollection) {
                    bike.setBikeStatus(BikeStatuses.AT_MAIN_PROVIDER);
                }
            default:
                return;
        }
    }
    
    public void setDepositPaid(){
        this.depositPaid = true;
    }
    
    public void setDepositReturned() {
        this.depositReturned = true;
    }
    
    public void setReturnProvider(Provider provider) {
        if(this.getProvider().verifyPartner(provider)) {
            this.returnProvider = provider;
        }else {
            throw new IllegalArgumentException("Requested return provider is not a partner"); //should I throw an exception here??
        }
    }
    
    public void setDeliveryRequired(Location customerLocation) {
        //Dummy bike object just to see
        //BikeType bikeType = new BikeType(BikeTypes.EBIKE, new BigDecimal(100)); 
        //Bike bike = new Bike(provider, bikeType);
        BikeCollection bikeCol = new BikeCollection(this);
        
        this.deliveryRequired = true;
        Location providerAddress = this.getProvider().getAddress();
        LocalDate pickupDate = this.getBookingDateRange().getStart();
        //Deliverable deliverable = new Deliverable();
        DeliveryServiceFactory.getDeliveryService().scheduleDelivery(bikeCol, //So with one bike object it seems to work
                providerAddress, customerLocation, pickupDate);
    }
    
    public enum BookingStatuses{
        PRECOMMENCEMENT,
        OUT_FOR_DELIVERY,
        IN_USE,
        AT_PARTNER,
        OVERNIGHT_RETURN,
        COMPLETE
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Booking other = (Booking) obj;
        return Objects.equals(orderNum, other.orderNum);
    }
    
//    public static void main(String[] args) {
//        
//        Location locationP1 = new Location("EH89QE", "Street name");
//        Location locationP3 = new Location("G138AB", "Street name");
//        Location locationC1 = new Location("EH45AH", "Street name");
//
//        DateRange dateRange1 = new DateRange(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 3));
//        Provider provider3 = new Provider("Provider3", locationP3, new BigDecimal(0.1));
//        Provider provider1 = new Provider("Provider1", locationP1, new BigDecimal(0.2));
//
//        Set<Provider> partnersOf1 = new HashSet<>();
//        provider1.setPartnerProviders(partnersOf1);
//        partnersOf1.add(provider3);
//        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
//        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(110.00));
//        BikeType eBike = new BikeType(BikeTypes.EBIKE, new BigDecimal(120.00));
//        System.out.println("ho");
//        provider1.setDailyRentalPrice(mountainBike, new BigDecimal(10.00));
//        provider3.setDailyRentalPrice(mountainBike, new BigDecimal(10.00));
//        provider3.setDailyRentalPrice(roadBike, new BigDecimal(10.00));
//        provider3.setDailyRentalPrice(eBike, new BigDecimal(10.00));
//        System.out.println("ho");
//        Bike bike1_1 = new Bike(provider1, mountainBike);
//        Set<Bike> provider1Stock = new HashSet<>();
//        provider1Stock.add(bike1_1);
//        System.out.println("ho");
//        System.out.println(bike1_1.getDailyRentalPrice());
//        System.out.println(bike1_1.getDailyRentalPrice());
//        bike1_1.printSummary();
//        System.out.println(provider1.getDailyRentalPrice(mountainBike));
//
//        Quote quote4 = new Quote(provider1Stock, provider1, dateRange1, locationP1);
//
//        Booking booking1 = new Booking(quote4, true, locationC1, provider3); //Delivery required, returned to partner prov
//        System.out.println(booking1.getOrderNum());
//    }

    public void printSummary() {
      System.out.println("Booking order no.:  " + this.orderNum);
      System.out.println("Provider:           " + this.provider.getProviderID());
      System.out.println("Total rental price: " + this.totalRentalPrice);
      System.out.println("Total deposit.:     " + this.totalDeposit);
      System.out.println("Booking pointer:    " + this + "\n");
  
    }

}
