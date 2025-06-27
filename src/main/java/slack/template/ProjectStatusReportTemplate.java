package slack.template;

import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Template for creating project status report messages
 * Handles project updates, milestone tracking and team collaboration reports
 */
public class ProjectStatusReportTemplate {

    /**
     * Creates a comprehensive project status report
     * 
     * @param projectName name of the project
     * @param projectManager name of the project manager
     * @param completionPercentage completion percentage (0-100)
     * @param currentPhase current project phase
     * @param completedTasks array of completed tasks
     * @param upcomingTasks array of upcoming tasks
     * @param blockers array of current blockers
     * @param nextMilestone description of the next milestone
     * @return SlackMessage containing the formatted project status report
     */
    public static SlackMessage createReport(String projectName, String projectManager, 
                                          int completionPercentage, String currentPhase,
                                          String[] completedTasks, String[] upcomingTasks,
                                          String[] blockers, String nextMilestone) {
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String progressBar = createProgressBar(completionPercentage);
        String statusIcon = getStatusIcon(completionPercentage);
        
        SlackMessageBuilder builder = SlackMessageBuilder.create()
            .addHeader(statusIcon + " Project Status - " + projectName)
            .addSection("*Project Manager:* " + projectManager + "\n*Current Phase:* " + currentPhase)
            .addSection("*Progress:* " + progressBar + " " + completionPercentage + "%")
            .addSection("*Last Updated:* " + timestamp)
            .addDivider()
            .addSection("✅ *Completed Tasks:*")
            .addSection(formatListItems(completedTasks))
            .addDivider()
            .addSection("🔄 *Upcoming Tasks:*")
            .addSection(formatListItems(upcomingTasks))
            .addDivider();

        // Add blockers section if any exist
        if (blockers.length > 0) {
            builder.addSection("🚫 *Current Blockers:*")
                   .addSection(formatListItems(blockers))
                   .addDivider();
        }

        builder.addSection("🎯 *Next Milestone:* " + nextMilestone)
               .addContext("📋 Project Management", "👥 Team Collaboration", "📅 " + timestamp)
               .addButtons(
                   new SlackMessageBuilder.ButtonConfig("Project Board").url("https://project.example.com").style("primary"),
                   new SlackMessageBuilder.ButtonConfig("Timeline").url("https://timeline.example.com"),
                   new SlackMessageBuilder.ButtonConfig("Team Chat").url("https://chat.example.com")
               );

        return builder.build();
    }

    /**
     * Creates a milestone achievement notification
     */
    public static SlackMessage createMilestoneNotification(String projectName, String milestoneName, 
                                                         String completionDate, String[] deliverables,
                                                         String nextMilestone) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        
        SlackMessageBuilder builder = SlackMessageBuilder.create()
            .addHeader("🎉 Milestone Achieved - " + projectName)
            .addSection("*Milestone:* " + milestoneName)
            .addSection("*Completed:* " + completionDate)
            .addSection("*Reported:* " + timestamp)
            .addDivider();

        if (deliverables.length > 0) {
            builder.addSection("📦 *Deliverables Completed:*")
                   .addSection(formatListItems(deliverables))
                   .addDivider();
        }

        builder.addSection("🎯 *Next Milestone:* " + nextMilestone)
               .addButtons(
                   new SlackMessageBuilder.ButtonConfig("View Details").url("https://milestone.example.com").style("primary"),
                   new SlackMessageBuilder.ButtonConfig("Celebrate").url("https://celebrate.example.com"),
                   new SlackMessageBuilder.ButtonConfig("Next Steps").url("https://nextsteps.example.com")
               );

