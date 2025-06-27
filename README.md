# Slack Notification Library

Slack kanallarına bildirim göndermek için Java kütüphanesi. Çeşitli rapor şablonları ile test sonuçları, deploy durumları ve sistem bilgileri paylaşabilirsiniz.

## 🚀 Özellikler

- **Test Report Templates**: Test sonuçlarını detaylı raporlama
- **Deployment Report Templates**: Deploy durumu bildirimleri
- **Error Report Templates**: Hata ve exception raporlama
- **Performance Report Templates**: Performans metrikleri
- **Project Status Templates**: Proje durum güncellemeleri
- **Financial Report Templates**: Mali rapor bildirimleri
- **Security Audit Templates**: Güvenlik denetim raporları
- **Kolay Kullanım**: Fluent API ile basit konfigürasyon
- **Template Factory**: Dinamik template seçimi

## 📦 Kurulum

### JitPack (Önerilen)

#### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.karyaboyraz</groupId>
    <artifactId>SlackBotNotifications</artifactId>
    <version>v1.0.0</version>
</dependency>
```

#### Gradle
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.karyaboyraz:SlackBotNotifications:v1.0.0'
}
```

### GitHub Packages

#### Maven
```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/karyaboyraz/SlackBotNotifications</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.karyaboyraz</groupId>
    <artifactId>slack-notification-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Not**: GitHub Packages için authentication gereklidir. `~/.m2/settings.xml` dosyasında GitHub token konfigürasyonu yapılmalıdır.

### Maven Central (Yakında)

```xml
<dependency>
    <groupId>com.github.karyaboyraz</groupId>
    <artifactId>slack-notification-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 🔧 Kullanım

### Temel Konfigürasyon

```java
import slack.client.SlackClient;
import slack.config.SlackConfig;

// Slack Client oluştur
SlackConfig config = SlackConfig.builder()
    .botToken("xoxb-your-bot-token")
    .defaultChannelId("C1234567890")
    .retryAttempts(3)
    .retryDelayMs(1000)
    .timeoutMs(30000)
    .build();

SlackClient client = new SlackClient(config);
```

### Test Raporu Gönderme

```java
import slack.template.TestReportTemplate;
import slack.template.TestReportTemplate.TestResult;

// Test sonuçlarını hazırla
List<TestResult> testResults = Arrays.asList(
    new TestResult("HOTEL", true),
    new TestResult("FLIGHT", true),
    new TestResult("CAR", false)
);

// Test raporu oluştur
SlackMessage testReport = TestReportTemplate.createReport(
    "@smoke @regression",           // Test tags
    "STAGING",                      // Environment
    "Chrome 96",                    // Browser
    System.currentTimeMillis() - 450000, // Start time
    25,                             // Total scenarios
    22,                             // Passed scenarios
    3,                              // Failed scenarios
    testResults,                    // Detailed results
    "https://cucumber.example.com/reports/latest",
    "https://allure.example.com/reports/latest"
);

// Raporu gönder
client.sendMessage(testReport);
```

### Deployment Raporu

```java
import slack.template.DeploymentReportTemplate;

SlackMessage deployReport = DeploymentReportTemplate.createReport(
    "Production",                   // Environment
    "v2.1.0",                      // Version
    "SUCCESS",                     // Status
    System.currentTimeMillis() - 300000, // Start time
    300000,                        // Duration in ms
    "https://github.com/user/repo/releases/tag/v2.1.0"
);

client.sendMessage(deployReport);
```

### Error Raporu

```java
import slack.template.ErrorReportTemplate;

SlackMessage errorReport = ErrorReportTemplate.createReport(
    "Payment Service",             // Service name
    "NullPointerException",        // Error type
    "User ID cannot be null",      // Error message
    "CRITICAL",                    // Severity
    "com.example.PaymentService:42", // Stack trace
    "https://monitoring.example.com/error/12345"
);

client.sendMessage(errorReport);
```

### Performance Raporu

```java
import slack.template.PerformanceReportTemplate;

SlackMessage perfReport = PerformanceReportTemplate.createReport(
    "API Performance Test",        // Test name
    "Production API",              // Target system
    1250.5,                        // Average response time
    850.0,                         // P95 response time
    99.8,                          // Success rate
    "https://grafana.example.com/dashboard/api-performance"
);

client.sendMessage(perfReport);
```

### Template Factory Kullanımı

```java
import slack.template.ReportTemplateFactory;

// Template type'a göre dinamik template seçimi
SlackMessage report = ReportTemplateFactory.createReport(
    "TEST",                        // Template type
    Map.of(
        "tags", "@smoke",
        "environment", "staging",
        "totalTests", 50,
        "passedTests", 45,
        "failedTests", 5
    )
);

client.sendMessage(report);
```

## 📋 Template Türleri

| Template | Açıklama | Kullanım Alanı |
|----------|----------|----------------|
| `TestReportTemplate` | Test sonuçları ve metrikleri | CI/CD, QA süreçleri |
| `DeploymentReportTemplate` | Deploy durumu ve detayları | DevOps, Release yönetimi |
| `ErrorReportTemplate` | Hata ve exception bildirimi | Monitoring, Alerting |
| `PerformanceReportTemplate` | Performans metrikleri | Load testing, Monitoring |
| `ProjectStatusReportTemplate` | Proje durumu güncellemeleri | Proje yönetimi |
| `FinancialReportTemplate` | Mali rapor bildirimleri | Finans, Accounting |
| `SecurityAuditReportTemplate` | Güvenlik denetim sonuçları | Security, Compliance |

## 🔒 Slack Bot Kurulumu

1. [Slack API](https://api.slack.com/apps) sayfasında yeni bir app oluşturun
2. **Bot Token Scopes** kısmına şu izinleri ekleyin:
   - `chat:write`
   - `chat:write.public`
3. Bot Token'ı (`xoxb-...`) kopyalayın
4. Bot'u ilgili kanala davet edin

## 🤝 Katkıda Bulunma

1. Repository'yi fork edin
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add amazing feature'`)
4. Branch'i push edin (`git push origin feature/amazing-feature`)
5. Pull Request açın

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.

## 🔗 Linkler

- [GitHub Repository](https://github.com/karyaboyraz/SlackBotNotifications)
- [JitPack](https://jitpack.io/#karyaboyraz/SlackBotNotifications)
- [Issues](https://github.com/karyaboyraz/SlackBotNotifications/issues)
- [Slack API Dokümantasyonu](https://api.slack.com/) 