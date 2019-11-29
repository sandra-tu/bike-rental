package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BikeType {
    private BikeTypes bikeType;
    private BigDecimal replacementValue;
    public static Set<BikeTypes> bikeTypeSet = new HashSet<>();
    
    public BikeType(BikeTypes bikeType, BigDecimal replacementValue) {
        this.bikeType = bikeType;
        if (replacementValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Replacement vlaue cannot be negative");
        } else {
            this.replacementValue = replacementValue;
        }         
    }
        
    public enum BikeTypes {
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
    
    @Override
    public int hashCode() {
        return Objects.hash(bikeType, replacementValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BikeType other = (BikeType) obj;
        return Objects.equals(bikeType, other.bikeType) 
                && Objects.equals(replacementValue, other.replacementValue);
    }    
}