        return builder.build();
    }

    /**
     * Creates a project risk alert
     */
    public static SlackMessage createRiskAlert(String projectName, String riskDescription, 
                                             String riskLevel, String impact, String[] mitigationActions,
                                             String owner) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String riskIcon = getRiskIcon(riskLevel);
        
        return SlackMessageBuilder.create()
            .addHeader(riskIcon + " Project Risk Alert - " + projectName)
            .addSection("*Risk Level:* " + riskIcon + " " + riskLevel)
            .addSection("*Impact:* " + impact)
            .addSection("*Owner:* " + owner)
            .addSection("*Identified:* " + timestamp)
            .addDivider()
            .addSection("⚠️ *Risk Description:*")
            .addSection(riskDescription)
            .addDivider()
            .addSection("🛡️ *Mitigation Actions:*")
            .addSection(formatListItems(mitigationActions))
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("Risk Register").url("https://risk.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Escalate").url("https://escalate.example.com").style("danger"),
                new SlackMessageBuilder.ButtonConfig("Contact PM").url("https://pm.example.com")
            )
            .build();
    }

    /**
     * Creates a sprint summary for agile projects
     */
    public static SlackMessage createSprintSummary(String projectName, String sprintNumber, 
                                                 int storyPointsCompleted, int storyPointsPlanned,
                                                 int tasksCompleted, int tasksCarriedOver,
                                                 String[] sprintGoals, String[] retrospectiveItems) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        double completionRate = (storyPointsCompleted * 100.0) / storyPointsPlanned;
        String velocityIcon = completionRate >= 90 ? "🚀" : completionRate >= 70 ? "⚡" : "🔄";
        
        SlackMessageBuilder builder = SlackMessageBuilder.create()
            .addHeader(velocityIcon + " Sprint " + sprintNumber + " Summary - " + projectName)
            .addSection("*Sprint Completed:* " + timestamp)
            .addDivider()
            .addSection("📊 *Sprint Metrics:*")
            .addTable(
                new String[]{"Metric", "Value", "Status"},
                new String[][]{
                    {"Story Points Completed", String.valueOf(storyPointsCompleted), velocityIcon + " Tracked"},
                    {"Story Points Planned", String.valueOf(storyPointsPlanned), "🎯 Target"},
                    {"Completion Rate", String.format("%.1f%%", completionRate), 
                     completionRate >= 90 ? "✅ Excellent" : completionRate >= 70 ? "⚠️ Good" : "🔶 Needs Focus"},
                    {"Tasks Completed", String.valueOf(tasksCompleted), "✅ Done"},
                    {"Tasks Carried Over", String.valueOf(tasksCarriedOver), 
                     tasksCarriedOver == 0 ? "✅ None" : "⚠️ " + tasksCarriedOver}
                }
            )
            .addDivider();

        if (sprintGoals.length > 0) {
            builder.addSection("🎯 *Sprint Goals Achievement:*")
                   .addSection(formatListItems(sprintGoals))
                   .addDivider();
        }

        if (retrospectiveItems.length > 0) {
            builder.addSection("🔄 *Key Retrospective Items:*")
                   .addSection(formatListItems(retrospectiveItems))
                   .addDivider();
        }

        builder.addContext("🏃‍♂️ Agile Sprint", "📊 Team Velocity", "📅 Sprint Review")
               .addButtons(
                   new SlackMessageBuilder.ButtonConfig("Sprint Board").url("https://sprint.example.com").style("primary"),
                   new SlackMessageBuilder.ButtonConfig("Burndown Chart").url("https://burndown.example.com"),
                   new SlackMessageBuilder.ButtonConfig("Next Sprint").url("https://nextsprint.example.com")
               );

        return builder.build();
    }

    /**
     * Creates a resource allocation alert
     */
    public static SlackMessage createResourceAlert(String projectName, String resourceType, 
                                                 String currentAllocation, String requiredAllocation,
                                                 String impactDescription, String requestedBy) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        
        return SlackMessageBuilder.create()
            .addHeader("📋 Resource Request - " + projectName)
            .addSection("*Resource Type:* " + resourceType)
            .addSection("*Current Allocation:* " + currentAllocation)
            .addSection("*Required Allocation:* " + requiredAllocation)
            .addSection("*Requested By:* " + requestedBy)
            .addSection("*Request Time:* " + timestamp)
            .addDivider()
            .addSection("⚡ *Impact Description:*")
            .addSection(impactDescription)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("Approve Request").url("https://approve.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Resource Pool").url("https://resources.example.com"),
                new SlackMessageBuilder.ButtonConfig("Schedule Meeting").url("https://meeting.example.com")
            )
            .build();
    }

    /**
     * Creates a visual progress bar
     */
    private static String createProgressBar(int percentage) {
        int filled = Math.min(percentage / 10, 10);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        return bar.toString();
    }

    /**
     * Gets status icon based on completion percentage
     */
    private static String getStatusIcon(int completionPercentage) {
        if (completionPercentage >= 90) return "🎯";
        if (completionPercentage >= 70) return "🚀";
        if (completionPercentage >= 50) return "⚡";
        return "🔄";
    }

    /**
     * Gets risk icon based on risk level
     */
    private static String getRiskIcon(String riskLevel) {
        switch (riskLevel.toUpperCase()) {
            case "CRITICAL": return "🚨";
            case "HIGH": return "⚠️";
            case "MEDIUM": return "🔶";
            case "LOW": return "ℹ️";
            default: return "❓";
        }
    }

    /**
     * Formats array items as bullet points
     */
    private static String formatListItems(String[] items) {
        if (items.length == 0) return "No items to display";
        return String.join("\n• ", Arrays.asList(items));
    }
} 