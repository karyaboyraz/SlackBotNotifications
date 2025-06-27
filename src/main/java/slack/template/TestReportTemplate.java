package slack.template;

import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Template for creating test execution report messages
 * Handles test results, vertical success rates and execution metrics
 */
public class TestReportTemplate {

    private static final String SUCCESS_BLOCK = "🟩";
    private static final String FAIL_BLOCK = "🟥";

    /**
     * Creates a comprehensive test execution report
     * 
     * @param testTags test tags that were executed
     * @param environment execution environment (staging, prod, etc.)
     * @param browser browser used for testing
     * @param startTime execution start timestamp
     * @param totalScenarios total number of test scenarios
     * @param passedScenarios number of passed scenarios
     * @param failedScenarios number of failed scenarios
     * @param testResults list of individual test results
     * @param cucumberReportUrl URL to cucumber report
     * @param allureReportUrl URL to allure report
     * @return SlackMessage containing the formatted test report
     */
    public static SlackMessage createReport(String testTags, String environment, String browser,
                                          long startTime, int totalScenarios, int passedScenarios, 
                                          int failedScenarios, List<TestResult> testResults,
                                          String cucumberReportUrl, String allureReportUrl) {
        
        String executionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        long executionTime = System.currentTimeMillis() - startTime;
        String duration = formatExecutionTime(executionTime);
        String successRate = calculateSuccessRate(totalScenarios, passedScenarios);
        
        String headerText = environment.toUpperCase() + " " + testTags + " TEST RESULTS";

        SlackMessageBuilder builder = SlackMessageBuilder.create()
            .addHeader(headerText)
            .addContext(
                "*Env:* " + environment.toUpperCase(),
                "*Browser:* " + browser,
                "*Date:* " + executionDate,
                "*Duration:* " + duration
            )
            .addDivider()
            .addSection(" *Test Results Summary:*")
            .addContext("• :bar_chart: Total Scenarios: " + totalScenarios + "\n" +
                       "• :white_check_mark: Passed: " + passedScenarios + "\n" +
                       "• :x: Failed: " + failedScenarios + "\n" +
                       "• :chart_with_upwards_trend: Success Rate: %" + successRate + "%")
            .addDivider()
            .addHeader("🏢 Verticals Success Rate Chart");

        // Generate vertical results
        Map<String, VerticalResult> verticalResultMap = new HashMap<>();
        for (TestResult tr : testResults) {
            verticalResultMap.computeIfAbsent(tr.getTestName(), v -> new VerticalResult()).addResult(tr.isPassed());
        }

        // Sort results by vertical name
        List<Map.Entry<String, VerticalResult>> sortedResults = new ArrayList<>(verticalResultMap.entrySet());
        sortedResults.sort(Comparator.comparing(Map.Entry::getKey));

        // Add vertical results
        for (Map.Entry<String, VerticalResult> entry : sortedResults) {
            String message = createVerticalResultLine(entry, entry.getKey());
            builder.addSection("`" + message + "`");
        }

        builder.addDivider();

        // Add report buttons
        if (cucumberReportUrl != null && !cucumberReportUrl.isEmpty()) {
            builder.addButtons(
                new SlackMessageBuilder.ButtonConfig("🥒 Cucumber Report").url(cucumberReportUrl).style("primary"),
                new SlackMessageBuilder.ButtonConfig("✨ Allure Report").url(allureReportUrl).style("primary")
            );
        } else {
            builder.addButtons(
                new SlackMessageBuilder.ButtonConfig("✨ Allure Report").url(allureReportUrl).style("primary")
            );
        }

        return builder.build();
    }

    /**
     * Creates a simplified test result notification
     */
    public static SlackMessage createQuickTestSummary(String testTags, String environment, 
                                                    int totalScenarios, int passedScenarios, 
                                                    int failedScenarios, String duration) {
        String successRate = calculateSuccessRate(totalScenarios, passedScenarios);
        String executionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        
        String status = failedScenarios == 0 ? "✅ ALL TESTS PASSED" : "⚠️ SOME TESTS FAILED";
        String statusIcon = failedScenarios == 0 ? "🎉" : "⚠️";
        
        return SlackMessageBuilder.create()
            .addHeader(statusIcon + " Test Execution Complete")
            .addSection("*Environment:* " + environment.toUpperCase())
            .addSection("*Tags:* " + testTags)
            .addSection("*Status:* " + status)
            .addSection("*Results:* " + passedScenarios + "/" + totalScenarios + " (" + successRate + "%)")
            .addSection("*Duration:* " + duration)
            .addSection("*Completed:* " + executionDate)
            .build();
    }

    /**
     * Calculates success rate percentage
     */
    private static String calculateSuccessRate(int total, int passed) {
        if (total == 0) return "0";
        double rate = (double) passed / total * 100;
        return String.format("%.1f", rate);
    }

    /**
     * Formats execution time from milliseconds to readable format
     */
    private static String formatExecutionTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes % 60, seconds % 60);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds % 60);
        } else {
            return String.format("%ds", seconds);
        }
    }

    /**
     * Creates a visual result line for a vertical with proper alignment
     */
    private static String createVerticalResultLine(Map.Entry<String, VerticalResult> entry, String verticalName) {
        VerticalResult vr = entry.getValue();
        int total = vr.passed + vr.failed;
        double successRate = (total == 0) ? 0.0 : (double) vr.passed / total;
        int successPercent = (int) Math.round(successRate * 100);

        StringBuilder bar = new StringBuilder();
        int passedBlocks = (int) Math.round(successRate * 10);
        bar.append(SUCCESS_BLOCK.repeat(Math.max(0, passedBlocks)));
        bar.append(FAIL_BLOCK.repeat(Math.max(0, 10 - passedBlocks)));

        // Fixed-width formatting for vertical names (10 characters)
        String alignedVerticalName = String.format("%-10s", verticalName.toUpperCase());
        
        return alignedVerticalName + " │ " + bar + " " + vr.passed + "/" + total + " (" + successPercent + "%)";
    }

    /**
     * Internal class to track vertical test results
     */
    private static class VerticalResult {
        int passed = 0;
        int failed = 0;

        void addResult(boolean isPassed) {
            if (isPassed) {
                passed++;
            } else {
                failed++;
            }
        }
    }

    /**
     * Test result data class
     */
    public static class TestResult {
        private final String testName;
        private final boolean isPassed;

        public TestResult(String testName, boolean isPassed) {
            this.testName = testName;
            this.isPassed = isPassed;
        }

        public String getTestName() {
            return testName;
        }

        public boolean isPassed() {
            return isPassed;
        }
    }
} 