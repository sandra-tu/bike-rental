package uk.ac.ed.bikerental;

public class Location {
    private String postcode;
    private String address;

    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        assert Character.isLetter(postcode.charAt(0));
        assert Character.isLetter(postcode.charAt(1));
        this.postcode = postcode;
        this.address = address;
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
        return postcode;
    }

    public String getAddress() {
        return address;
    }
    // You can add your own methods here
    public static void main(String[] args) {
        Location loc1 = new Location("EH165AY", "27 East Preston Street");
        Location loc2 = new Location("EH89QE", "27 East Preston Street");
        Location loc3 = new Location("123456", "27 East Preston Street");
        Location loc4 = new Location("KY123AA", "27 East Preston Street");
        Location loc5 = new Location("eh234bv", "27 East Preston Street");
        Location loc6 = new Location("b1", "27 East Preston Street");

        System.out.println(loc1.isNearTo(loc2));
        System.out.println(loc2.isNearTo(loc3));
        System.out.println(loc3.isNearTo(loc4));
        System.out.println(loc4.isNearTo(loc5));
        System.out.println(loc5.isNearTo(loc6));
    }
}
