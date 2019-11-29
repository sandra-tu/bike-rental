package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a range of dates, uses two LocalDate objects for start date and end date
 */

public class DateRange {
    
    /**
     * Represents the start date and end date of a date range
     */
    private LocalDate start, end;
    
    /**
     * Class constructor
     * 
     * @param start LocalDate object for start date
     * @param end   LocalDate object for end date
     */
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        assert !this.end.isBefore(this.start);
    }
    
    /**
     * Getter method for start
     * 
     * @return the start date of the DateRange
     */
    public LocalDate getStart() {
        return this.start;
    }
    
    /**
     * Getter method for end
     * @return the end date of the DateRange
     */
    public LocalDate getEnd() {
        return this.end;
    }
    
    /**
     * Method for seeing the number of years the DateRange spans
     * @return a long representing the number of years
     */
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }
    
    /**
     * Method for seeing the number of days the DateRange spans
     * @return a long representing the number of days
     */
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }
    
    /**
     * Method to check if this DateRange overlaps with another given DateRange. Returns true when
     * the start or end date of one DateRange is within the range of the other DateRange
     * 
     * @param other  a DateRange object that will be compared another DateRange object
     * @return       a boolean representing whether there is an overlap or not
     */
    public boolean overlaps(DateRange other) {
        // TODO: implement date range intersection checking
        LocalDate start1 = this.getStart();
        LocalDate start2 = other.getStart();
        LocalDate end1 = this.getEnd();
        LocalDate end2 = other.getEnd();
        boolean overlaps;
        if (start1.isAfter(end2) || start2.isAfter(end1)) {
            overlaps = false;
        } else {
            overlaps = true;
        }
        return overlaps;
    }
    
    /**
     * hashCode method allowing use in collections
     * 
     * A method that overrides the normal hashCode() function to facilitate comparisons of 
     * collections of this object.
     * 
     * @return an int specifying which bucket the object should be stored in
     */
    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }
    
    /**
     * equals method for testing equality in tests
     * 
     * A method that overrides the normal equals(), and compares DateRange objects and assesses 
     * whether they are equal. This is done by comparing the start and end date for each DateRange.
     * 
     * @param  obj a Location that requires comparison
     * @return     a boolean to indicate whether or not the two locations are equal
     */
    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }
}
