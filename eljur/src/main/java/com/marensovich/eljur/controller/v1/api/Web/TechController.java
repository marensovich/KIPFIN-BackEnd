package com.marensovich.eljur.controller.v1.api.Web;


import com.marensovich.eljur.model.StatRecord;
import com.marensovich.eljur.repository.StatRepository;
import com.marensovich.eljur.service.TechService;
import com.marensovich.eljur.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * REST controller for system technical statistics and monitoring.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Database information (size, connections, version, engine)</li>
 *     <li>CPU usage statistics</li>
 *     <li>Memory usage statistics</li>
 *     <li>System uptime</li>
 *     <li>Operating system information</li>
 *     <li>Visits statistics (10 minutes, 1 hour, 12 hours, 24 hours)</li>
 *     <li>Graph data for CPU, memory, and visits</li>
 *     <li>Combined system statistics</li>
 * </ul>
 *
 * <p>Endpoints are primarily used in the admin panel for system monitoring.</p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/tech")
public class TechController {

    //TODO: Simplify the methods for obtaining data in N time. Create one method that accepts the "time" argument.

    @Autowired
    private TechService techService;

    @Autowired
    private VisitService visitService;
    @Autowired
    private StatRepository statRepository;

    /**
     * Returns database statistics including size, active connections, version, and engine.
     *
     * @return a map containing database information
     * @since v.0.1
     */
    @GetMapping("/database")
    public Map<String, Object> getDatabaseInfo() {
        String databaseSize = techService.getDatabaseSize();
        int activeConnections = techService.getActiveConnections();
        String dbVersion = techService.getDatabaseVersion();
        String dbEngine = techService.getDatabaseEngine();

        return Map.of(
                "databaseSize", databaseSize,
                "activeConnections", activeConnections,
                "dbVersion", dbVersion,
                "dbEngine", dbEngine
        );
    }

    /**
     * Returns current CPU usage and number of CPU cores.
     *
     * @return a map containing CPU usage statistics
     * @since v.0.1
     */
    @GetMapping("/cpu")
    public Map<String, Object> getCpuUsage() {
        double cpuUsage = techService.getCpuUsage();
        String cpuCores = techService.getCpuCores();
        return Map.of(
                "cpuUsage", cpuUsage + "%",
                "cpuCores", cpuCores
        );
    }

    /**
     * Returns memory usage statistics.
     *
     * @return a map containing memory usage details
     * @since v.0.1
     */
    @GetMapping("/memory")
    public Map<String, Object> getMemoryUsage() {
        return techService.getMemoryUsage();
    }

    /**
     * Returns system uptime in a human-readable format.
     *
     * @return a map containing system uptime
     * @since v.0.1
     */
    @GetMapping("/uptime")
    public Map<String, Object> getUptime() {
        String uptime = techService.getUptime();
        return Map.of("uptime", uptime);
    }

    /**
     * Returns operating system information including name, architecture, version, and processors.
     *
     * @return a map containing OS information
     * @since v.0.1
     */
    @GetMapping("/os")
    public Map<String, Object> getOsInfo() {
        String osName = techService.getOsName();
        String osArch = techService.getOsArch();
        String osVersion = techService.getOsVersion();
        long availableProcessors = techService.getAvailableProcessors();

        return Map.of(
                "osName", osName,
                "osArch", osArch,
                "osVersion", osVersion,
                "availableProcessors", availableProcessors
        );
    }

    /**
     * Returns the number of visits recorded in the last 10 minutes.
     *
     * @return a map containing visit statistics
     * @since v.0.1
     */
    @GetMapping("/visitsLast10Minutes")
    public Map<String, Object> getVisitsLast10Minutes() {
        int visits = visitService.getVisitsLast10Minutes();
        return Map.of("visitsLast10Minutes", visits);
    }

    /**
     * Returns the number of visits recorded in the last hour.
     *
     * @return a map containing visit statistics
     * @since v.0.1
     */
    @GetMapping("/visitsLastHour")
    public Map<String, Object> getVisitsLastHour() {
        int visits = visitService.getVisitsLastHour();
        return Map.of("visitsLastHour", visits);
    }


    /**
     * Returns the number of visits recorded in the last 12 hours.
     *
     * @return a map containing visit statistics
     * @since v.0.1
     */
    @GetMapping("/visitsLast12Hours")
    public Map<String, Object> getVisitsLast12Hours() {
        int visits = visitService.getVisitsLast12Hours();
        return Map.of("visitsLast12Hours", visits);
    }

    /**
     * Returns the number of visits recorded in the last 24 hours.
     *
     * @return a map containing visit statistics
     * @since v.0.1
     */
    @GetMapping("/visitsLastDay")
    public Map<String, Object> getVisitsLastDay() {
        int visits = visitService.getVisitsLastDay();
        return Map.of("visitsLastDay", visits);
    }

