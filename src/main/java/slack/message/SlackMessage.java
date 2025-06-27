package slack.message;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Slack message with blocks and components
 * This class provides a clean API for building Slack messages
 */
public class SlackMessage {
    @SerializedName("channel")
    private String channel;

    @SerializedName("thread_ts")
    private String threadTs;

    @SerializedName("text")
    private String text;

    @SerializedName("blocks")
    private List<Block> blocks;

    public SlackMessage() {
        this.blocks = new ArrayList<>();
    }

    public SlackMessage(String channel) {
        this();
        this.channel = channel;
    }

    // Getters and setters
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getThreadTs() { return threadTs; }
    public void setThreadTs(String threadTs) { this.threadTs = threadTs; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<Block> getBlocks() { return blocks; }
    public void setBlocks(List<Block> blocks) { this.blocks = blocks; }

    public void addBlock(Block block) {
        if (this.blocks == null) {
            this.blocks = new ArrayList<>();
        }
        this.blocks.add(block);
    }

    public static class Block {
        @SerializedName("type")
        private String type;

        @SerializedName("text")
        private Text text;

        @SerializedName("block_id")
        private String blockId;

        @SerializedName("elements")
        private List<Element> elements;

        @SerializedName("fields")
        private List<Text> fields;

        @SerializedName("accessory")
        private Element accessory;

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Text getText() { return text; }
        public void setText(Text text) { this.text = text; }
        public String getBlockId() { return blockId; }
        public void setBlockId(String blockId) { this.blockId = blockId; }
        public List<Element> getElements() { return elements; }
        public void setElements(List<Element> elements) { this.elements = elements; }
        public List<Text> getFields() { return fields; }
        public void setFields(List<Text> fields) { this.fields = fields; }
        public Element getAccessory() { return accessory; }
        public void setAccessory(Element accessory) { this.accessory = accessory; }
    }

    public static class Text {
        @SerializedName("type")
        private String type;

        @SerializedName("text")
        private String text;

        @SerializedName("emoji")
        private Boolean emoji;

        @SerializedName("verbatim")
        private Boolean verbatim;

        public Text() {}

        public Text(String type, String text) {
            this.type = type;
            this.text = text;
        }

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public Boolean getEmoji() { return emoji; }
        public void setEmoji(Boolean emoji) { this.emoji = emoji; }
        public Boolean getVerbatim() { return verbatim; }
        public void setVerbatim(Boolean verbatim) { this.verbatim = verbatim; }
    }

    public static class Element {
        @SerializedName("type")
        private String type;

        @SerializedName("text")
        private Object text; // Can be String or Text object

        @SerializedName("action_id")
        private String actionId;

        @SerializedName("url")
        private String url;

        @SerializedName("style")
        private String style;

        @SerializedName("value")
        private String value;

        public Element() {}

        public Element(String type) {
            this.type = type;
        }

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Object getText() { return text; }
        public void setText(String text) { this.text = text; }
        public void setText(Text text) { this.text = text; }
        public String getActionId() { return actionId; }
        public void setActionId(String actionId) { this.actionId = actionId; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getStyle() { return style; }
        public void setStyle(String style) { this.style = style; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
} 