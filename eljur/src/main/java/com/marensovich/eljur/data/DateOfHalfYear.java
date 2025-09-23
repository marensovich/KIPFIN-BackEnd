package com.marensovich.eljur.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the academic half-years with their start and end dates.
 *
 * <p>Each constant stores the date range of one semester (half of the study year).
 * Used in scheduling, grade reports, and other date-related operations.</p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public enum DateOfHalfYear {

    /**
     * First half of the year (September–December).
     */
    FIRST(
            LocalDate.parse("01.09.2025", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            LocalDate.parse("29.12.2025", DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    ),

    /**
     * Second half of the year (January–July).
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
     * Gets the start date of the half-year.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the half-year.
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