    /**
     * Returns graph data for CPU usage, memory usage, and visits for the last 24 hours.
     *
     * @param range optional time range (default 24h)
     * @return a map containing graph datasets
     * @since v.0.1
     */
    @GetMapping("/graph")
    public Map<String, Object> getGraphData(@RequestParam(required = false, defaultValue = "24h") String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minusHours(24);
        List<StatRecord> records = statRepository.findStatsSince(startTime);

        Map<Integer, List<StatRecord>> groupedByHour = records.stream()
                .collect(Collectors.groupingBy(record -> record.getTimestamp().getHour()));

        List<String> labels = new ArrayList<>();
        List<Double> avgCpuUsage = new ArrayList<>();
        List<Long> avgMemoryUsage = new ArrayList<>();
        List<Integer> avgVisits = new ArrayList<>();

        for (int i = 23; i >= 0; i--) {
            LocalDateTime hourTime = now.minusHours(i);
            int hourKey = hourTime.getHour();

            if (groupedByHour.containsKey(hourKey)) {
                List<StatRecord> hourlyRecords = groupedByHour.get(hourKey);

                double avgCpu = hourlyRecords.stream().mapToDouble(StatRecord::getCpuUsage).average().orElse(0);
                long avgMemory = (long) hourlyRecords.stream().mapToLong(StatRecord::getUsedMemory).average().orElse(0) / (1024 * 1024);
                int avgVisit = (int) hourlyRecords.stream().mapToInt(StatRecord::getVisitCount).average().orElse(0);

                labels.add(hourTime.format(DateTimeFormatter.ofPattern("HH:mm"))); // Форматируем часы: 17:00, 18:00...
                avgCpuUsage.add(Math.round(avgCpu * 10.0) / 10.0);
                avgMemoryUsage.add(avgMemory);
                avgVisits.add(avgVisit);
            }
        }

        return Map.of(
                "graphData", Map.of(
                        "labels", labels,
                        "datasets", List.of(
                                Map.of("label", "Использование памяти (MB)", "data", avgMemoryUsage),
                                Map.of("label", "Загрузка процессора (%)", "data", avgCpuUsage),
                                Map.of("label", "Посещения", "data", avgVisits)
                        )
                )
        );
    }

    /**
     * Returns combined system statistics including database, CPU, memory, uptime, OS, visits, and graph data.
     *
     * @param range optional time range (default 24h)
     * @return a map containing all system statistics
     * @since v.0.1
     */
    @GetMapping("/all")
    public Map<String, Object> getAllStats(@RequestParam(required = false, defaultValue = "24h") String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minusHours(24);
        List<StatRecord> records = statRepository.findStatsSince(startTime);

        Map<Integer, List<StatRecord>> groupedByHour = records.stream()
                .collect(Collectors.groupingBy(record -> record.getTimestamp().getHour()));

        List<String> labels = new ArrayList<>();
        List<Double> avgCpuUsage = new ArrayList<>();
        List<Long> avgMemoryUsage = new ArrayList<>();
        List<Integer> avgVisits = new ArrayList<>();

        for (int i = 23; i >= 0; i--) {
            LocalDateTime hourTime = now.minusHours(i);
            int hourKey = hourTime.getHour();

            if (groupedByHour.containsKey(hourKey)) {
                List<StatRecord> hourlyRecords = groupedByHour.get(hourKey);

                double avgCpu = hourlyRecords.stream().mapToDouble(StatRecord::getCpuUsage).average().orElse(0);
                long avgMemory = (long) hourlyRecords.stream().mapToLong(StatRecord::getUsedMemory).average().orElse(0) / (1024 * 1024);
                int avgVisit = (int) hourlyRecords.stream().mapToInt(StatRecord::getVisitCount).average().orElse(0);

                labels.add(hourTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                avgCpuUsage.add(Math.round(avgCpu * 10.0) / 10.0);
                avgMemoryUsage.add(avgMemory);
                avgVisits.add(avgVisit);
            }
        }

        return Map.of(
                "graphData", Map.of(
                        "labels", labels,
                        "datasets", List.of(
                                Map.of("label", "Использование памяти (MB)", "data", avgMemoryUsage),
                                Map.of("label", "Загрузка процессора (%)", "data", avgCpuUsage),
                                Map.of("label", "Посещения", "data", avgVisits)
                        )
                ),
                "database", Map.of(
                        "databaseSize", techService.getDatabaseSize(),
                        "activeConnections", techService.getActiveConnections(),
                        "dbVersion", techService.getDatabaseVersion(),
                        "dbEngine", techService.getDatabaseEngine()
                ),
                "cpu", Map.of(
                        "cpuUsage", techService.getCpuUsage() + "%",
                        "cpuCores", techService.getCpuCores()
                ),
                "memory", techService.getMemoryUsage(),
                "uptime", techService.getUptime(),
                "os", Map.of(
                        "osName", techService.getOsName(),
                        "osArch", techService.getOsArch(),
                        "osVersion", techService.getOsVersion(),
                        "availableProcessors", techService.getAvailableProcessors()
                ),
                "visits", Map.of(
                        "visitsLast10Minutes", visitService.getVisitsLast10Minutes(),
                        "visitsLastHour", visitService.getVisitsLastHour(),
                        "visitsLast12Hours", visitService.getVisitsLast12Hours(),
                        "visitsLastDay", visitService.getVisitsLastDay()
                )
        );
    }

    private LocalDateTime parseTimeRange(String range) {
        Pattern pattern = Pattern.compile("(\\d+)([mh])");
        Matcher matcher = pattern.matcher(range);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now;

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            if ("m".equals(unit)) {
                startTime = startTime.minusMinutes(value);
            } else if ("h".equals(unit)) {
                startTime = startTime.minusHours(value);
            }
        }

        return startTime;
    }

}

