package slack.template;

import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Template for creating deployment report messages
 * Handles deployment notifications, release reports and CI/CD pipeline updates
 */
public class DeploymentReportTemplate {

    /**
     * Creates a detailed deployment report
     * 
     * @param applicationName name of the application being deployed
     * @param version version number or tag being deployed
     * @param environment target environment (development, staging, production)
     * @param deploymentSuccess whether the deployment was successful
     * @param deploymentDuration time taken for the deployment
     * @param deployedServices array of services that were deployed
     * @param changesIncluded array of changes included in this deployment
     * @return SlackMessage containing the formatted deployment report
     */
    public static SlackMessage createReport(String applicationName, String version, 
                                          String environment, boolean deploymentSuccess, 
                                          String deploymentDuration, String[] deployedServices,
                                          String[] changesIncluded) {
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String status = deploymentSuccess ? "✅ SUCCESS" : "❌ FAILED";
        String statusIcon = deploymentSuccess ? "🚀" : "💥";
        
        SlackMessageBuilder builder = SlackMessageBuilder.create()
            .addHeader(statusIcon + " Deployment Report - " + applicationName)
            .addSection("*Version:* `" + version + "`\n*Environment:* " + environment.toUpperCase())
            .addSection("*Status:* " + status + "\n*Duration:* " + deploymentDuration)
            .addSection("*Completed:* " + timestamp)
            .addDivider();

        // Add deployed services if any
        if (deployedServices.length > 0) {
            builder.addSection("📦 *Deployed Services:*");
            builder.addSection(formatListItems(deployedServices));
            builder.addDivider();
        }

        // Add changes if any
        if (changesIncluded.length > 0) {
            builder.addSection("📝 *Changes Included:*");
            builder.addSection(formatListItems(changesIncluded));
            builder.addDivider();
        }

        builder.addContext("🔄 CI/CD Pipeline", "🛠️ DevOps Team", "📅 " + timestamp);

        // Add appropriate buttons based on deployment success
        if (deploymentSuccess) {
            builder.addButtons(
                new SlackMessageBuilder.ButtonConfig("View Application").url("https://app.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Release Notes").url("https://releases.example.com"),
                new SlackMessageBuilder.ButtonConfig("Monitoring").url("https://monitoring.example.com")
            );
        } else {
            builder.addButtons(
                new SlackMessageBuilder.ButtonConfig("View Logs").url("https://deployment-logs.example.com").style("danger"),
                new SlackMessageBuilder.ButtonConfig("Rollback").url("https://rollback.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Contact DevOps").url("https://devops-support.example.com")
            );
        }

        return builder.build();
    }

    /**
     * Creates a simple deployment notification for quick updates
     */
    public static SlackMessage createSimpleNotification(String applicationName, String version, 
                                                       String environment, boolean deploymentSuccess) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String status = deploymentSuccess ? "✅ SUCCESS" : "❌ FAILED";
        String statusIcon = deploymentSuccess ? "🚀" : "💥";
        
        return SlackMessageBuilder.create()
            .addHeader(statusIcon + " Deployment " + (deploymentSuccess ? "Completed" : "Failed"))
            .addSection("*Application:* " + applicationName)
            .addSection("*Version:* `" + version + "`")
            .addSection("*Environment:* " + environment.toUpperCase())
            .addSection("*Status:* " + status)
            .addSection("*Time:* " + timestamp)
            .build();
    }

    /**
     * Creates a deployment started notification
     */
    public static SlackMessage createDeploymentStartedNotification(String applicationName, String version, 
                                                                 String environment, String estimatedDuration) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        
        return SlackMessageBuilder.create()
            .addHeader("🔄 Deployment Started - " + applicationName)
            .addSection("*Version:* `" + version + "`")
            .addSection("*Environment:* " + environment.toUpperCase())
            .addSection("*Started:* " + timestamp)
            .addSection("*Estimated Duration:* " + estimatedDuration)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Progress").url("https://deployment.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Cancel Deployment").url("https://cancel.example.com").style("danger")
            )
            .build();
    }

    /**
     * Creates a rollback notification
     */
    public static SlackMessage createRollbackNotification(String applicationName, String fromVersion, 
                                                         String toVersion, String environment, 
                                                         boolean rollbackSuccess, String reason) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String status = rollbackSuccess ? "✅ SUCCESS" : "❌ FAILED";
        String statusIcon = rollbackSuccess ? "↩️" : "⚠️";
        
        return SlackMessageBuilder.create()
            .addHeader(statusIcon + " Rollback " + (rollbackSuccess ? "Completed" : "Failed"))
            .addSection("*Application:* " + applicationName)
            .addSection("*Environment:* " + environment.toUpperCase())
            .addSection("*From Version:* `" + fromVersion + "`")
            .addSection("*To Version:* `" + toVersion + "`")
            .addSection("*Status:* " + status)
            .addSection("*Reason:* " + reason)
            .addSection("*Completed:* " + timestamp)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Application").url("https://app.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Incident Report").url("https://incident.example.com")
            )
            .build();
    }

    /**
     * Formats array items as bullet points
     */
    private static String formatListItems(String[] items) {
        StringBuilder formatted = new StringBuilder();
        for (String item : items) {
            formatted.append("• ").append(item).append("\n");
        }
        return formatted.toString().trim();
    }
} 