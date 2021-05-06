package clark.data;

/**
 *
 * @author William Clark
 */
public class JobDataException extends Exception {

    /**
     * 
     */
    public JobDataException() {
    }

    /**
     *
     * @param message
     */
    public JobDataException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public JobDataException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public JobDataException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public JobDataException(String message, Throwable cause
            , boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
