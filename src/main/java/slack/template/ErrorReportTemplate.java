package slack.template;

import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Template for creating system error report messages
 * Handles error notifications, alerts and incident reporting
 */
public class ErrorReportTemplate {

    /**
     * Creates a comprehensive system error report
     * 
     * @param systemName name of the system experiencing errors
     * @param errorType type of error that occurred
     * @param errorMessage detailed error message or stack trace
     * @param errorCount number of times this error occurred
     * @param severity severity level of the error
     * @param affectedServices services affected by this error
     * @return SlackMessage containing the formatted error report
     */
    public static SlackMessage createReport(String systemName, String errorType, String errorMessage, 
                                          int errorCount, String severity, String affectedServices) {
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String severityIcon = getSeverityIcon(severity);
        
        return SlackMessageBuilder.create()
            .addHeader(severityIcon + " System Error Alert - " + systemName)
            .addSection("*Error Type:* `" + errorType + "`\n*Severity:* " + severityIcon + " " + severity)
            .addSection("*Timestamp:* " + timestamp + "\n*Error Count:* " + errorCount + " occurrences")
            .addDivider()
            .addSection("🔍 *Error Details:*")
            .addSection("```" + errorMessage + "```")
            .addSection("*Affected Services:* " + affectedServices)
            .addDivider()
            .addSection("📋 *Recommended Actions:*")
            .addSection(generateRecommendedActions(severity))
            .addDivider()
            .addContext("🔔 Alert System", "📱 Incident Management", "⏰ " + timestamp)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Logs").url("https://logs.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Create Incident").url("https://incident.example.com").style("danger"),
                new SlackMessageBuilder.ButtonConfig("System Status").url("https://status.example.com")
            )
            .build();
    }

    /**
     * Gets the appropriate icon for error severity level
     */
    private static String getSeverityIcon(String severity) {
        switch (severity.toUpperCase()) {
            case "CRITICAL": return "🚨";
            case "HIGH": return "⚠️";
            case "MEDIUM": return "🔶";
            case "LOW": return "ℹ️";
            default: return "❓";
        }
    }

    /**
     * Generates context-appropriate recommended actions based on severity
     */
    private static String generateRecommendedActions(String severity) {
        switch (severity.toUpperCase()) {
            case "CRITICAL":
                return "• **IMMEDIATE ACTION REQUIRED**\n" +
                       "• Escalate to on-call engineer\n" +
                       "• Check system logs for detailed stack trace\n" +
                       "• Consider emergency rollback\n" +
                       "• Notify stakeholders immediately";
            case "HIGH":
                return "• Check system logs for detailed stack trace\n" +
                       "• Verify service dependencies\n" +
                       "• Monitor resource utilization\n" +
                       "• Consider scaling if needed\n" +
                       "• Update incident tracking";
            case "MEDIUM":
                return "• Review error patterns and frequency\n" +
                       "• Check recent deployments\n" +
                       "• Monitor for escalation\n" +
                       "• Schedule investigation";
            default:
                return "• Log for analysis\n" +
                       "• Monitor for patterns\n" +
                       "• Include in regular maintenance";
        }
    }

    /**
     * Creates a simplified error alert for quick notifications
     */
    public static SlackMessage createQuickAlert(String systemName, String errorType, String severity) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String severityIcon = getSeverityIcon(severity);
        
        return SlackMessageBuilder.create()
            .addHeader(severityIcon + " Quick Alert - " + systemName)
            .addSection("*Error:* `" + errorType + "`")
            .addSection("*Severity:* " + severityIcon + " " + severity)
            .addSection("*Time:* " + timestamp)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Details").url("https://logs.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Acknowledge").url("https://ack.example.com").style("default")
            )
            .build();
    }
} 