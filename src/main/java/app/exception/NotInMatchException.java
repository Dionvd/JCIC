package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 400 when this request is called.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You cannot send moves because you are currently not in a match.")
public class NotInMatchException extends Exception {

    /**
     * Empty constructor.
     */
    public NotInMatchException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     * @param e
     */
    public NotInMatchException(Exception e) {
        super(e.getMessage());

    }
}
