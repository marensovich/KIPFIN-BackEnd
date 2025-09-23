package com.marensovich.eljur.data;


import java.util.List;
import java.util.stream.Collectors;

/**
 * The enum Final scores type.
 */
public enum FinalScoresType {

    /**
     * Two final scores type.
     */
    TWO(2, "Два"),
    /**
     * Three final scores type.
     */
    THREE(3, "Три"),
    /**
     * Four final scores type.
     */
    FOUR(4, "Четыре"),
    /**
     * Five final scores type.
     */
    FIVE(5, "Пять"),
    /**
     * Na final scores type.
     */
    NA(0, "Не аттестован");

    private final int numericValue;
    private final String description;

    FinalScoresType(int numericValue, String description) {
        this.numericValue = numericValue;
        this.description = description;
    }

    /**
     * Gets numeric value.
     *
     * @return the numeric value
     */
    public int getNumericValue() {
        return numericValue;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Is not attested boolean.
     *
     * @return the boolean
     */
    public boolean isNotAttested() {
        return this == NA;
    }

    /**
     * From string final scores type.
     *
     * @param value the value
     * @return the final scores type
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
     * Gets all scores.
     *
     * @return the all scores
     */
    public static List<FinalScoresType> getAllScores() {
        return List.of(FinalScoresType.values());
    }

    /**
     * Gets all descriptions.
     *
     * @return the all descriptions
     */
    public static List<String> getAllDescriptions() {
        return List.of(FinalScoresType.values()).stream()
                .map(FinalScoresType::getDescription)
                .collect(Collectors.toList());
    }

    /**
     * From numeric value final scores type.
     *
     * @param numericValue the numeric value
     * @return the final scores type
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