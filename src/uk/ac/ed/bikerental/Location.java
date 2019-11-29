package uk.ac.ed.bikerental;

import java.util.Objects;

/** 
 * Represents a location in Scotland by a street address and postcode
 */
public class Location {
    /**
     * Represents the parts of a location, namely its postcode and street address, both as Strings 
     */
    private String postcode, streetAddress;

    /**
     * Class constructor
     * 
     * @param postcode      the postcode of the Location
     * @param streetAddress the street address of the Location
     */
    public Location(String postcode, String streetAddress) {
        assert postcode.length() >= 6;
        assert Character.isLetter(postcode.charAt(0));
        assert Character.isLetterOrDigit(postcode.charAt(1));
        this.postcode = postcode;
        this.streetAddress = streetAddress;
    }

    /**
     * Method that calculates whether two locations are near to one another
     * 
     * Returns true if the two location's postcodes have the same first two letters/digits
     * 
     * @param other    the location that the method is comparing to
     * @return         a boolean indicated whether the locations are near (true) or not (false)
     */
    public boolean isNearTo(Location other) {
        // TODO: Implement Location.isNearTo
        boolean isNear;
        String locationA = this.postcode;
        String locationB = other.getPostcode();
        String subLocA = locationA.substring(0,2);
        String subLocB = locationB.substring(0,2);
        subLocA.toUpperCase();
        subLocB.toUpperCase();
        isNear = subLocA.equals(subLocB);
        return isNear;
    }

    /**
     * Getter method for postcode
     * 
     * @return the Location's postcode 
     */
    public String getPostcode() {
        return this.postcode;
    }

    /**
     * Getter method for streetAdress
     * 
     * @return the Location's street address
     */
    public String getStreetAddress() {
        return this.streetAddress;
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
        return Objects.hash(postcode, streetAddress);
    }
    
    /**
     * equals method for testing equality in tests
     * 
     * A method that overrides the normal equals(), and compares Location objects and assessing 
     * whether they are equal. This is done by comparing the streetAddress field and the postcode
     * field.
     * 
     * @param  obj a Location that requires comparison
     * @return     a boolean to indicate whether or not the two locations are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        return Objects.equals(postcode, other.postcode) 
                && Objects.equals(streetAddress, other.streetAddress);
    }
}
