package com.marensovich.eljur.service;


import com.marensovich.eljur.model.StatRecord;
import com.marensovich.eljur.repository.StatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tech service.
 */
@Service
public class TechService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StatRepository statRepository;
    @Autowired
    private VisitService visitService;

    /**
     * Collect stats.
     */
//@Scheduled(fixedRate = 600000) // 10 минут в миллисекундах
    //@Scheduled(cron = "0 * * * * * ")
    @Scheduled(cron = "0 */10 * * * *") // Каждые 10 минут
    @Transactional
    public void collectStats() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        double cpuUsage = (osBean instanceof com.sun.management.OperatingSystemMXBean)
                ? ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad() * 100
                : -1;

        long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
        int visitsLastDay = visitService.getVisitsLastDay();

        StatRecord record = new StatRecord(LocalDateTime.now(ZoneId.of("Europe/Moscow")), cpuUsage, usedMemory, visitsLastDay);
        statRepository.save(record);
    }

    /**
     * Gets stats for time range.
     *
     * @param startTime the start time
     * @return the stats for time range
     */
    public List<StatRecord> getStatsForTimeRange(LocalDateTime startTime) {
        return statRepository.findStatsSince(startTime);
    }

    /**
     * Gets database size.
     *
     * @return the database size
     */
    public String getDatabaseSize() {
        String sql = "SELECT SUM(data_length + index_length) / 1024 / 1024 AS size_mb FROM information_schema.TABLES WHERE table_schema = DATABASE()";
        Double sizeMb = jdbcTemplate.queryForObject(sql, Double.class);
        return sizeMb != null ? String.format("%.2f MB", sizeMb) : "N/A";
    }

    /**
     * Gets database version.
     *
     * @return the database version
     */
    public String getDatabaseVersion() {
        String sql = "SELECT VERSION()";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    /**
     * Gets database engine.
     *
     * @return the database engine
     */
    public String getDatabaseEngine() {
        String sql = "SELECT ENGINE FROM information_schema.tables WHERE table_schema = DATABASE() LIMIT 1";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    /**
     * Gets active connections.
     *
     * @return the active connections
     */
    public int getActiveConnections() {
        String sql = "SELECT COUNT(*) FROM information_schema.PROCESSLIST WHERE DB = DATABASE()";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Gets cpu usage.
     *
     * @return the cpu usage
     */
    public double getCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
            return sunOsBean.getSystemCpuLoad() * 100;
        }
        return -1;
    }

    /**
     * Gets cpu cores.
     *
     * @return the cpu cores
     */
    public String getCpuCores() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        return String.valueOf(osBean.getAvailableProcessors());
    }

    /**
     * Gets memory usage.
     *
     * @return the memory usage
     */
    public Map<String, Object> getMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
        long maxMemory = memoryBean.getHeapMemoryUsage().getMax();

        Map<String, Object> memoryInfo = new HashMap<>();
        memoryInfo.put("usedMemory", formatBytes(usedMemory));
        memoryInfo.put("maxMemory", formatBytes(maxMemory));
        memoryInfo.put("usagePercentage", (double) usedMemory / maxMemory * 100);

        return memoryInfo;
    }

    /**
     * Gets uptime.
     *
     * @return the uptime
     */
    public String getUptime() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeBean.getUptime();
        return formatUptime(uptime);
    }

    /**
     * Gets os name.
     *
     * @return the os name
     */
    public String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Gets os arch.
     *
     * @return the os arch
     */
    public String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Gets os version.
     *
     * @return the os version
     */
    public String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Gets available processors.
     *
     * @return the available processors
     */
    public long getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "B";
        return String.format("%.1f %s", bytes / Math.pow(1024, exp), pre);
    }

    private String formatUptime(long uptime) {
        long days = uptime / (1000 * 60 * 60 * 24);
        long hours = (uptime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (uptime % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (uptime % (1000 * 60)) / 1000;

        return String.format("%d дней, %d часов, %d минут, %d секунд", days, hours, minutes, seconds);
    }
}
