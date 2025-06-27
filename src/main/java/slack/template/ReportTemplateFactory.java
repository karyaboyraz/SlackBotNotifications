package slack.template;

import slack.message.SlackMessage;

/**
 * Factory class for creating various types of Slack report messages
 * Provides centralized access to all available report templates
 * Follows the Factory design pattern and Single Responsibility Principle
 */
public class ReportTemplateFactory {

    /**
     * Creates a performance report using PerformanceReportTemplate
     */
    public static SlackMessage createPerformanceReport(String serviceName, double avgResponseTime, 
                                                      int totalRequests, double errorRate, double cpuUsage, 
                                                      double memoryUsage, String reportPeriod) {
        return PerformanceReportTemplate.createReport(serviceName, avgResponseTime, totalRequests, 
                                                     errorRate, cpuUsage, memoryUsage, reportPeriod);
    }

    /**
     * Creates an error report using ErrorReportTemplate
     */
    public static SlackMessage createErrorReport(String systemName, String errorType, String errorMessage, 
                                               int errorCount, String severity, String affectedServices) {
        return ErrorReportTemplate.createReport(systemName, errorType, errorMessage, 
                                               errorCount, severity, affectedServices);
    }

    /**
     * Creates a quick error alert using ErrorReportTemplate
     */
    public static SlackMessage createQuickErrorAlert(String systemName, String errorType, String severity) {
        return ErrorReportTemplate.createQuickAlert(systemName, errorType, severity);
    }

    /**
     * Creates a deployment report using DeploymentReportTemplate
     */
    public static SlackMessage createDeploymentReport(String applicationName, String version, 
                                                    String environment, boolean deploymentSuccess, 
                                                    String deploymentDuration, String[] deployedServices,
                                                    String[] changesIncluded) {
        return DeploymentReportTemplate.createReport(applicationName, version, environment, 
                                                    deploymentSuccess, deploymentDuration, 
                                                    deployedServices, changesIncluded);
    }

    /**
     * Creates a simple deployment notification using DeploymentReportTemplate
     */
    public static SlackMessage createSimpleDeploymentNotification(String applicationName, String version, 
                                                                String environment, boolean deploymentSuccess) {
        return DeploymentReportTemplate.createSimpleNotification(applicationName, version, 
                                                                environment, deploymentSuccess);
    }

    /**
     * Creates a deployment started notification using DeploymentReportTemplate
     */
    public static SlackMessage createDeploymentStartedNotification(String applicationName, String version, 
                                                                 String environment, String estimatedDuration) {
        return DeploymentReportTemplate.createDeploymentStartedNotification(applicationName, version, 
                                                                           environment, estimatedDuration);
    }

    /**
     * Creates a rollback notification using DeploymentReportTemplate
     */
    public static SlackMessage createRollbackNotification(String applicationName, String fromVersion, 
                                                        String toVersion, String environment, 
                                                        boolean rollbackSuccess, String reason) {
        return DeploymentReportTemplate.createRollbackNotification(applicationName, fromVersion, 
                                                                  toVersion, environment, 
                                                                  rollbackSuccess, reason);
    }

    /**
     * Creates a security audit report using SecurityAuditReportTemplate
     */
    public static SlackMessage createSecurityAuditReport(int totalScans, int vulnerabilitiesFound, 
                                                        int criticalIssues, int highIssues, int mediumIssues, 
                                                        int lowIssues, String[] affectedSystems, 
                                                        String scanDuration) {
        return SecurityAuditReportTemplate.createReport(totalScans, vulnerabilitiesFound, criticalIssues, 
                                                       highIssues, mediumIssues, lowIssues, 
                                                       affectedSystems, scanDuration);
    }

    /**
     * Creates a critical vulnerability alert using SecurityAuditReportTemplate
     */
    public static SlackMessage createCriticalVulnerabilityAlert(String systemName, String vulnerabilityType, 
                                                               String cveId, String description, 
                                                               String recommendedAction) {
        return SecurityAuditReportTemplate.createCriticalVulnerabilityAlert(systemName, vulnerabilityType, 
                                                                           cveId, description, recommendedAction);
    }

