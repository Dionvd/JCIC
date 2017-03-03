package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 404 when an index is out of bounds.
 * Compared to NotFoundException, this exception specifies that the index was
 * out of bounds.
 * @author dion
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data could be found because the index you provided was out of bounds.")
public class NotFoundOutOfBoundsException extends Exception {

    /**
     * Empty constructor.
     */
    public NotFoundOutOfBoundsException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     * @param e
     */
    public NotFoundOutOfBoundsException(Exception e) {
        super(e.getMessage());
    }

}
