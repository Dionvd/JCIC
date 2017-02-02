package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A value couldn't be translated to an integer.") //400
public class NotANumberException extends RuntimeException {

    
    public NotANumberException() {
    }
}
