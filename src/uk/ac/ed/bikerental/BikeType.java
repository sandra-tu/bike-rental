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
//        assert !bikeTypeSet.contains(bikeType);
        this.bikeType = bikeType;
        if (replacementValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Replacement vlaue cannot be negative");
        } else {
            this.replacementValue = replacementValue;
        }
        
        //Throws error if the replacement value has already been set for a bike type
        if (bikeTypeSet.contains(this.bikeType)) {
            throw new IllegalArgumentException("This bike type already exists");
        } else {
            bikeTypeSet.add(this.bikeType);
        }
                
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
    
    public static void main(String[] args) {
        BikeType mountain = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(30.00));
        BikeType city = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(20.00));
        System.out.println(mountain.getReplacementValue());
        System.out.println(city.getReplacementValue());
    }
    
}