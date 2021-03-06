//https://en.wikipedia.org/wiki/Hyper_Text_Coffee_Pot_Control_Protocol
package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ImATeapotException will be thrown when you ask a teapot to brew you some
 * coffee. That's obviously not what teapots are for!
 *
 * @author dion
 */
@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "I am a teapot. Teapots cannot brew coffee.")
public class ImATeapotException extends Exception {

    /**
     *  Empty constructor.
     */
    public ImATeapotException() {
    }

}
