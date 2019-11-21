package uk.ac.ed.bikerental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLocation {
	private Location loc1, loc2, loc3, loc4;
	
    @BeforeEach
    void setUp() throws Exception{
        // TODO: setup some resources before each test
    	this.loc1 = new Location("EH15 7TT", "1 Newington Rd.");
    	this.loc2 = new Location("EH5 4NG", "1 Newington Rd.");
    	this.loc3 = new Location("GB12 6FF", "1 Newington Rd.");
    }
    
    // TODO: put some tests here
    @Test
    public void testIsNearTo() {
        boolean close = loc1.isNearTo(loc2);
        boolean far = loc1.isNearTo(loc3);
        assertEquals(true, close);
        assertEquals(false, far);
    }
    
    // Tests to assert that the postcode is valid
    @Test
    public void testExpectedAssertions() {
        Assertions.assertThrows(AssertionError.class, () -> {
            this.loc4 = new Location("1234 231", "1 Newington Rd.");
        });
        
        Assertions.assertThrows(AssertionError.class, () -> {
            this.loc4 = new Location("123", "1 Newington Rd.");
        });
    }
}
