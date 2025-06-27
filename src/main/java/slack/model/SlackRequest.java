package slack.model;

/**
 * Represents a Slack API request
 * Contains common request parameters for Slack API calls
 */
public class SlackRequest {

    private String channel;
    private String text;
    private Object blocks;
    private String threadTs;
    private String username;
    private String iconEmoji;
    private String iconUrl;
    private Boolean asUser;
    private String parse;
    private Boolean linkNames;
    private Boolean unfurlLinks;
    private Boolean unfurlMedia;

    public SlackRequest() {
    }

    public SlackRequest(String channel, String text) {
        this.channel = channel;
        this.text = text;
    }

    // Getters and setters
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getBlocks() {
        return blocks;
    }

    public void setBlocks(Object blocks) {
        this.blocks = blocks;
    }

    public String getThreadTs() {
        return threadTs;
    }

    public void setThreadTs(String threadTs) {
        this.threadTs = threadTs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconEmoji() {
        return iconEmoji;
    }

    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Boolean getAsUser() {
        return asUser;
    }

    public void setAsUser(Boolean asUser) {
        this.asUser = asUser;
    }

    public String getParse() {
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    public Boolean getLinkNames() {
        return linkNames;
    }

    public void setLinkNames(Boolean linkNames) {
        this.linkNames = linkNames;
    }

    public Boolean getUnfurlLinks() {
        return unfurlLinks;
    }

    public void setUnfurlLinks(Boolean unfurlLinks) {
        this.unfurlLinks = unfurlLinks;
    }

    public Boolean getUnfurlMedia() {
        return unfurlMedia;
    }

    public void setUnfurlMedia(Boolean unfurlMedia) {
        this.unfurlMedia = unfurlMedia;
    }

    @Override
    public String toString() {
        return "SlackRequest{" +
                "channel='" + channel + '\'' +
                ", text='" + text + '\'' +
                ", threadTs='" + threadTs + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
} 