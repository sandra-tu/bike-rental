package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3, dateRange4,
        dateRange5, dateRange6, dateRange7;

    @BeforeEach
    void setUp() throws Exception { //Maybe it throws and exception if it can't create these objects
        // Setup resources before each test
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                LocalDate.of(2018, 1, 10));
        this.dateRange4 = new DateRange(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 10));
        this.dateRange5 = new DateRange(LocalDate.of(2020, 2, 8),
                LocalDate.of(2020, 2, 19));
        this.dateRange6 = new DateRange(LocalDate.of(2020, 1, 7),
                LocalDate.of(2020, 1, 7));

    }

    // Sample JUnit tests checking toYears works
    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    @Test
    void testOverlapsTrue() {
        // TODO: check we can see when two date ranges overlap
        assertTrue(this.dateRange1.overlaps(this.dateRange2));
        assertTrue(this.dateRange4.overlaps(this.dateRange4));
        assertTrue(this.dateRange5.overlaps(this.dateRange5));
        assertTrue(this.dateRange4.overlaps(this.dateRange6));
    }

    @Test
    void testOverlapsFalse() {
        // TODO: check we can see when two date ranges  don't overlap
        assertFalse(this.dateRange2.overlaps(this.dateRange3));
        assertFalse(this.dateRange4.overlaps(this.dateRange5));
        assertFalse(this.dateRange5.overlaps(this.dateRange4));
        assertFalse(this.dateRange6.overlaps(this.dateRange5));
    }

    // TODO: put some of your own unit tests here
    
    @Test
    void testEndBeforeStartDate() { //Also shouldn't this date range be declared in the BeforeEach bit? it can't be because it throws an error
        Assertions.assertThrows(AssertionError.class, () -> {
            this.dateRange7 = new DateRange(LocalDate.of(2020, 1, 7),
                    LocalDate.of(2019, 1, 10)); //Why would this throw an error? the 7th is before the 10th, the years are janked
        });
    }
}
