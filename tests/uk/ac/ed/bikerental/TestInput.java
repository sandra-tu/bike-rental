package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.junit.Test;
import uk.ac.ed.bikerental.BikeType.BikeTypes;

public class TestInput {
    private Input input1, input2, input3, input4;
    
    //@Sandra - please help
    //Is the duplicated code in the two tests necessary? Or is there some way to avoid that?
    //Is the setUp() basically a constructor test? Because it feels like it might be exactly what
    //    it's doing
    
    @BeforeEach
    void setUp() throws Exception {
//        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(20.00));
//        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(15.00));
//        BikeType[] bikesRequested1 = {mountainBike};
//        BikeType[] bikesRequested2 = {mountainBike, mountainBike, roadBike};
//        Location location = new Location("Eh165AY", "Street name");
    }
    
    @Test
    public void constructorTest() throws Exception {    
        DateRange dateRange = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(20.00));
        BikeType roadBike = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(15.00));
        ArrayList<BikeType> bikesRequested1 = new ArrayList<BikeType>(
                Arrays.asList(mountainBike));
        ArrayList<BikeType> bikesRequested2 = new ArrayList<BikeType>(
                Arrays.asList(mountainBike, 
                              mountainBike, 
                              roadBike));        
        Location location = new Location("EH165AY", "Street name");
        this.input1 = new Input(dateRange, bikesRequested1, location);
        this.input2 = new Input(dateRange, bikesRequested2, location);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionTest() {
        DateRange dateRange = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        BikeType mountainBike = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(20.00));
        ArrayList<BikeType> bikesRequested1 = new ArrayList<BikeType>(
                Arrays.asList(mountainBike));
        Location location = new Location("EH165AY", "Street name");
    
        this.input3 = new Input(dateRange, null, location);
        this.input4 = new Input(dateRange, bikesRequested1, null);
    }
}
