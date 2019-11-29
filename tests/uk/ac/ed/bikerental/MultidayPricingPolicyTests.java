package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MultidayPricingPolicyTests {
    private Provider p;
    
    @BeforeEach
    void setUp() {
        this.p = new Provider("p", new Location("aaaaaa","a"), new BigDecimal(0));
    }

    @Test
    void testPricingPolicyUnequalArgumentSizes() {
        int[] thresholds = {2, 6, 13, 14};
        double[] discounts = {0.95, 0.9, 0.85};
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            MultidayPricingPolicy pp = new MultidayPricingPolicy(thresholds, discounts, p);
        });
    }

}
