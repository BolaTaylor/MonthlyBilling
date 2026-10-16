package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyBillingTest {

    private static final double DELTA = 0.001; // Tolerance threshold for floating-point calculations

    @Nested
    @DisplayName("Invalid & Edge Case Inputs")
    class InvalidInputs {

        @Test
        @DisplayName("Should throw IllegalArgumentException when consumption is negative")
        void shouldThrowExceptionForNegativeInput() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                MonthlyBilling.calculateMonthlyPayment(-5.0);
            });
            assertEquals("Monthly payment must be greater than 0", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when consumption is null")
        void shouldThrowExceptionForNullInput() {
            assertThrows(IllegalArgumentException.class, () -> {
                MonthlyBilling.calculateMonthlyPayment(null);
            });
        }

        @Test
        @DisplayName("Should return 0.0 when consumption is exactly 0")
        void shouldReturnZeroForZeroInput() {
            assertEquals(0.0, MonthlyBilling.calculateMonthlyPayment(0.0), DELTA);
        }
    }

    @Nested
    @DisplayName("Tier 1: 0 to 100 (Rate: 0.10 * consumption)")
    class TierOneTests {

        @ParameterizedTest
        @ValueSource(doubles = {0.1, 50.0, 100.0})
        @DisplayName("Should calculate exactly 10% rate for Tier 1 boundaries")
        void shouldCalculateCorrectlyForTierOne(double input) {
            double expected = 0.10 * input;
            assertEquals(expected, MonthlyBilling.calculateMonthlyPayment(input), DELTA);
        }
    }

    @Nested
    @DisplayName("Tier 2: 100.01 to 300 (Formula: 0.10*c + 0.15*(c-100))")
    class TierTwoTests {

        @Test
        @DisplayName("Should calculate correctly for fractional step right above Tier 1")
        void shouldCalculateForFractionalStep() {
            assertEquals(10.125, MonthlyBilling.calculateMonthlyPayment(100.5), DELTA);
        }

        @Test
        @DisplayName("Should calculate correctly at Tier 2 upper boundary (300)")
        void shouldCalculateAtUpperBoundary() {
            assertEquals(60.0, MonthlyBilling.calculateMonthlyPayment(300.0), DELTA);
        }
    }

    @Nested
    @DisplayName("Tier 3: 300.01 to 500 (Formula: 0.15*c + 0.30*(c-300))")
    class TierThreeTests {

        @Test
        @DisplayName("Should calculate correctly for mid-range Tier 3 value")
        void shouldCalculateForMidRange() {
            assertEquals(90.0, MonthlyBilling.calculateMonthlyPayment(400.0), DELTA);
        }

        @Test
        @DisplayName("Should calculate correctly at Tier 3 upper boundary (500)")
        void shouldCalculateAtUpperBoundary() {
            assertEquals(135.0, MonthlyBilling.calculateMonthlyPayment(500.0), DELTA);
        }
    }

    @Nested
    @DisplayName("Tier 4: Above 500 (Formula: 0.30*c + 0.40*(c-500))")
    class TierFourTests {

        @Test
        @DisplayName("Should calculate correctly for Tier 4 values")
        void shouldCalculateForTierFour() {
            assertEquals(220.0, MonthlyBilling.calculateMonthlyPayment(600.0), DELTA);
        }
    }
}
