package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.ArrayList;

public class Input {
    
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private ArrayList<BikeType> bikesRequested; //Tested as an array (Just let me know if you change it)
    private Location hireLocation;
    
    public Input(LocalDate dateStart, LocalDate dateEnd, ArrayList<BikeType> bikesRequested,
        Location hireLocation) {
        
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.bikesRequested = bikesRequested;
        this.hireLocation = hireLocation;
    }
    
    public LocalDate getStartDate() {
        return this.dateStart;
    }
    
    public LocalDate getEndDate() {
        return this.dateEnd;
    }
    
    public DateRange getRequestedDateRange() {
        DateRange dateRange = new DateRange(this.getStartDate(), this.getEndDate());
        return dateRange;
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
