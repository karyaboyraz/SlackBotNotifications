package slack.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import slack.config.SlackConfig;
import slack.exception.SlackException;
import slack.message.SlackMessage;
import slack.message.SlackMessageBuilder;
import slack.model.SlackRequest;
import slack.model.SlackResponse;
import slack.template.MessageTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Main Slack client for sending messages
 * This is the primary interface for users of the library
 */
public class SlackClient {
    private static final Logger LOGGER = Logger.getLogger(SlackClient.class.getName());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private final SlackConfig config;

    public SlackClient(SlackConfig config) {
        this.config = config;
    }

    /**
     * Sends a simple text message to the default channel
     */
    public boolean sendSimpleMessage(String text) throws SlackException {
        SlackMessage message = SlackMessageBuilder.create()
                .text(text)
                .addSection(text)
                .build();
        
        return sendMessage(message);
    }

    /**
     * Sends a simple text message to a specific channel
     */
    public boolean sendSimpleMessage(String text, String channelId) throws SlackException {
        SlackMessage message = SlackMessageBuilder.create(channelId)
                .text(text)
                .addSection(text)
                .build();
        
        return sendMessage(message, channelId);
    }

    /**
     * Sends a SlackMessage to the default channel
     */
    public boolean sendMessage(SlackMessage message) throws SlackException {
        return sendMessage(message, config.getDefaultChannelId());
    }

    /**
     * Sends a SlackMessage to a specific channel
     */
    public boolean sendMessage(SlackMessage message, String channelId) throws SlackException {
        validateInputs(message, channelId);
        
        message.setChannel(channelId);
        return sendMessageWithRetry(message, channelId);
    }

    /**
     * Sends a message using a template
     */
    public boolean sendTemplate(MessageTemplate template) throws SlackException {
        SlackMessage message = template.buildMessage();
        return sendMessage(message);
    }

    /**
     * Sends a message using a template to a specific channel
     */
    public boolean sendTemplate(MessageTemplate template, String channelId) throws SlackException {
        SlackMessage message = template.buildMessage();
        return sendMessage(message, channelId);
    }

    /**
     * Creates a new message builder
     */
    public SlackMessageBuilder createMessage() {
        return SlackMessageBuilder.create(config.getDefaultChannelId());
    }

    /**
     * Creates a new message builder for a specific channel
     */
    public SlackMessageBuilder createMessage(String channelId) {
        return SlackMessageBuilder.create(channelId);
    }

    private boolean sendMessageWithRetry(SlackMessage message, String channelId) throws SlackException {
        int attempts = 0;
        Exception lastException = null;
        
        while (attempts < config.getRetryAttempts()) {
            try {
                attempts++;
                LOGGER.info("Sending Slack message, attempt " + attempts);
                
                boolean success = performHttpRequest(message, channelId);
                if (success) {
                    LOGGER.info("Slack message sent successfully");
                    return true;
                }
                
                if (attempts < config.getRetryAttempts()) {
                    LOGGER.warning("Failed to send message, retrying in " + config.getRetryDelayMs() + "ms");
                    Thread.sleep(config.getRetryDelayMs());
                }
                
            } catch (Exception e) {
                lastException = e;
                LOGGER.warning("Error sending Slack message (attempt " + attempts + "): " + e.getMessage());
                
                if (attempts < config.getRetryAttempts()) {
                    try {
                        Thread.sleep(config.getRetryDelayMs());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new SlackException("Interrupted while retrying", ie);
                    }
                }
            }
        }
        
        throw new SlackException("Failed to send message after " + config.getRetryAttempts() + " attempts", lastException);
    }

    private boolean performHttpRequest(SlackMessage message, String channelId) throws IOException {
        URL url = new URL(config.getApiUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        try {
            // Configure connection
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Authorization", "Bearer " + config.getBotToken());
            connection.setDoOutput(true);
            connection.setConnectTimeout(config.getTimeoutMs());
            connection.setReadTimeout(config.getTimeoutMs());
            
            // Create request
            SlackRequest slackRequest = new SlackRequest();
            slackRequest.setChannel(channelId);
            slackRequest.setText("Automated Notification");
            slackRequest.setBlocks(message.getBlocks());
            String jsonPayload = GSON.toJson(slackRequest);
            
            LOGGER.info("Slack request payload: " + jsonPayload);
            
            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Read response
            int responseCode = connection.getResponseCode();
            String responseBody = readResponse(connection);
            
            LOGGER.info("Slack API Response Code: " + responseCode);
            LOGGER.info("Slack API Response: " + responseBody);
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                SlackResponse response = GSON.fromJson(responseBody, SlackResponse.class);
                if (response != null && response.isOk()) {
                    return true;
                } else {
                    LOGGER.warning("Slack API returned error: " + (response != null ? response.getError() : "Unknown error"));
                    return false;
                }
            } else {
                LOGGER.warning("HTTP error: " + responseCode + " - " + responseBody);
                return false;
            }
            
        } finally {
            connection.disconnect();
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        connection.getResponseCode() >= 400 ? 
                            connection.getErrorStream() : connection.getInputStream(),
                        StandardCharsets.UTF_8))) {
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line).append(System.lineSeparator());
            }
            return response.toString();
        }
    }

    private void validateInputs(SlackMessage message, String channelId) throws SlackException {
        if (message == null) {
            throw new SlackException("Message cannot be null");
        }
        if (channelId == null || channelId.trim().isEmpty()) {
            throw new SlackException("Channel ID cannot be null or empty");
        }
        if (config.getBotToken() == null || config.getBotToken().trim().isEmpty()) {
            throw new SlackException("Bot token is not configured");
        }
    }
} 