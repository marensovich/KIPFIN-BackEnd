package com.marensovich.eljur.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
}
