package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that throws HTTP error 400 during a web service.
 * @author dion
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A value couldn't be parsed to an integer.") //400
public class NotANumberException extends NumberFormatException {

    public NotANumberException() {
    }
    
    public NotANumberException(String s) {
        super(s);

    }
}
