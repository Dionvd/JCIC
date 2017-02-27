package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 404 when an index is out of bounds.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data could be found because the index you provided is out of bounds.")
public class NotFoundOutOfBoundsException extends IndexOutOfBoundsException {

    public NotFoundOutOfBoundsException() {
    }

    public NotFoundOutOfBoundsException(Exception e) {
        super(e.getMessage());
    }

}
