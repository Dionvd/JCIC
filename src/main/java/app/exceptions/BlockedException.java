//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

package app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 401 during a web service.
 * @author dion
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You are temporarily blocked because of too many (failed) requests. Come back in 5 minutes.") 
public class BlockedException extends ArrayIndexOutOfBoundsException {
   
    public BlockedException() {
    }
    
    public BlockedException(Exception e) {
        super(e.getMessage());
    }

}
