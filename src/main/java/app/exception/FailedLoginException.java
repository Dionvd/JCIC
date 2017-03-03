//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 401 when the given credentials to 
 * login with do not match.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Failed to login with the given credentials.")
public class FailedLoginException extends Exception {

    /**
     * Empty constructor.
     */
    public FailedLoginException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     * @param e
     */
    public FailedLoginException(Exception e) {
        super(e.getMessage());
    }

}
