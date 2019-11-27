package uk.ac.ed.bikerental;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDate;
import java.math.BigDecimal;

import uk.ac.ed.bikerental.Bike.BikeStatuses;
import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class Booking {
    
    //See at bottom for inheritted empty methods from Deliverable class
    
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
    private MockDeliveryService delivery;
    private BookingStatuses bookingStatus;
    private static AtomicLong orderNumCounter = new AtomicLong();
    
    //CONSTRUCTOR
    public Booking(Quote quote) {
        this.orderNum = createOrderNum();
        this.bikeCollection = quote.getBikes();
        this.dateRange = quote.getDateRange();
        this.provider = quote.getProvider();
        this.bookingStatus = BookingStatuses.PRECOMMENCEMENT;
        this.totalRentalPrice = quote.getTotalRentalPrice();
        this.totalDeposit = quote.getTotalDepositPrice();
    }
    
    public Integer createOrderNum() {
        return Integer.valueOf(String.valueOf(orderNumCounter.getAndIncrement()));
    }
    
    //GETTERS
    public int gerOrderNum() {return this.orderNum;} 
    public Set<Bike> getBikeCollection(){return this.bikeCollection;} 
    public DateRange getBookingDateRange() {return this.dateRange;}
    public Provider getProvider() {return this.provider;}
    public Provider getReturnProvider() {return this.returnProvider;}
    public boolean getDepositIsPaid() {return this.depositPaid;}
    public boolean getDepositReturned() {return this.depositReturned;}
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
    
    public void setDeliveryRequired(Location customerLocation) {
        //Dummy bike object just to see
        BikeType bikeType = new BikeType(BikeTypes.EBIKE, new BigDecimal(100));
        Bike bike = new Bike(provider, bikeType);
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

}
