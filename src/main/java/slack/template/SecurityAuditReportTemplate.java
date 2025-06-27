package slack.template;

import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Template for creating security audit report messages
 * Handles security scan results, vulnerability reports and compliance notifications
 */
public class SecurityAuditReportTemplate {

    /**
     * Creates a comprehensive security audit report
     * 
     * @param totalScans total number of systems scanned
     * @param vulnerabilitiesFound total vulnerabilities discovered
     * @param criticalIssues number of critical vulnerabilities
     * @param highIssues number of high severity vulnerabilities
     * @param mediumIssues number of medium severity vulnerabilities
     * @param lowIssues number of low severity vulnerabilities
     * @param affectedSystems array of systems affected by vulnerabilities
     * @param scanDuration time taken to complete the security scan
     * @return SlackMessage containing the formatted security audit report
     */
    public static SlackMessage createReport(int totalScans, int vulnerabilitiesFound, 
                                          int criticalIssues, int highIssues, int mediumIssues, 
                                          int lowIssues, String[] affectedSystems, 
                                          String scanDuration) {
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String overallRisk = determineOverallRisk(criticalIssues, highIssues, mediumIssues);
        
        return SlackMessageBuilder.create()
            .addHeader("🔒 Security Audit Report")
            .addSection("*Scan Completed:* " + timestamp + "\n*Duration:* " + scanDuration)
            .addSection("*Systems Scanned:* " + totalScans + "\n*Overall Risk Level:* " + overallRisk)
            .addDivider()
            .addSection("🛡️ *Vulnerability Summary:*")
            .addTable(
                new String[]{"Severity", "Count", "Action Required"},
                new String[][]{
                    {"🚨 Critical", String.valueOf(criticalIssues), getActionRequired(criticalIssues, "Immediate")},
                    {"⚠️ High", String.valueOf(highIssues), getActionRequired(highIssues, "Within 24h")},
                    {"🔶 Medium", String.valueOf(mediumIssues), getActionRequired(mediumIssues, "Within 1 week")},
                    {"ℹ️ Low", String.valueOf(lowIssues), getActionRequired(lowIssues, "Next cycle")}
                }
            )
            .addDivider()
            .addSection("🎯 *Affected Systems:*")
            .addSection(formatAffectedSystems(affectedSystems))
            .addDivider()
            .addSection("📋 *Recommended Actions:*")
            .addSection(generateRecommendedActions(criticalIssues, highIssues))
            .addContext("🔍 Security Team", "🛡️ Automated Scanning", "📊 Compliance Report")
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Details").url("https://security.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Download Report").url("https://reports.example.com"),
                new SlackMessageBuilder.ButtonConfig("Schedule Review").url("https://calendar.example.com")
            )
            .build();
    }

    /**
     * Creates a quick vulnerability alert for critical findings
     */
    public static SlackMessage createCriticalVulnerabilityAlert(String systemName, String vulnerabilityType, 
                                                               String cveId, String description, 
                                                               String recommendedAction) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        
        return SlackMessageBuilder.create()
            .addHeader("🚨 CRITICAL VULNERABILITY DETECTED")
            .addSection("*System:* " + systemName)
            .addSection("*Vulnerability:* " + vulnerabilityType)
            .addSection("*CVE ID:* `" + cveId + "`")
            .addSection("*Description:* " + description)
            .addSection("*Detected:* " + timestamp)
            .addDivider()
            .addSection("⚡ *IMMEDIATE ACTION REQUIRED:*")
            .addSection(recommendedAction)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("Patch Now").url("https://patch.example.com").style("danger"),
                new SlackMessageBuilder.ButtonConfig("View Details").url("https://vuln.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Create Incident").url("https://incident.example.com")
            )
            .build();
    }

    /**
     * Creates a compliance status report
     */
    public static SlackMessage createComplianceReport(String complianceFramework, int totalControls, 
                                                     int passedControls, int failedControls, 
                                                     int notApplicableControls, double complianceScore) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String complianceStatus = complianceScore >= 95 ? "✅ COMPLIANT" : 
                                 complianceScore >= 80 ? "⚠️ PARTIAL" : "🚨 NON-COMPLIANT";
        
        return SlackMessageBuilder.create()
            .addHeader("📋 " + complianceFramework + " Compliance Report")
            .addSection("*Assessment Date:* " + timestamp)
            .addSection("*Compliance Score:* " + String.format("%.1f%%", complianceScore))
            .addSection("*Status:* " + complianceStatus)
            .addDivider()
            .addSection("📊 *Control Summary:*")
            .addTable(
                new String[]{"Status", "Count", "Percentage"},
                new String[][]{
                    {"✅ Passed", String.valueOf(passedControls), 
                     String.format("%.1f%%", (passedControls * 100.0) / totalControls)},
                    {"❌ Failed", String.valueOf(failedControls), 
                     String.format("%.1f%%", (failedControls * 100.0) / totalControls)},
                    {"➖ N/A", String.valueOf(notApplicableControls), 
                     String.format("%.1f%%", (notApplicableControls * 100.0) / totalControls)}
                }
            )
            .addDivider()
            .addContext("🏛️ Compliance Team", "📋 " + complianceFramework, "📅 " + timestamp)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("Full Report").url("https://compliance.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Remediation Plan").url("https://remediation.example.com"),
                new SlackMessageBuilder.ButtonConfig("Audit Trail").url("https://audit.example.com")
            )
            .build();
    }

    /**
     * Determines overall risk level based on vulnerability counts
     */
    private static String determineOverallRisk(int criticalIssues, int highIssues, int mediumIssues) {
        if (criticalIssues > 0) return "🚨 CRITICAL";
        if (highIssues > 0) return "⚠️ HIGH";
        if (mediumIssues > 5) return "🔶 MEDIUM";
        return "✅ LOW";
    }

    /**
     * Gets action required text based on issue count
     */
    private static String getActionRequired(int issueCount, String timeframe) {
        return issueCount > 0 ? timeframe : "None";
    }

    /**
     * Formats affected systems list
     */
    private static String formatAffectedSystems(String[] affectedSystems) {
        if (affectedSystems.length == 0) {
            return "No systems affected";
        }
        return String.join("\n• ", Arrays.asList(affectedSystems));
    }

    /**
     * Generates recommended actions based on vulnerability severity
     */
    private static String generateRecommendedActions(int criticalIssues, int highIssues) {
        StringBuilder actions = new StringBuilder();
        
        if (criticalIssues > 0) {
            actions.append("• **CRITICAL**: Immediate patch deployment required\n");
            actions.append("• Isolate affected systems if necessary\n");
            actions.append("• Notify security incident response team\n");
        }
        
        if (highIssues > 0) {
            actions.append("• Review and prioritize high severity vulnerabilities\n");
            actions.append("• Schedule emergency patching within 24 hours\n");
        }
        
        actions.append("• Conduct penetration testing on affected systems\n");
        actions.append("• Update security policies and procedures\n");
        actions.append("• Schedule regular security training for team");
        
        return actions.toString();
    }

    /**
     * Creates a security scan started notification
     */
    public static SlackMessage createScanStartedNotification(String scanType, int systemCount, String estimatedDuration) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        
        return SlackMessageBuilder.create()
            .addHeader("🔍 Security Scan Started")
            .addSection("*Scan Type:* " + scanType)
            .addSection("*Systems:* " + systemCount)
            .addSection("*Started:* " + timestamp)
            .addSection("*Estimated Duration:* " + estimatedDuration)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("Monitor Progress").url("https://scan.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Cancel Scan").url("https://cancel-scan.example.com")
            )
            .build();
    }
} 