package com.github.rustock0.fuzzy;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Implementation of methods to work with Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class FuzzyNumber extends FuzzySet {

    public FuzzyNumber(Map<Double, Double> set) {
        super(set);
    }

    /**
     * Returns a result of addition current and specified numbers.
     *
     * @param anotherNumber an another fuzzy number.
     * @return see description.
     */
    public FuzzyNumber add(FuzzyNumber anotherNumber) {
        return action(anotherNumber, (first, second) -> first + second);
    }

    /**
     * Returns a result of subtraction current and specified numbers.
     *
     * @param anotherNumber an another fuzzy number.
     * @return see description.
     */
    public FuzzyNumber subtract(FuzzyNumber anotherNumber) {
        return action(anotherNumber, (first, second) -> first - second);
    }

    /**
     * Returns a result of multiplication of the current and specified numbers.
     *
     * @param anotherNumber an another fuzzy number.
     * @return see description.
     */
    public FuzzyNumber multiply(FuzzyNumber anotherNumber) {
        return action(anotherNumber, (first, second) -> first * second);
    }

    /**
     * Returns a result of division of the current and specified numbers.
     *
     * @param anotherNumber an another fuzzy number.
     * @return see description.
     */
    public FuzzyNumber divide(FuzzyNumber anotherNumber) {
        return action(anotherNumber, (first, second) -> first / second);
    }

    /**
     * Returns an extra maximum of the current and specified numbers.
     *
     * @param anotherNumber an another fuzzy number.
     * @return see description.
     */
    public FuzzyNumber extraMaximum(FuzzyNumber anotherNumber) {
        return action(anotherNumber, Math::max);
    }

    /**
     * Returns an extra minimum of the current and specified numbers.
     *
     * @param anotherNumber an another fuzzy number.
     * @return see description.
     */
    public FuzzyNumber extraMinimum(FuzzyNumber anotherNumber) {
        return action(anotherNumber, Math::min);
    }

    /**
     * Returns a result of next expression:
     * A1 function A2 = (sup {(x1, x2) | x1 function x2}; min {mu(x1), mu(x2)})
     * where A1 is the current fuzzy number and A2 is a specified fuzzy number,
     * and action is a specified function, e.g. A1*A2.
     *
     * @param anotherSet an another fuzzy number.
     * @param function   function for calculation, e.g. A1*A2.
     * @return see description.
     */
    private FuzzyNumber action(FuzzyNumber anotherSet, BiFunction<Double, Double, Double> function) {
        Map<Double, Double> result = new TreeMap<>();
        for (Map.Entry<Double, Double> entrySet : set.entrySet()) {
            for (Map.Entry<Double, Double> anotherEntrySet : anotherSet.set.entrySet()) {
                double key = function.apply(entrySet.getKey(), anotherEntrySet.getKey());
                double value = Math.min(entrySet.getValue(), anotherEntrySet.getValue());
                if (result.containsKey(key)) {
                    double oldValue = result.get(key);
                    value = Math.max(value, oldValue);
                }
                result.put(key, value);
            }
        }
        return new FuzzyNumber(result);
    }
}
