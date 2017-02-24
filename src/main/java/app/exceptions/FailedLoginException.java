//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

package app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 401 during a web service.
 * @author dion
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Failed to login with the given credentials.") 
public class FailedLoginException extends IllegalAccessException {
   
    public FailedLoginException() {
    }
    
    public FailedLoginException(Exception e) {
        super(e.getMessage());
    }

}
