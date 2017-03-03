//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 404 when no data could be found with
 * given parameters.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data was found that matches these paremeters.")
public class NotFoundException extends Exception {

    /**
     * Empty constructor.
     */
    public NotFoundException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     * @param e
     */
    public NotFoundException(Exception e) {
        super(e.getMessage());
    }

}
