package slack.template;

import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Template for creating financial report messages
 * Handles financial performance, revenue metrics and business intelligence reports
 */
public class FinancialReportTemplate {

    /**
     * Creates a comprehensive financial performance report
     * 
     * @param reportPeriod time period covered by the report
     * @param totalRevenue total revenue for the period
     * @param totalExpenses total expenses for the period
     * @param profitMargin profit margin as a percentage
     * @param monthlyGrowth growth rate as a percentage
     * @param topPerformers array of top performing metrics or categories
     * @param keyMetrics array of key business metrics
     * @return SlackMessage containing the formatted financial report
     */
    public static SlackMessage createReport(String reportPeriod, double totalRevenue, 
                                          double totalExpenses, double profitMargin, 
                                          double monthlyGrowth, String[] topPerformers,
                                          String[] keyMetrics) {
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String growthIcon = monthlyGrowth > 0 ? "📈" : "📉";
        String profitStatus = evaluateProfitStatus(profitMargin);
        double netProfit = totalRevenue - totalExpenses;
        
        return SlackMessageBuilder.create()
            .addHeader("💼 Financial Performance Report")
            .addSection("*Report Period:* " + reportPeriod + "\n*Generated:* " + timestamp)
            .addDivider()
            .addSection("💰 *Financial Overview:*")
            .addTable(
                new String[]{"Metric", "Amount", "Status"},
                new String[][]{
                    {"Total Revenue", formatCurrency(totalRevenue), "💵 Tracked"},
                    {"Total Expenses", formatCurrency(totalExpenses), "💸 Monitored"},
                    {"Net Profit", formatCurrency(netProfit), profitStatus},
                    {"Profit Margin", String.format("%.1f%%", profitMargin), profitStatus},
                    {"Monthly Growth", String.format("%.1f%%", monthlyGrowth), 
                     growthIcon + " " + (monthlyGrowth > 0 ? "Positive" : "Negative")}
                }
            )
            .addDivider()
            .addSection("🏆 *Top Performers:*")
            .addSection(formatListItems(topPerformers))
            .addDivider()
            .addSection("📊 *Key Metrics:*")
            .addSection(formatListItems(keyMetrics))
            .addContext("💼 Finance Team", "📈 Business Intelligence", "💹 Market Analysis")
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("Full Dashboard").url("https://finance.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Export Data").url("https://export.example.com"),
                new SlackMessageBuilder.ButtonConfig("Schedule Meeting").url("https://meeting.example.com")
            )
            .build();
    }

    /**
     * Creates a revenue milestone notification
     */
    public static SlackMessage createRevenueMilestoneNotification(String milestoneType, double amount, 
                                                                String period, double growthRate) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        
        return SlackMessageBuilder.create()
            .addHeader("🎉 Revenue Milestone Achieved!")
            .addSection("*Milestone:* " + milestoneType)
            .addSection("*Amount:* " + formatCurrency(amount))
            .addSection("*Period:* " + period)
            .addSection("*Growth Rate:* " + String.format("%.1f%%", growthRate))
            .addSection("*Achieved:* " + timestamp)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Details").url("https://revenue.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Share News").url("https://share.example.com")
            )
            .build();
    }

    /**
     * Creates a budget alert notification
     */
    public static SlackMessage createBudgetAlert(String department, String budgetCategory, 
                                               double budgetLimit, double currentSpend, 
                                               double utilizationPercentage, String alertLevel) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String alertIcon = getAlertIcon(alertLevel);
        double remainingBudget = budgetLimit - currentSpend;
        
        return SlackMessageBuilder.create()
            .addHeader(alertIcon + " Budget Alert - " + department)
            .addSection("*Category:* " + budgetCategory)
            .addSection("*Alert Level:* " + alertIcon + " " + alertLevel)
            .addDivider()
            .addSection("💰 *Budget Status:*")
            .addTable(
                new String[]{"Metric", "Amount", "Status"},
                new String[][]{
                    {"Budget Limit", formatCurrency(budgetLimit), "🎯 Target"},
                    {"Current Spend", formatCurrency(currentSpend), "💸 Used"},
                    {"Remaining", formatCurrency(remainingBudget), 
                     remainingBudget > 0 ? "✅ Available" : "🚨 Exceeded"},
                    {"Utilization", String.format("%.1f%%", utilizationPercentage), 
                     evaluateUtilization(utilizationPercentage)}
                }
            )
            .addSection("*Alert Time:* " + timestamp)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("Review Budget").url("https://budget.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Request Approval").url("https://approval.example.com"),
                new SlackMessageBuilder.ButtonConfig("Contact Finance").url("https://finance-team.example.com")
            )
            .build();
    }

    /**
     * Creates a quarterly financial summary
     */
    public static SlackMessage createQuarterlySummary(String quarter, double revenue, double expenses, 
                                                    double profit, double previousQuarterRevenue, 
                                                    String[] achievements, String[] challenges) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        double quarterOverQuarterGrowth = ((revenue - previousQuarterRevenue) / previousQuarterRevenue) * 100;
        String growthIcon = quarterOverQuarterGrowth > 0 ? "📈" : "📉";
        
        SlackMessageBuilder builder = SlackMessageBuilder.create()
            .addHeader("📊 " + quarter + " Financial Summary")
            .addSection("*Report Generated:* " + timestamp)
            .addDivider()
            .addSection("💰 *Quarter Highlights:*")
            .addTable(
                new String[]{"Metric", "Amount", "QoQ Change"},
                new String[][]{
                    {"Revenue", formatCurrency(revenue), 
                     growthIcon + " " + String.format("%.1f%%", quarterOverQuarterGrowth)},
                    {"Expenses", formatCurrency(expenses), "💸 Managed"},
                    {"Net Profit", formatCurrency(profit), evaluateProfitStatus((profit/revenue)*100)}
                }
            )
            .addDivider();

        if (achievements.length > 0) {
            builder.addSection("🏆 *Key Achievements:*")
                   .addSection(formatListItems(achievements))
                   .addDivider();
        }

        if (challenges.length > 0) {
            builder.addSection("⚠️ *Challenges & Focus Areas:*")
                   .addSection(formatListItems(challenges))
                   .addDivider();
        }

        builder.addContext("📈 Quarterly Review", "💼 Executive Summary", "📅 " + quarter)
               .addButtons(
                   new SlackMessageBuilder.ButtonConfig("Full Report").url("https://quarterly.example.com").style("primary"),
                   new SlackMessageBuilder.ButtonConfig("Board Presentation").url("https://board.example.com"),
                   new SlackMessageBuilder.ButtonConfig("Next Quarter Plan").url("https://planning.example.com")
               );

        return builder.build();
    }

    /**
     * Creates a cash flow alert
     */
    public static SlackMessage createCashFlowAlert(double currentCashBalance, double projectedCashFlow, 
                                                 int daysOfCashRemaining, String alertType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String alertIcon = getAlertIcon(alertType);
        
        return SlackMessageBuilder.create()
            .addHeader(alertIcon + " Cash Flow Alert")
            .addSection("*Alert Type:* " + alertIcon + " " + alertType)
            .addSection("*Current Balance:* " + formatCurrency(currentCashBalance))
            .addSection("*Projected Cash Flow:* " + formatCurrency(projectedCashFlow))
            .addSection("*Days of Cash Remaining:* " + daysOfCashRemaining + " days")
            .addSection("*Alert Time:* " + timestamp)
            .addButtons(
                new SlackMessageBuilder.ButtonConfig("View Cash Flow").url("https://cashflow.example.com").style("primary"),
                new SlackMessageBuilder.ButtonConfig("Emergency Plan").url("https://emergency.example.com").style("danger"),
                new SlackMessageBuilder.ButtonConfig("Contact CFO").url("https://cfo.example.com")
            )
            .build();
    }

    /**
     * Formats currency values
     */
    private static String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }

    /**
     * Evaluates profit status based on margin
     */
    private static String evaluateProfitStatus(double profitMargin) {
        if (profitMargin > 20) return "💰 Excellent";
        if (profitMargin > 10) return "✅ Good";
        if (profitMargin > 0) return "⚠️ Moderate";
        return "🚨 Loss";
    }

    /**
     * Gets alert icon based on alert level
     */
    private static String getAlertIcon(String alertLevel) {
        switch (alertLevel.toUpperCase()) {
            case "CRITICAL": return "🚨";
            case "HIGH": return "⚠️";
            case "MEDIUM": return "🔶";
            case "LOW": return "ℹ️";
            default: return "📊";
        }
    }

    /**
     * Evaluates budget utilization
     */
    private static String evaluateUtilization(double utilizationPercentage) {
        if (utilizationPercentage > 100) return "🚨 Over Budget";
        if (utilizationPercentage > 90) return "⚠️ High";
        if (utilizationPercentage > 75) return "🔶 Moderate";
        return "✅ Good";
    }

    /**
     * Formats array items as bullet points
     */
    private static String formatListItems(String[] items) {
        if (items.length == 0) return "No items to display";
        return String.join("\n• ", Arrays.asList(items));
    }
} 