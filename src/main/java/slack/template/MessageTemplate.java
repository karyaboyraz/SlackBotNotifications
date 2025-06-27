package slack.template;

import slack.message.SlackMessage;

/**
 * Interface for message templates
 * Implementations should provide specific message building logic
 */
public interface MessageTemplate {
    /**
     * Builds and returns a SlackMessage based on the template
     * @return The constructed SlackMessage
     */
    SlackMessage buildMessage();
} 