package uk.ac.ed.bikerental;

import java.util.Set;
import java.math.BigDecimal;;

public class Invoice {
    private int orderNumber;
    private DateRange dateRange;
    private Set<Bike> bikesBooked;
    private BigDecimal totalRentalPrice;
    private BigDecimal totalDeposit;
    
    public Invoice(Booking booking) {
        this.orderNumber = booking.gerOrderNum();
        this.dateRange = booking.getBookingDateRange();
        this.bikesBooked = booking.getBikeCollection();
        //this.totalRentalPrice = booking.get
    }
}
