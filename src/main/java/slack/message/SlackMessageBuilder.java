package slack.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for creating Slack messages with fluent API
 * Provides easy-to-use methods for constructing complex Slack messages
 */
public class SlackMessageBuilder {
    private final SlackMessage message;

    private SlackMessageBuilder() {
        this.message = new SlackMessage();
    }

    private SlackMessageBuilder(String channel) {
        this.message = new SlackMessage(channel);
    }

    public static SlackMessageBuilder create() {
        return new SlackMessageBuilder();
    }

    public static SlackMessageBuilder create(String channel) {
        return new SlackMessageBuilder(channel);
    }

    public SlackMessageBuilder channel(String channel) {
        message.setChannel(channel);
        return this;
    }

    public SlackMessageBuilder text(String text) {
        message.setText(text);
        return this;
    }

    public SlackMessageBuilder threadTs(String threadTs) {
        message.setThreadTs(threadTs);
        return this;
    }

    /**
     * Adds a header block to the message
     */
    public SlackMessageBuilder addHeader(String text) {
        SlackMessage.Block header = new SlackMessage.Block();
        header.setType("header");
        
        SlackMessage.Text textObj = new SlackMessage.Text("plain_text", text);
        textObj.setEmoji(true);
        header.setText(textObj);
        
        message.addBlock(header);
        return this;
    }

    /**
     * Adds a section block with markdown text
     */
    public SlackMessageBuilder addSection(String text) {
        SlackMessage.Block section = new SlackMessage.Block();
        section.setType("section");
        section.setText(new SlackMessage.Text("mrkdwn", text));
        
        message.addBlock(section);
        return this;
    }

    /**
     * Adds a plain text section
     */
    public SlackMessageBuilder addPlainSection(String text) {
        SlackMessage.Block section = new SlackMessage.Block();
        section.setType("section");
        section.setText(new SlackMessage.Text("plain_text", text));
        
        message.addBlock(section);
        return this;
    }

    /**
     * Adds a context block with multiple elements
     */
    public SlackMessageBuilder addContext(String... texts) {
        SlackMessage.Block context = new SlackMessage.Block();
        context.setType("context");
        
        List<SlackMessage.Element> elements = new ArrayList<>();
        for (String text : texts) {
            SlackMessage.Element element = new SlackMessage.Element("mrkdwn");
            element.setText(text);
            elements.add(element);
        }
        context.setElements(elements);
        
        message.addBlock(context);
        return this;
    }

    /**
     * Adds a divider block
     */
    public SlackMessageBuilder addDivider() {
        SlackMessage.Block divider = new SlackMessage.Block();
        divider.setType("divider");
        message.addBlock(divider);
        return this;
    }

    /**
     * Adds a button with URL
     */
    public SlackMessageBuilder addButton(String text, String url, String style) {
        SlackMessage.Block actions = new SlackMessage.Block();
        actions.setType("actions");
        
        List<SlackMessage.Element> elements = new ArrayList<>();
        SlackMessage.Element button = new SlackMessage.Element("button");
        button.setText(new SlackMessage.Text("plain_text", text));
        button.setUrl(url);
        if (style != null) {
            button.setStyle(style);
        }
        elements.add(button);
        
        actions.setElements(elements);
        message.addBlock(actions);
        return this;
    }

    /**
     * Adds multiple buttons in an actions block
     */
    public SlackMessageBuilder addButtons(ButtonConfig... buttons) {
        SlackMessage.Block actions = new SlackMessage.Block();
        actions.setType("actions");
        
        List<SlackMessage.Element> elements = new ArrayList<>();
        for (ButtonConfig buttonConfig : buttons) {
            SlackMessage.Element button = new SlackMessage.Element("button");
            button.setText(new SlackMessage.Text("plain_text", buttonConfig.text));
            if (buttonConfig.url != null) {
                button.setUrl(buttonConfig.url);
            }
            if (buttonConfig.actionId != null) {
                button.setActionId(buttonConfig.actionId);
            }
            if (buttonConfig.style != null) {
                button.setStyle(buttonConfig.style);
            }
            if (buttonConfig.value != null) {
                button.setValue(buttonConfig.value);
            }
            elements.add(button);
        }
        
        actions.setElements(elements);
        message.addBlock(actions);
        return this;
    }

    /**
     * Adds a table with headers and rows
     */
    public SlackMessageBuilder addTable(String[] headers, String[][] rows) {
        // Add headers
        SlackMessage.Block headerBlock = new SlackMessage.Block();
        headerBlock.setType("section");
        
        List<SlackMessage.Text> headerFields = new ArrayList<>();
        for (String header : headers) {
            headerFields.add(new SlackMessage.Text("mrkdwn", "*" + header + "*"));
        }
        headerBlock.setFields(headerFields);
        message.addBlock(headerBlock);
        
        addDivider();
        
        // Add rows
        for (String[] row : rows) {
            SlackMessage.Block rowBlock = new SlackMessage.Block();
            rowBlock.setType("section");
            
            List<SlackMessage.Text> rowFields = new ArrayList<>();
            for (String cell : row) {
                rowFields.add(new SlackMessage.Text("mrkdwn", cell));
            }
            rowBlock.setFields(rowFields);
            message.addBlock(rowBlock);
            addDivider();
        }
        
        return this;
    }

    /**
     * Adds a custom block
     */
    public SlackMessageBuilder addCustomBlock(SlackMessage.Block block) {
        message.addBlock(block);
        return this;
    }

    /**
     * Builds and returns the SlackMessage
     */
    public SlackMessage build() {
        return message;
    }

    /**
     * Configuration class for buttons
     */
    public static class ButtonConfig {
        private String text;
        private String url;
        private String actionId;
        private String style;
        private String value;

        public ButtonConfig(String text) {
            this.text = text;
        }

        public ButtonConfig url(String url) {
            this.url = url;
            return this;
        }

        public ButtonConfig actionId(String actionId) {
            this.actionId = actionId;
            return this;
        }

        public ButtonConfig style(String style) {
            this.style = style;
            return this;
        }

        public ButtonConfig value(String value) {
            this.value = value;
            return this;
        }
    }
} 