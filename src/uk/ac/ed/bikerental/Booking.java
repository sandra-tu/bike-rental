package uk.ac.ed.bikerental;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import uk.ac.ed.bikerental.Bike.BikeStatuses;

public class Booking {
    
    private Integer orderNum;
    private Set<Bike> bikeCollection;
    private DateRange dateRange;
    private Provider provider;
    private Provider returnProvider;
    private boolean depositPaid = false;
    private boolean depositReturned = false;
    private BookingStatuses bookingStatus;
    private static AtomicLong orderNumCounter = new AtomicLong();
    
    //CONSTRUCTOR
    public Booking(Quote quote) {
        this.dateRange = quote.getDateRange();
        this.provider = quote.getProvider();
        this.bookingStatus = BookingStatuses.PRECOMMENCEMENT;
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
    public BookingStatuses getBookingStatus() {return this.bookingStatus;}
    
    //SETTERS
    public void setBookingStatus(BookingStatuses bookingStat) {
        this.bookingStatus = bookingStat;
        switch(bookingStat) {
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
    
    public enum BookingStatuses{
        PRECOMMENCEMENT,
        OUT_FOR_DELIVERY,
        IN_USE,
        AT_PARTNER,
        OVERNIGHT_RETURN,
        COMPLETE
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
