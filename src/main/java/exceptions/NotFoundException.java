//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 404 during a web service.
 * @author dion
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data was found that matches these paremeters.") 
public class NotFoundException extends ArrayIndexOutOfBoundsException {


   
    public NotFoundException() {
    }
    
    public NotFoundException(Exception e) {
        super(e.getMessage());
    }

}
