package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 400 when this request is called.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You cannot send moves because this round has already ended!")
public class RoundHasEndedException extends Exception {

    /**
     * Empty constructor.
     */
    public RoundHasEndedException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     * @param e
     */
    public RoundHasEndedException(Exception e) {
        super(e.getMessage());

    }
}
