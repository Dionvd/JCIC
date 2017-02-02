package rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Simple RESTful web service resource for redirections.
 * @author dion
 */
@ApiIgnore
@RestController
public class Redirect {

    /**
     * Redirects  from home page ("/") to swagger.
     * @return HTML String
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String redirect() {

        return "<html><head><meta http-equiv=\"refresh\" content=\"0; url=swagger-ui.html\" /></head><body><p><a href=\"swagger-ui.html\">Redirect</a></p></body></html>";
    }

}
