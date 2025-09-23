package com.marensovich.eljur.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Visit service.
 */
@Service
public class VisitService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Record visit.
     */
//TODO: Разобраться в сервисе хранения данных о посещениях пользователей.
    public void recordVisit() {
        String sql = "INSERT INTO site_visits (visit_time) VALUES (NOW())";
        jdbcTemplate.update(sql);
    }

    /**
     * Gets visits last 10 minutes.
     *
     * @return the visits last 10 minutes
     */
    public int getVisitsLast10Minutes() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 10 MINUTE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Gets visits last hour.
     *
     * @return the visits last hour
     */
    public int getVisitsLastHour() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 1 HOUR";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Gets visits last 12 hours.
     *
     * @return the visits last 12 hours
     */
    public int getVisitsLast12Hours() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 12 HOUR";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Gets visits last day.
     *
     * @return the visits last day
     */
    public int getVisitsLastDay() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 1 DAY";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


    /**
     * Retrieves the number of site visits within the given time interval.
     *
     * <p>The time string should be in one of the following formats:
     * <ul>
     *   <li>{@code "30m"} – last 30 minutes</li>
     *   <li>{@code "1h"} – last 1 hour</li>
     *   <li>{@code "1h30m"} – last 1 hour and 30 minutes</li>
     *   <li>{@code "2d3h15m"} – last 2 days, 3 hours, and 15 minutes</li>
     * </ul>
     * @since v.0.1
     * @param time the time interval string (e.g. "30m", "1h", "1h30m", "2d3h15m")
     * @return the number of visits within the specified time interval
     * @throws IllegalArgumentException if the time string has an invalid format
     */
    public int getVisits(String time) {
        int minutes = parseTimeToMinutes(time);

        String sql = "SELECT COUNT(*) FROM site_visits " +
                "WHERE visit_time >= NOW() - INTERVAL ? MINUTE";

        return jdbcTemplate.queryForObject(sql, Integer.class, minutes);
    }

    /**
     * Converts a time string into the total number of minutes.
     *
     * <p>Supported formats:
     * <ul>
     *   <li>{@code Xm} → X minutes</li>
     *   <li>{@code Yh} → Y hours</li>
     *   <li>{@code Zd} → Z days</li>
     *   <li>Combinations allowed, e.g. "2d3h15m" → 2 days, 3 hours, 15 minutes</li>
     * </ul>
     * @since v.0.1
     * @param time the time string in "Xm", "Yh", "Zd", or combined format
     * @return the equivalent number of minutes
     * @throws IllegalArgumentException if the time string has an invalid format
     */
    private int parseTimeToMinutes(String time) {
        int totalMinutes = 0;

        Pattern pattern = Pattern.compile("(\\d+)([dhm])");
        Matcher matcher = pattern.matcher(time);

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "d" -> totalMinutes += value * 24 * 60;
                case "h" -> totalMinutes += value * 60;
                case "m" -> totalMinutes += value;
                default -> throw new IllegalArgumentException("Invalid time unit: " + unit);
            }
        }

        if (totalMinutes == 0) {
            throw new IllegalArgumentException("Invalid time format: " + time);
        }

        return totalMinutes;
    }
}
