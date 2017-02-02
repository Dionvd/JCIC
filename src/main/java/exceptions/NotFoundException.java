//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No object with this Id was found.") //404
public class NotFoundException extends RuntimeException {

   
    public NotFoundException() {
    }
}
