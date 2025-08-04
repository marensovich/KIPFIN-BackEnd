package com.marensovich.eljur.data;


import java.util.List;
import java.util.stream.Collectors;

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

    public int getNumericValue() {
        return numericValue;
    }

    public String getDescription() {
        return description;
    }

    public boolean isNotAttested() {
        return this == NA;
    }

    public static FinalScoresType fromString(String value) {
        for (FinalScoresType score : FinalScoresType.values()) {
            if (score.description.equalsIgnoreCase(value) || score.name().equalsIgnoreCase(value)) {
                return score;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + value);
    }

    public static List<FinalScoresType> getAllScores() {
        return List.of(FinalScoresType.values());
    }

    public static List<String> getAllDescriptions() {
        return List.of(FinalScoresType.values()).stream()
                .map(FinalScoresType::getDescription)
                .collect(Collectors.toList());
    }

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