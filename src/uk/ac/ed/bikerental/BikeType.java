package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private Provider provider;
    private BikeTypes bikeType;
    
    public BikeType(Provider provider, BikeTypes bikeType) {
        this.provider = provider;
        this.bikeType = bikeType;
    }
    
    public BigDecimal getReplacementValue() {
        // TODO: Implement Bike.getReplacementValue
        // How do you access the specific provider for this specific bikeType?
        assert false;
        return null;
    }
}