    /**
     * Creates a compliance report using SecurityAuditReportTemplate
     */
    public static SlackMessage createComplianceReport(String complianceFramework, int totalControls, 
                                                     int passedControls, int failedControls, 
                                                     int notApplicableControls, double complianceScore) {
        return SecurityAuditReportTemplate.createComplianceReport(complianceFramework, totalControls, 
                                                                 passedControls, failedControls, 
                                                                 notApplicableControls, complianceScore);
    }

    /**
     * Creates a security scan started notification using SecurityAuditReportTemplate
     */
    public static SlackMessage createSecurityScanStartedNotification(String scanType, int systemCount, 
                                                                   String estimatedDuration) {
        return SecurityAuditReportTemplate.createScanStartedNotification(scanType, systemCount, estimatedDuration);
    }

    /**
     * Creates a financial report using FinancialReportTemplate
     */
    public static SlackMessage createFinancialReport(String reportPeriod, double totalRevenue, 
                                                   double totalExpenses, double profitMargin, 
                                                   double monthlyGrowth, String[] topPerformers,
                                                   String[] keyMetrics) {
        return FinancialReportTemplate.createReport(reportPeriod, totalRevenue, totalExpenses, 
                                                   profitMargin, monthlyGrowth, topPerformers, keyMetrics);
    }

    /**
     * Creates a revenue milestone notification using FinancialReportTemplate
     */
    public static SlackMessage createRevenueMilestoneNotification(String milestoneType, double amount, 
                                                                String period, double growthRate) {
        return FinancialReportTemplate.createRevenueMilestoneNotification(milestoneType, amount, period, growthRate);
    }

    /**
     * Creates a budget alert using FinancialReportTemplate
     */
    public static SlackMessage createBudgetAlert(String department, String budgetCategory, 
                                               double budgetLimit, double currentSpend, 
                                               double utilizationPercentage, String alertLevel) {
        return FinancialReportTemplate.createBudgetAlert(department, budgetCategory, budgetLimit, 
                                                        currentSpend, utilizationPercentage, alertLevel);
    }

    /**
     * Creates a quarterly financial summary using FinancialReportTemplate
     */
    public static SlackMessage createQuarterlySummary(String quarter, double revenue, double expenses, 
                                                    double profit, double previousQuarterRevenue, 
                                                    String[] achievements, String[] challenges) {
        return FinancialReportTemplate.createQuarterlySummary(quarter, revenue, expenses, profit, 
                                                            previousQuarterRevenue, achievements, challenges);
    }

    /**
     * Creates a cash flow alert using FinancialReportTemplate
     */
    public static SlackMessage createCashFlowAlert(double currentCashBalance, double projectedCashFlow, 
                                                 int daysOfCashRemaining, String alertType) {
        return FinancialReportTemplate.createCashFlowAlert(currentCashBalance, projectedCashFlow, 
                                                         daysOfCashRemaining, alertType);
    }

    /**
     * Creates a project status report using ProjectStatusReportTemplate
     */
    public static SlackMessage createProjectStatusReport(String projectName, String projectManager, 
                                                        int completionPercentage, String currentPhase,
                                                        String[] completedTasks, String[] upcomingTasks,
                                                        String[] blockers, String nextMilestone) {
        return ProjectStatusReportTemplate.createReport(projectName, projectManager, completionPercentage, 
                                                       currentPhase, completedTasks, upcomingTasks, 
                                                       blockers, nextMilestone);
    }

    /**
     * Creates a milestone notification using ProjectStatusReportTemplate
     */
    public static SlackMessage createMilestoneNotification(String projectName, String milestoneName, 
                                                         String completionDate, String[] deliverables,
                                                         String nextMilestone) {
        return ProjectStatusReportTemplate.createMilestoneNotification(projectName, milestoneName, 
                                                                      completionDate, deliverables, nextMilestone);
    }

