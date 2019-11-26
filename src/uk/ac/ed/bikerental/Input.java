package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.ArrayList;

public class Input {
    
    private DateRange dateRange;
    private ArrayList<BikeType> bikesRequested; 
    private Location hireLocation;
    
    public Input(DateRange dateRange, ArrayList<BikeType> bikesRequested,
        Location hireLocation) {        
        this.dateRange = dateRange;
        this.bikesRequested = bikesRequested;
        this.hireLocation = hireLocation;
    }
    
    public LocalDate getStartDate() {
        return this.dateRange.getStart();
    }
    
    public LocalDate getEndDate() {
        return this.dateRange.getEnd();
    }
    
    public DateRange getRequestedDateRange() {
        return this.dateRange;
    }
    
    public ArrayList<BikeType> getBikesRequested() {
        return this.bikesRequested;
    }
    
    public Location getRequestedLocation() {
        return this.hireLocation;
    }

    //No need for extendDateRange method (stated in cw3 pg 8)
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
