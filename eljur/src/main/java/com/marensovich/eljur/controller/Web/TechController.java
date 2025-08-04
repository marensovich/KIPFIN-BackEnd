package com.marensovich.eljur.controller.Web;


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

@RestController
@RequestMapping("/api/tech")
public class TechController {

    @Autowired
    private TechService techService;

    @Autowired
    private VisitService visitService;
    @Autowired
    private StatRepository statRepository;

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

    @GetMapping("/cpu")
    public Map<String, Object> getCpuUsage() {
        double cpuUsage = techService.getCpuUsage();
        String cpuCores = techService.getCpuCores();
        return Map.of(
                "cpuUsage", cpuUsage + "%",
                "cpuCores", cpuCores
        );
    }

    @GetMapping("/memory")
    public Map<String, Object> getMemoryUsage() {
        return techService.getMemoryUsage();
    }

    @GetMapping("/uptime")
    public Map<String, Object> getUptime() {
        String uptime = techService.getUptime();
        return Map.of("uptime", uptime);
    }

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

    @GetMapping("/visitsLast10Minutes")
    public Map<String, Object> getVisitsLast10Minutes() {
        int visits = visitService.getVisitsLast10Minutes();
        return Map.of("visitsLast10Minutes", visits);
    }

    @GetMapping("/visitsLastHour")
    public Map<String, Object> getVisitsLastHour() {
        int visits = visitService.getVisitsLastHour();
        return Map.of("visitsLastHour", visits);
    }

    @GetMapping("/visitsLast12Hours")
    public Map<String, Object> getVisitsLast12Hours() {
        int visits = visitService.getVisitsLast12Hours();
        return Map.of("visitsLast12Hours", visits);
    }

    @GetMapping("/visitsLastDay")
    public Map<String, Object> getVisitsLastDay() {
        int visits = visitService.getVisitsLastDay();
        return Map.of("visitsLastDay", visits);
    }

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


}

