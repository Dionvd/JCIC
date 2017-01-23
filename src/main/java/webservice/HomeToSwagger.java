
package webservice;
        
import entity.Main;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dion
 */
@RestController
public class HomeToSwagger {
    
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String redirect() {
        
        if (Main.self == null)
        {    
            Main.self = new Main();
            Main.self.MockData();
        }   
        
        return "<html><head><meta http-equiv=\"refresh\" content=\"0; url=swagger-ui.html\" /></head><body><p><a href=\"swagger-ui.html\">Redirect</a></p></body></html>";
    }
    
}
