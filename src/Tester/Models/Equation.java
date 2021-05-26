package Tester.Models;

public class Equation {

    enum Operator {ADDITION , SUBTRACTION}

    private final Double firstValue;
    private final Double secondValue;
    private final Operator operator;

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