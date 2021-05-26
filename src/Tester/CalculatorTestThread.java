package Tester;

import Tester.Models.Equation;

import java.util.concurrent.CountDownLatch;

/**
 * CalculatorTest provides the runnable Thread that will be used to test each individual crystal calculator.
 * <p>
 * For each equation, the calculator will attempt to perform floating-point arithmetic operations on two values,
 * and will compare the value to calculations made in Java.
 * <p>
 * Success rate is determined at the end and is accessible from CalculatorTest
 */

public class CalculatorTestThread extends Thread {

    private final Calculator calculator;            // The calculator to test
    private final StringBuilder logBuilder;         // A StringBuilder that will keep track of logs
    private final Equation[] equations;             // An array of equations to be used for tests
    private final int numberOfTests;                // The number of tests to perform
    private final CountDownLatch countDownLatch;    // CountDownLatch to ensure all tasks are completed before reporting results

    private int successes;                          // Number of correct computations made by calculator
    private Double successRate;                     // Overall success rate (successes / numberOfTests)


    public CalculatorTestThread(Calculator calculator, Equation[] equations, int numberOfTests, CountDownLatch latch) {
        this.calculator = calculator;
        this.logBuilder = new StringBuilder();
        this.equations = equations;
        this.numberOfTests = numberOfTests;
        this.countDownLatch = latch;
        this.successes = 0;
        this.successRate = 0.0;
    }

    /**
     * Compares the result of the calculator performing calculations given the 2 operands and operator.
     * Determines the number of successes and determines success rate by dividing successes by the number of tests run
     */
    @Override
    public void run() {

        // Initial Header for results output
        logBuilder.append("Calculator ").append(calculator.getName()).append("\n");


        double expected;

        // For each pair, perform calculation, and increment success if calculation is good
        for (Equation equation : equations) {
            double first = equation.getFirstValue();
            double second = equation.getSecondValue();
            String operator = equation.getOperatorString();

            logBuilder.append(String.format("%-20s", first)).append(" ")
                    .append(operator).append(" ")
                    .append(String.format("%-20s", first)).append(" = ");

            /*
              Check to see what operator is being used to determine output and to determine correctness
              TODO: Refactor the duplicate code
             */
            if (operator.equals("+")) {
                expected = first + second;
                logBuilder.append(String.format("%-25s", expected));
                if (this.calculator.add(first, second) == expected) {
                    logBuilder.append("(correct)");
                    successes++;
                } else {
                    logBuilder.append("(error)");
                }
            } else {
                expected = first - second;
                logBuilder.append(String.format("%-25s", expected));
                if (this.calculator.subtract(first, second) == expected) {
                    logBuilder.append("(correct)");
                    successes++;
                } else {
                    logBuilder.append("(error)");
                }
            }
            logBuilder.append("\n");
        }

        // Calculate successRate
        this.successRate = (double) successes / numberOfTests;

        // Decrement countDownLatch to indicate that the task is finished executing
        countDownLatch.countDown();
    }

    /**
     * Constructs and prints String from log builder
     */
    public void printLog() {
        System.out.println(logBuilder.toString());
    }

    public Calculator getCalculator() {
        return this.calculator;
    }

    public double getSuccessRate() {
        return this.successRate;
    }
}
