package Tester.Models;

/**
 * A class representing a simple arithmetic equation with 2 floating-point operands and one operator.
 */
public class Equation {

    public enum Operator {ADDITION, SUBTRACTION} // ADDITION(+), SUBTRACTION(-)

    private final Double firstValue;             // First operand
    private final Double secondValue;            // Second operand
    private final Operator operator;             // Operator being used

    public Equation(Double smallestPossibleValue, Double largestPossibleValue, Operator operator) {
        double rangeMultiplier = (largestPossibleValue - smallestPossibleValue + 1) + smallestPossibleValue;
        this.firstValue = Math.random() * rangeMultiplier;
        this.secondValue = Math.random() * rangeMultiplier;
        this.operator = operator;
    }

    public Double getFirstValue() {
        return firstValue;
    }

    public Double getSecondValue() {
        return secondValue;
    }

    public String getOperatorString() {
        return operator == Operator.ADDITION ? "+" : "-";
    }
}