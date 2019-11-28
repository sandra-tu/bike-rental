package uk.ac.ed.bikerental;

import java.util.Objects;
import java.util.Set;
import java.math.BigDecimal;;

public class Invoice {
    private final int invoiceNum;
    private DateRange dateRange;
    private Set<Bike> bikesBooked;
    private BigDecimal totalRentalPrice;
    private BigDecimal totalDeposit;
    
    public Invoice(Booking booking) {
        this.invoiceNum = booking.getOrderNum();
        this.dateRange = booking.getBookingDateRange();
        this.bikesBooked = booking.getBikeCollection();
        this.totalRentalPrice = booking.getTotalRentalPrice();
        this.totalDeposit = booking.getTotalDeposit();
    }
    
    public void printInvoice() {
        //details for formatting invoiceOutput
    }
    
    //GETTERS
    
    
    @Override
    public int hashCode() {
        return Objects.hash(invoiceNum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Invoice other = (Invoice) obj;
        return Objects.equals(invoiceNum, other.invoiceNum);
    }
    
}
