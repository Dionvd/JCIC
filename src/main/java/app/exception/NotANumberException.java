package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 400 when an incorrect parameter is 
 * given by the client, that could not be parsed to an int or long.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A value couldn't be parsed as a number.")
public class NotANumberException extends Exception {

    /**
     * Empty constructor.
     */
    public NotANumberException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     * @param e
     */
    public NotANumberException(Exception e) {
        super(e.getMessage());

    }
}
