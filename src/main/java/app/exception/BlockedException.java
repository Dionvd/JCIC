//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 401 when a user is temporarily 
 * blocked because of too many failed verification requests.
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You are temporarily blocked because of too many (failed) verification requests. Come back in 5 minutes.")
public class BlockedException extends Exception {

    /**
     * Empty constructor.
     */
    public BlockedException() {
    }

    /**
     * Information passing constructor. When possible use this constructor.
     * @param e
     */
    public BlockedException(Exception e) {
        super(e.getMessage());
    }

}
