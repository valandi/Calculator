package Tester;


import Tester.Models.Equation;

/**
 * CalculatorTest provides the runnable Thread that will be used to test each individual crystal calculator.
 *
 * For each equation, the calculator will attempt to perform floating-point arithmetic operations on two values,
 * and will compare the value to calculations made in Java.
 *
 * Success rate is determined at the end and is accessible from CalculatorTest
 */

public class CalculatorTestThread extends Thread {

    private final Calculator calculator;
    private final StringBuilder logBuilder;
    private final Equation[] equations;
    private final int numberOfTests;
    private int successes;
    private Double successRate;

    public CalculatorTestThread(Calculator calculator, Equation[] equations, int numberOfTests) {
        this.calculator = calculator;
        this.logBuilder = new StringBuilder();
        this.successes = 0;
        this.successRate = 0.0;
        this.equations = equations;
        this.numberOfTests = numberOfTests;
    }

    /**
     * Compares the result of the calculator performing calculations given the 2 operands and operator.
     * Determines the number of successes and determines success rate by dividing successes by the number of tests run
     */
    @Override
    public void run() {

        logBuilder.append("Tester.Calculator ").append(calculator.getName()).append("\n");

        // For each pair, perform calculation, and increment success if calculation is good
        double expected;
        for(Equation equation : equations) {
            double first = equation.getFirstValue();
            double second = equation.getSecondValue();
            String operator = equation.getOperatorString();

            logBuilder.append(first).append(" ").append(operator).append(" ").append(second).append(" = ");

            if (operator.equals("+")) {
                expected = first + second;
                logBuilder.append(expected);
                if (this.calculator.add(first, second) == expected) {
                    logBuilder.append(String.format("%25s", "(correct)"));
                    successes++;
                } else {
                    logBuilder.append(String.format("%25s", "(error)"));
                }
            } else {
                expected = first - second;
                logBuilder.append(expected);
                if (this.calculator.subtract(first, second) == expected) {
                    logBuilder.append(String.format("%25s", "(correct)"));
                    successes++;
                } else {
                    logBuilder.append(String.format("%25s", "(error)"));
                }
            }
            logBuilder.append("\n");
        }

        // Calculate successRate
        this.successRate = (double) successes / numberOfTests;
    }

    /**
     * Constructs and prints String from log builder
     */
    public void printLog() { System.out.println(logBuilder.toString()); }
}
