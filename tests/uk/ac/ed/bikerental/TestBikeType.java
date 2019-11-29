package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

class TestBikeType {
    private BikeType mountainBike, roadBike, eBike, otherBike;
    
    @BeforeEach
    void setUp() throws Exception {
        this.mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        this.roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(150.00));
        this.eBike = new BikeType(BikeTypes.EBIKE, new BigDecimal(9.00));
        this.otherBike = new BikeType(BikeTypes.OTHERBIKE, new BigDecimal(250.00));
    }
    
    @Test
    public void testExpectedAssertions() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(1.00));
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(-1.00));
        });
    }
    
}
