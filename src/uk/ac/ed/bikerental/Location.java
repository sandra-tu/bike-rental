package uk.ac.ed.bikerental;

public class Location {
    private String postcode;
    private String streetAddress;

    public Location(String postcode, String streetAddress) {
        assert postcode.length() >= 6;
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
    
    // You can add your own methods here
    public String formatAddress() {
        return this.streetAddress + ", " + this.postcode + ".";    
    }
}
