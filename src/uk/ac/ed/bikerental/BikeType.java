package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private BikeTypes bikeType;
    private BigDecimal dailyPrice;
    
    
    public BikeType(BikeTypes bikeType, BigDecimal dailyPrice) {
        this.bikeType = bikeType;
        this.dailyPrice = dailyPrice;
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
        // How do you access the specific provider for this specific bikeType? hehe i guess we won't
        
        assert false;
        return null;
    }
    
}