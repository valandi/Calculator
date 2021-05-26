package Tester;

import Tester.Models.Equation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Manages all tests being performed on calculators.
 * <p>
 * Generates the test Threads.
 * Executes the test Threads.
 * Determines results and outputs results to console.
 */
public class CalculatorTester {

    private final int numberOfTests;                                // Total number of tests to run per calculator
    private final Calculator[] calculators;                         // All calculators being tested
    private final Equation[] equations;                             // All equations to use in tests
    private final Map<String, Double> calculatorSuccessRates;       // Map of calculator name to success rate
    private final List<CalculatorTestThread> calculatorRunnables;   // List of CalculatorTest threads
    private final CountDownLatch countDownLatch;                    // Used to determine when all tasks are finished

    public CalculatorTester(int numberOfTests, double operandMinValue, double operandMaxValue, Calculator... calculators) {
        this.numberOfTests = numberOfTests;
        this.calculators = calculators;
        this.equations = new Equation[numberOfTests];
        for (int i = 0; i < numberOfTests; i++) {
            this.equations[i] = new Equation(
                    operandMinValue,
                    operandMinValue,
                    Math.random() > 0.5 ? Equation.Operator.ADDITION : Equation.Operator.SUBTRACTION
            );
        }

        this.calculatorSuccessRates = new LinkedHashMap<>();

        this.calculatorRunnables = Collections.synchronizedList(new ArrayList<>());
        this.countDownLatch = new CountDownLatch(calculators.length);
    }

    /**
     * Exposed method that Runner invokes to execute calculator tests.
     */
    public void runCalculatorTests() {
        createCalculatorTests();
        executeTests();
        determineAndPrintResult();
    }

    /**
     * Creates the CalculatorTest threads and adds them to list.
     */
    private void createCalculatorTests() {
        Arrays.stream(calculators).forEach(calculator ->
                calculatorRunnables.add(new CalculatorTestThread(calculator, equations, numberOfTests, countDownLatch))
        );
    }

    /**
     * Call run on each CalculatorTestThread in CalculatorRunnables
     * Wait for the CountDownLatch to reach zero, blocking determineAndPrintResult() until all tasks are complete.
     */
    private void executeTests() {

        calculatorRunnables.forEach(CalculatorTestThread::run); // Run each of the test threads

        try {
            countDownLatch.await(); // Wait for the CountdownLatch to hit 0
        } catch (InterruptedException e) {
            System.out.println("There was an issue waiting for each task to finish :(");
            System.out.println(e.toString());
        }
    }

    /**
     * Find the calculator thread that reports the highest success rate.
     * Output results of experiment to console.
     */
    private void determineAndPrintResult() {
        // Find the calculator thread that reports the highest success rates
        String calculatorMostSuccessName = "";
        String currName;
        double maxSuccess = 0.0;
        double currSuccess;

        /**
         * Keep track of the maxSuccess rate as we go along.
         * If we find a calculatorTestThread that reports a higher success rate than maxSuccess
         * Set maxSuccess to the current success rate and calculatorMostSuccessName as the current calculator name
         */
        for (CalculatorTestThread calculatorTestThread : calculatorRunnables) {
            currSuccess = calculatorTestThread.getSuccessRate();
            currName = calculatorTestThread.getCalculator().getName();

            if (currSuccess >= maxSuccess) {
                maxSuccess = currSuccess;
                calculatorMostSuccessName = calculatorTestThread.getCalculator().getName();
            }

            calculatorSuccessRates.put(currName, currSuccess);
        }

        // Print the logs for each of the thread
        calculatorRunnables.forEach(CalculatorTestThread::printLog);

        // Print the message indicating which calculator performed best
        calculatorSuccessRates.keySet().stream()
                .map(calculatorName -> calculatorName + " Success rate: " + calculatorSuccessRates.get(calculatorName))
                .forEach(System.out::println);
        System.out.println(calculatorMostSuccessName + " is better");
    }
}
