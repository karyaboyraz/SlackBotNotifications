package slack.template;

import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Template for creating performance report messages
 * Handles system performance metrics and health status reporting
 */
public class PerformanceReportTemplate {

    /**
     * Creates a detailed performance report message
     * 
     * @param serviceName name of the service being monitored
     * @param avgResponseTime average response time in milliseconds
     * @param totalRequests total number of requests processed
     * @param errorRate error rate as a percentage
     * @param cpuUsage CPU usage as a percentage
     * @param memoryUsage memory usage as a percentage
     * @param reportPeriod the time period covered by this report
     * @return SlackMessage containing the formatted performance report
     */
    public static SlackMessage createReport(String serviceName, double avgResponseTime, 
                                          int totalRequests, double errorRate, double cpuUsage, 
                                          double memoryUsage, String reportPeriod) {
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String status = determineHealthStatus(errorRate);
        
        return SlackMessageBuilder.create()
            .addHeader("🚀 " + serviceName + " Performance Report")
            .addSection("*Report Period:* " + reportPeriod + "\n*Generated:* " + timestamp)
            .addDivider()
            .addSection("*Overall Status:* " + status)
            .addSection("📊 *Key Metrics:*")
            .addTable(
                new String[]{"Metric", "Value", "Status"},
                new String[][]{
                    {"Average Response Time", String.format("%.2f ms", avgResponseTime), 
                     evaluateResponseTime(avgResponseTime)},
                    {"Total Requests", String.format("%,d", totalRequests), "📈 Tracked"},
                    {"Error Rate", String.format("%.2f%%", errorRate), 
                     evaluateErrorRate(errorRate)},
                    {"CPU Usage", String.format("%.1f%%", cpuUsage), 
                     evaluateResourceUsage(cpuUsage, 70, 85)},
                    {"Memory Usage", String.format("%.1f%%", memoryUsage), 
                     evaluateResourceUsage(memoryUsage, 80, 90)}
                }
            )
            .addDivider()
            .addContext("📋 Automated Performance Monitoring", "🕐 Next Report: 1 hour")
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Dashboard").url("https://dashboard.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Download Report").url("https://reports.example.com").style("default"),
                new SlackMessageBuilder.ButtonConfig("Alert Settings").url("https://settings.example.com")
            )
            .build();
    }

    /**
     * Determines overall health status based on error rate
     */
    private static String determineHealthStatus(double errorRate) {
        if (errorRate < 5) return "✅ HEALTHY";
        if (errorRate < 15) return "⚠️ WARNING";
        return "🚨 CRITICAL";
    }

    /**
     * Evaluates response time performance
     */
    private static String evaluateResponseTime(double responseTime) {
        if (responseTime < 200) return "✅ Good";
        if (responseTime < 500) return "⚠️ Fair";
        return "🚨 Poor";
    }

    /**
     * Evaluates error rate status
     */
    private static String evaluateErrorRate(double errorRate) {
        if (errorRate < 5) return "✅ Good";
        if (errorRate < 15) return "⚠️ Warning";
        return "🚨 Critical";
    }

    /**
     * Evaluates resource usage (CPU/Memory)
     */
    private static String evaluateResourceUsage(double usage, double warningThreshold, double criticalThreshold) {
        if (usage < warningThreshold) return "✅ Normal";
        if (usage < criticalThreshold) return "⚠️ High";
        return "�� Critical";
    }
} 