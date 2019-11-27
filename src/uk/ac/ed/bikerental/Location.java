package uk.ac.ed.bikerental;

import java.util.Objects;

public class Location {
    private String postcode, streetAddress;

    public Location(String postcode, String streetAddress) {
        assert postcode.length() >= 6;
        assert Character.isLetter(postcode.charAt(0));
        assert Character.isLetterOrDigit(postcode.charAt(1));
        this.postcode = postcode;
        this.streetAddress = streetAddress;
    }

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

    public String getPostcode() {
        return this.postcode;
    }

    public String getAddress() {
        return this.streetAddress;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(postcode, streetAddress);
    }
    
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
    
    // You can add your own methods here
    public String formatAddress() {
        return this.streetAddress + ", " + this.postcode + ".";    
    }
}
