package com.marensovich.eljur.data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents final grades in the academic system.
 *
 * <p>Includes both numeric values (2–5) and "not attested" (NA).</p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public enum FinalScoresType {

    TWO(2, "Два"),
    THREE(3, "Три"),
    FOUR(4, "Четыре"),
    FIVE(5, "Пять"),
    NA(0, "Не аттестован");

    private final int numericValue;
    private final String description;

    FinalScoresType(int numericValue, String description) {
        this.numericValue = numericValue;
        this.description = description;
    }

    /**
     * Gets numeric value of the grade.
     *
     * @return the numeric value
     */
    public int getNumericValue() {
        return numericValue;
    }

    /**
     * Gets description of the grade.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the grade means "not attested".
     *
     * @return true if NA, false otherwise
     */
    public boolean isNotAttested() {
        return this == NA;
    }

    /**
     * Creates a FinalScoresType from string value.
     *
     * @param value string value ("Два", "Пять", etc.)
     * @return corresponding FinalScoresType
     */
    public static FinalScoresType fromString(String value) {
        for (FinalScoresType score : FinalScoresType.values()) {
            if (score.description.equalsIgnoreCase(value) || score.name().equalsIgnoreCase(value)) {
                return score;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + value);
    }

    /**
     * Returns all grade types.
     *
     * @return list of all scores
     */
    public static List<FinalScoresType> getAllScores() {
        return List.of(FinalScoresType.values());
    }

    /**
     * Returns all grade descriptions.
     *
     * @return list of descriptions
     */
    public static List<String> getAllDescriptions() {
        return List.of(FinalScoresType.values()).stream()
                .map(FinalScoresType::getDescription)
                .collect(Collectors.toList());
    }

    /**
     * Creates a FinalScoresType from numeric value.
     *
     * @param numericValue numeric value (2–5)
     * @return corresponding FinalScoresType
     */
    public static FinalScoresType fromNumericValue(int numericValue) {
        for (FinalScoresType score : FinalScoresType.values()) {
            if (score.numericValue == numericValue) {
                return score;
            }
        }
        throw new IllegalArgumentException("Неизвестное числовое значение: " + numericValue);
    }

    @Override
    public String toString() {
        return description;
    }
}
