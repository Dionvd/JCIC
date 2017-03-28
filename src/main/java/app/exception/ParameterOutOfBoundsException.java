package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 400 when an index parameter is
 * out of bounds. Compared to NotFoundException, this exception specifies that
 * the index was out of bounds and throws a different HTTP error.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "An index parameter that you provided was out of bounds, therefor your request could not be processed.")
public class ParameterOutOfBoundsException extends Exception {

    /**
     * Empty constructor.
     */
    public ParameterOutOfBoundsException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     *
     * @param e
     */
    public ParameterOutOfBoundsException(Exception e) {
        super(e.getMessage());
    }

}