    /**
     * Creates a project risk alert using ProjectStatusReportTemplate
     */
    public static SlackMessage createProjectRiskAlert(String projectName, String riskDescription, 
                                                     String riskLevel, String impact, String[] mitigationActions,
                                                     String owner) {
        return ProjectStatusReportTemplate.createRiskAlert(projectName, riskDescription, riskLevel, 
                                                          impact, mitigationActions, owner);
    }

    /**
     * Creates a sprint summary using ProjectStatusReportTemplate
     */
    public static SlackMessage createSprintSummary(String projectName, String sprintNumber, 
                                                 int storyPointsCompleted, int storyPointsPlanned,
                                                 int tasksCompleted, int tasksCarriedOver,
                                                 String[] sprintGoals, String[] retrospectiveItems) {
        return ProjectStatusReportTemplate.createSprintSummary(projectName, sprintNumber, storyPointsCompleted, 
                                                             storyPointsPlanned, tasksCompleted, tasksCarriedOver, 
                                                             sprintGoals, retrospectiveItems);
    }

    /**
     * Creates a resource allocation alert using ProjectStatusReportTemplate
     */
    public static SlackMessage createResourceAlert(String projectName, String resourceType, 
                                                 String currentAllocation, String requiredAllocation,
                                                 String impactDescription, String requestedBy) {
        return ProjectStatusReportTemplate.createResourceAlert(projectName, resourceType, currentAllocation, 
                                                             requiredAllocation, impactDescription, requestedBy);
    }

    /**
     * Creates a test execution report using TestReportTemplate
     */
    public static SlackMessage createTestReport(String testTags, String environment, String browser,
                                              long startTime, int totalScenarios, int passedScenarios, 
                                              int failedScenarios, java.util.List<TestReportTemplate.TestResult> testResults,
                                              String cucumberReportUrl, String allureReportUrl) {
        return TestReportTemplate.createReport(testTags, environment, browser, startTime, totalScenarios, 
                                             passedScenarios, failedScenarios, testResults, 
                                             cucumberReportUrl, allureReportUrl);
    }

    /**
     * Creates a quick test summary using TestReportTemplate
     */
    public static SlackMessage createQuickTestSummary(String testTags, String environment, 
                                                    int totalScenarios, int passedScenarios, 
                                                    int failedScenarios, String duration) {
        return TestReportTemplate.createQuickTestSummary(testTags, environment, totalScenarios, 
                                                       passedScenarios, failedScenarios, duration);
    }

    /**
     * Lists all available report types
     */
    public static String[] getAvailableReportTypes() {
        return new String[]{
            "Performance Report",
            "Error Report", 
            "Deployment Report",
            "Security Audit Report",
            "Financial Report",
            "Project Status Report",
            "Test Report",
            "Milestone Notification",
            "Risk Alert",
            "Sprint Summary",
            "Budget Alert",
            "Compliance Report",
            "Vulnerability Alert",
            "Test Failure Alert"
        };
    }

    /**
     * Gets report type description
     */
    public static String getReportTypeDescription(String reportType) {
        switch (reportType.toLowerCase()) {
            case "performance report":
                return "Tracks system performance metrics like response time, error rate, and resource usage";
            case "error report":
                return "Reports system errors, exceptions, and incidents with severity levels";
            case "deployment report":
                return "Notifies about application deployments, releases, and rollbacks";
            case "security audit report":
                return "Reports security scan results, vulnerabilities, and compliance status";
            case "financial report":
                return "Tracks financial metrics, revenue, expenses, and budget status";
            case "project status report":
                return "Updates on project progress, milestones, and team collaboration";
            case "test report":
                return "Reports automated test execution results with vertical success rates and metrics";
            case "test failure alert":
                return "Alerts for critical test failures with error details and debugging links";
            default:
                return "Report type description not available";
        }
    }
} 