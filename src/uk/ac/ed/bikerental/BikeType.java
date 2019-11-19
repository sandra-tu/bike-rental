package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private BikeTypes bikeType;
    private BigDecimal replacementValue; // Should this be final? Or maybe it just is by virtue of there not being a setter
    
    public BikeType(BikeTypes bikeType) {
        this.bikeType = bikeType;
        this.replacementValue = replacementValue;
    }
    
    public enum BikeTypes{
        ROADBIKE,
        MOUNTAINBIKE,
        HYBRIDBIKE,
        EBIKE,
        OTHERBIKE
    }
    
    public BigDecimal getReplacementValue() {
        // TODO: Implement Bike.getReplacementValue
        return replacementValue;
    }
    
}