import Tester.Calculator;
import Tester.CalculatorTester;

/**
 * Runner is the starting point for running the calculator tests.
 *
 * Simply create new calculator objects for each of the crystals being tested.
 * Pass each calculator into calculatorTester, and then you will see the results of running the test.
 */

public class Runner {

    private static final int NUMBER_OF_TESTS_TO_RUN  = 20;   // The number of arithmetic tests to perform per calculator
    private static final double OPERAND_MIN_VALUE    = 0.0;  // The minimum value of operands used in test equations
    private static final double OPERAND_MAX_VALUE    = 10.0; // The maximum value of operands used in test equations

    public static void main(String[] args) {
        Calculator calculatorOne   = new Calculator("Crystal 1");
        Calculator calculatorTwo   = new Calculator("Crystal 2");
        Calculator calculatorThree = new Calculator("Crystal 3");
        Calculator calculatorFour  = new Calculator("Crystal 4");
        Calculator calculatorFive  = new Calculator("Crystal 5");
        CalculatorTester calculatorTester = new CalculatorTester(
                NUMBER_OF_TESTS_TO_RUN,
                OPERAND_MIN_VALUE,
                OPERAND_MAX_VALUE,
                calculatorOne, calculatorTwo, calculatorThree, calculatorFour, calculatorFive
        );
        calculatorTester.runCalculatorTests();
    }
}