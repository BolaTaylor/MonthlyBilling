package org.example;

import java.util.Scanner;

public class MonthlyBillingTwo {
    public static void main(String[] args) {
        System.out.println("Please Enter in a valid monthly billing amount");
        try (Scanner scanner = new Scanner(System.in)) {
            Double amount = scanner.nextDouble();
            Double monthlyAmount = calculateMonthlyPayment(amount);
            System.out.printf("The monthly billing amount is: %.2f%n", monthlyAmount);
        } catch (Exception e) {
            System.out.println("Invalid monthly billing amount");
        }
    }

    public static Double calculateMonthlyPayment(Double monthlyConsumption) {
        if (monthlyConsumption == null || monthlyConsumption < 0) {
            throw new IllegalArgumentException("Monthly payment must be greater than 0");
        }

        // Using modern Java switch expression with pattern matching (when clauses)
        return switch (monthlyConsumption) {
            case Double c when c <= 100 ->
                    0.10 * c;

            case Double c when c <= 300 ->
                    (0.10 * c) + (0.15 * (c - 100));

            case Double c when c <= 500 ->
                    (0.15 * c) + (0.30 * (c - 300));

            default ->
                    (0.30 * monthlyConsumption) + (0.40 * (monthlyConsumption - 500));
        };
    }
}
