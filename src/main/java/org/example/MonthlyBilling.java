package org.example;

import java.util.Scanner;

public class MonthlyBilling {
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
        Double amountToPay = 0.0;
        if (monthlyConsumption <= 100) {
            amountToPay = 0.10 * monthlyConsumption;
        }
        else if (monthlyConsumption <= 300) {
            amountToPay = 0.10 * monthlyConsumption;
            double localizedConsumption = monthlyConsumption - 100;
            amountToPay += 0.15 * localizedConsumption;
        }
        else if (monthlyConsumption <= 500) {
            amountToPay = 0.15 * monthlyConsumption;
            double localizedConsumption = monthlyConsumption - 300;
            amountToPay += 0.30 * localizedConsumption;
        }
        else {
            amountToPay = 0.30 * monthlyConsumption;
            double localizedConsumption = monthlyConsumption - 500;
            amountToPay += 0.40 * localizedConsumption;
        }
        return amountToPay;
    }
}
