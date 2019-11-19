package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private BikeTypes bikeType;
    private BigDecimal replacementValue; // Should this be final? Or maybe it just is by virtue of there not being a setter
    
    public BikeType(BikeTypes bikeType, BigDecimal replacementValue) {
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
    
    public static void main(String[] args) {
        BikeType mountain = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(30.00));
        System.out.println(mountain.getReplacementValue());
    }
    
}