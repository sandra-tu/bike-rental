package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class TestInput {
    private Input input1, input2, input3, input4, input5;
    
    //Is the duplicated code in the two tests necessary? Or is there some way to avoid that?
    
//    @BeforeEach
//    void setUp() throws Exception {
//        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(20.00));
//        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(15.00));
//        BikeType[] bikesRequested1 = {mountainBike};
//        BikeType[] bikesRequested2 = {mountainBike, mountainBike, roadBike};
//        Location location = new Location("Eh165AY", "Street name");
//    }
    
    @Test
    public void constructorTest() {
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(20.00));
        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(15.00));
        BikeType[] bikesRequested1 = {mountainBike};
        BikeType[] bikesRequested2 = {mountainBike, mountainBike, roadBike};
        Location location = new Location("EH165AY", "Street name");
        
        input1 = new Input(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10),
                bikesRequested1, location);
        input2 = new Input(LocalDate.of(2019, 5, 10), LocalDate.of(2019, 6, 3),
                bikesRequested2, location);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionTest() {
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(20.00));
        BikeType[] bikesRequested1 = {mountainBike};
        Location location = new Location("EH165AY", "Street name");
        
        input3 = new Input(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 1),
                bikesRequested1, location);
        input4 = new Input(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10),
                null, location);
        input5 = new Input(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10),
                bikesRequested1, null);
    }
}
