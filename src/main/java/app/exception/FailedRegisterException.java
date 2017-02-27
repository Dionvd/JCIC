//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 400 during a web service.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to register with the given credentials, name or email is already in use.")
public class FailedRegisterException extends IllegalAccessException {

    public FailedRegisterException() {
    }

    public FailedRegisterException(Exception e) {
        super(e.getMessage());
    }

}
