package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyBillingTwoTest {

    private static final double DELTA = 0.001; // Tolerance for floating-point comparisons

    @ParameterizedTest(name = "Consumption of {0} should result in a bill of {1}")
    @CsvSource({
            "0.0, 0.0",       // Lower bound of Tier 1
            "50.0, 5.0",      // Midpoint Tier 1 (50 * 0.10)
            "100.0, 10.0"     // Upper bound Tier 1 (100 * 0.10)
    })
    @DisplayName("Tier 1: Consumption between 0 and 100")
    void testTierOneBilling(double consumption, double expectedBill) {
        assertEquals(expectedBill, MonthlyBilling.calculateMonthlyPayment(consumption), DELTA);
    }

    @ParameterizedTest(name = "Consumption of {0} should result in a bill of {1}")
    @CsvSource({
            "100.01, 10.0025", // Just above Tier 1 boundary
            "200.0, 35.0",     // Midpoint Tier 2: (0.10 * 200) + (0.15 * 100) = 20 + 15
            "300.0, 60.0"      // Upper bound Tier 2: (0.10 * 300) + (0.15 * 200) = 30 + 30
    })
    @DisplayName("Tier 2: Consumption between 100 and 300")
    void testTierTwoBilling(double consumption, double expectedBill) {
        assertEquals(expectedBill, MonthlyBilling.calculateMonthlyPayment(consumption), DELTA);
    }

    @ParameterizedTest(name = "Consumption of {0} should result in a bill of {1}")
    @CsvSource({
            "300.01, 45.0045", // Just above Tier 2 boundary
            "400.0, 90.0",     // Midpoint Tier 3: (0.15 * 400) + (0.30 * 100) = 60 + 30
            "500.0, 135.0"     // Upper bound Tier 3: (0.15 * 500) + (0.30 * 200) = 75 + 60
    })
    @DisplayName("Tier 3: Consumption between 300 and 500")
    void testTierThreeBilling(double consumption, double expectedBill) {
        assertEquals(expectedBill, MonthlyBilling.calculateMonthlyPayment(consumption), DELTA);
    }

    @ParameterizedTest(name = "Consumption of {0} should result in a bill of {1}")
    @CsvSource({
            "500.01, 150.007", // Just above Tier 3 boundary
            "600.0, 220.0"     // Beyond Tier 3: (0.30 * 600) + (0.40 * 100) = 180 + 40
    })
    @DisplayName("Tier 4: Consumption above 500")
    void testTierFourBilling(double consumption, double expectedBill) {
        assertEquals(expectedBill, MonthlyBilling.calculateMonthlyPayment(consumption), DELTA);
    }

    @Test
    @DisplayName("Exception: Negative consumption throws IllegalArgumentException")
    void testNegativeConsumptionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MonthlyBilling.calculateMonthlyPayment(-10.5);
        });
        assertEquals("Monthly payment must be greater than 0", exception.getMessage());
    }

    @Test
    @DisplayName("Exception: Null consumption throws IllegalArgumentException")
    void testNullConsumptionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MonthlyBilling.calculateMonthlyPayment(null);
        });
        assertEquals("Monthly payment must be greater than 0", exception.getMessage());
    }
}
