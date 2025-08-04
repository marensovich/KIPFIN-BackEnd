package com.marensovich.eljur.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VisitService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void recordVisit() {
        String sql = "INSERT INTO site_visits (visit_time) VALUES (NOW())";
        jdbcTemplate.update(sql);
    }

    public int getVisitsLast10Minutes() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 10 MINUTE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    public int getVisitsLastHour() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 1 HOUR";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getVisitsLast12Hours() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 12 HOUR";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getVisitsLastDay() {
        String sql = "SELECT COUNT(*) FROM site_visits WHERE visit_time >= NOW() - INTERVAL 1 DAY";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
