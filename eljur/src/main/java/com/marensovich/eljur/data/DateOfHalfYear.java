package com.marensovich.eljur.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The enum Date of half year.
 */
public enum DateOfHalfYear {

    /**
     * First date of half year.
     */
    FIRST(
            LocalDate.parse("01.09.2025", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            LocalDate.parse("29.12.2025", DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    ),
    /**
     * Second date of half year.
     */
    SECOND(
            LocalDate.parse("13.01.2026", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            LocalDate.parse("15.07.2026", DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    );

    private final LocalDate startDate;
    private final LocalDate endDate;

    DateOfHalfYear(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " +
                endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}