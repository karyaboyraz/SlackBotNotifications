package slack.config;

/**
 * Slack integration configuration class
 * This class holds all necessary configuration for Slack client
 */
public class SlackConfig {
    private final String botToken;
    private final String defaultChannelId;
    private final String apiUrl;
    private final int retryAttempts;
    private final long retryDelayMs;
    private final int timeoutMs;

    private SlackConfig(Builder builder) {
        this.botToken = builder.botToken;
        this.defaultChannelId = builder.defaultChannelId;
        this.apiUrl = builder.apiUrl;
        this.retryAttempts = builder.retryAttempts;
        this.retryDelayMs = builder.retryDelayMs;
        this.timeoutMs = builder.timeoutMs;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getDefaultChannelId() {
        return defaultChannelId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public long getRetryDelayMs() {
        return retryDelayMs;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String botToken;
        private String defaultChannelId;
        private String apiUrl = "https://slack.com/api/chat.postMessage";
        private int retryAttempts = 3;
        private long retryDelayMs = 1000;
        private int timeoutMs = 30000;

        public Builder botToken(String botToken) {
            this.botToken = botToken;
            return this;
        }

        public Builder defaultChannelId(String defaultChannelId) {
            this.defaultChannelId = defaultChannelId;
            return this;
        }

        public Builder apiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public Builder retryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
            return this;
        }

        public Builder retryDelayMs(long retryDelayMs) {
            this.retryDelayMs = retryDelayMs;
            return this;
        }

        public Builder timeoutMs(int timeoutMs) {
            this.timeoutMs = timeoutMs;
            return this;
        }

        public SlackConfig build() {
            if (botToken == null || botToken.trim().isEmpty()) {
                throw new IllegalArgumentException("Bot token is required");
            }
            if (defaultChannelId == null || defaultChannelId.trim().isEmpty()) {
                throw new IllegalArgumentException("Default channel ID is required");
            }
            return new SlackConfig(this);
        }
    }
} 