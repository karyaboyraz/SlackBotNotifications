package slack.model;

/**
 * Represents a Slack API response
 * Contains common response fields from Slack API calls
 */
public class SlackResponse {

    private boolean ok;
    private String error;
    private String warning;
    private String ts;
    private String channel;
    private Object message;
    private String responseMetadata;

    public SlackResponse() {
    }

    public SlackResponse(boolean ok) {
        this.ok = ok;
    }

    public SlackResponse(boolean ok, String error) {
        this.ok = ok;
        this.error = error;
    }

    // Getters and setters
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getResponseMetadata() {
        return responseMetadata;
    }

    public void setResponseMetadata(String responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

    /**
     * Checks if the response indicates success
     */
    public boolean isSuccessful() {
        return ok && error == null;
    }

    /**
     * Gets error message if any
     */
    public String getErrorMessage() {
        return error != null ? error : "Unknown error";
    }

    @Override
    public String toString() {
        return "SlackResponse{" +
                "ok=" + ok +
                ", error='" + error + '\'' +
                ", warning='" + warning + '\'' +
                ", ts='" + ts + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
} 