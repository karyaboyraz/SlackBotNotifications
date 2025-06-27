package slack.exception;

/**
 * Custom exception for Slack API related errors
 * Provides specific error handling for Slack operations
 */
public class SlackException extends Exception {

    private final String errorCode;
    private final int httpStatusCode;

    /**
     * Creates a new SlackException with a message
     */
    public SlackException(String message) {
        super(message);
        this.errorCode = null;
        this.httpStatusCode = 0;
    }

    /**
     * Creates a new SlackException with a message and cause
     */
    public SlackException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
        this.httpStatusCode = 0;
    }

    /**
     * Creates a new SlackException with detailed error information
     */
    public SlackException(String message, String errorCode, int httpStatusCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Creates a new SlackException with detailed error information and cause
     */
    public SlackException(String message, String errorCode, int httpStatusCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Returns the Slack API error code if available
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the HTTP status code if available
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Returns a detailed error message including error code and status code
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SlackException: ").append(getMessage());
        
        if (errorCode != null) {
            sb.append(" [Error Code: ").append(errorCode).append("]");
        }
        
        if (httpStatusCode > 0) {
            sb.append(" [HTTP Status: ").append(httpStatusCode).append("]");
        }
        
        return sb.toString();
    }
} 