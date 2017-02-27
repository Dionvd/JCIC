package app.rest;

import app.exception.ImATeapotException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Simple RESTful web service resource for redirections. There's also something
 * else, but just ignore that.
 *
 * @author dion
 */
@ApiIgnore
@RestController
public class Redirect {

    /**
     * Redirects from home page ("/") to swagger.
     *
     * @return HTML String
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String redirect() {

        return "<html><head><meta http-equiv=\"refresh\" content=\"0; url=swagger-ui.html\" /></head><body><p><a href=\"swagger-ui.html\">Redirect</a></p></body></html>";
    }

    /**
     * Just ignore this part.
     *
     * @return String maybe, okay, probably not.
     * @throws ImATeapotException
     */
    @RequestMapping(value = "/teapot/brewcoffee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String brewcoffee() throws ImATeapotException {

        throw new ImATeapotException();
    }
}
