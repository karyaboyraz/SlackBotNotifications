package slack.template;

import slack.message.SlackMessage;
import slack.template.TestReportTemplate.TestResult;
import slack.client.SlackClient;
import slack.config.SlackConfig;
import slack.exception.SlackException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Demo class to showcase TestReportTemplate functionality
 * Demonstrates test reporting capabilities with sample data
 */
public class TestReportTemplateDemo {

    // Slack Configuration - Replace these values with your own bot token and channel ID
    private static final String BOT_TOKEN = "YOUR_SLACK_BOT_TOKEN_HERE";
    private static final String CHANNEL_ID = "YOUR_CHANNEL_ID_HERE";

    public static void main(String[] args) {
        System.out.println("🧪 Test Report Template Demo");
        System.out.println("==============================\n");

        try {
            // SlackClient yapılandırması
            SlackConfig config = SlackConfig.builder()
                .botToken(BOT_TOKEN)
                .defaultChannelId(CHANNEL_ID)
                .retryAttempts(3)
                .retryDelayMs(1000)
                .timeoutMs(30000)
                .build();

            SlackClient slackClient = new SlackClient(config);

            // Kullanıcıdan onay al
            Scanner scanner = new Scanner(System.in);
            System.out.println("⚠️  Bu demo gerçek Slack mesajları gönderecektir!");
            System.out.println("📋 Channel: " + CHANNEL_ID);
            System.out.println("🤖 Bot Token: " + BOT_TOKEN.substring(0, 20) + "...");
            
        

            demonstrateTestReports(slackClient);
            scanner.close();

        } catch (Exception e) {
            System.err.println("❌ Demo hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void demonstrateTestReports(SlackClient slackClient) {
        // Sample test results
        List<TestResult> testResults = createSampleTestResults();
        
        try {
            System.out.println("📊 Comprehensive Test Report oluşturuluyor ve gönderiliyor...");
            
            // Create comprehensive test report
            SlackMessage testReport = TestReportTemplate.createReport(
                "@smoke @regression",
                "STAGING",
                "Chrome 96",
                System.currentTimeMillis() - 450000, // 7.5 minutes ago
                25,   // total scenarios
                22,   // passed scenarios
                3,    // failed scenarios
                testResults,
                "https://cucumber.example.com/reports/latest",
                "https://allure.example.com/reports/latest"
            );
            
            boolean success = slackClient.sendMessage(testReport);
            if (success) {
                System.out.println("✅ Test report başarıyla Slack'e gönderildi!");
                System.out.println("📋 TestReportTemplate.createReport() kullanıldı");
                System.out.println("🎯 Kanal: " + CHANNEL_ID);
                System.out.println("📈 Total: 25 scenarios (22 passed, 3 failed)");
                System.out.println("🏢 Verticals: Hotel, Flight, Bus, Car, Ferry, MyAccount");
            } else {
                System.out.println("❌ Test report gönderilemedi.");
            }

        } catch (SlackException e) {
            System.err.println("❌ Slack hatası: " + e.getMessage());
        }
    }

    private static List<TestResult> createSampleTestResults() {
        List<TestResult> testResults = new ArrayList<>();
        
        // Hotel vertical tests
        testResults.add(new TestResult("HOTEL", true));
        
        // Flight vertical tests
        testResults.add(new TestResult("FLIGHT", true));
        
        // Bus vertical tests
        testResults.add(new TestResult("BUS", true));
        
        // Car vertical tests
        testResults.add(new TestResult("CAR", true));
        
        // Ferry vertical tests
        testResults.add(new TestResult("SEA", true));
        
        // MyAccount tests
        testResults.add(new TestResult("MYACCOUNT", true));
        
        return testResults;
    }
} 