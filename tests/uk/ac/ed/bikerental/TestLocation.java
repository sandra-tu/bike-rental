package uk.ac.ed.bikerental;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLocation {
	private Location loc1, loc2, loc3;
	
    @BeforeEach
    void setUp() throws Exception{
        System.out.println("what");
    	this.loc1 = new Location("EH15 7TT", "ookyspooky");
    	this.loc2 = new Location("EH5 4NG", "hmmm");
    	this.loc3 = new Location("GB12 6FF", "longwalk");
    }
    
    @Test
    public void testIsNearTo() {
        System.out.println("hey");
        boolean close = loc1.isNearTo(loc2);
        boolean far = loc1.isNearTo(loc3);
        assertEquals(true, close);
        assertEquals(false, far);
    }
}
