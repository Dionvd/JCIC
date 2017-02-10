//https://www.leveluplunch.com/java/tutorials/014-post-json-to-spring-rest-webservice/

package rest;

import entity.*;
import exceptions.FailedLoginException;
import exceptions.FailedRegisterException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * RESTful web service resource for handling logging in.
 * 
 * @author dion
 */
@RestController
public class LoginResource {

    /**
     * Logs in with the given credentials.
     * @param credentials
     * @param sessionLeaveEmpty auto injected.
     * @return
     * @throws exceptions.FailedLoginException
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper login(@RequestBody Credentials credentials, HttpSession sessionLeaveEmpty) throws FailedLoginException {
        
        //after 2 minutes of inactivity, the session expires...
        sessionLeaveEmpty.setMaxInactiveInterval(60*2);
        
        HttpSession session;
        
        //check if credentials match
        Player player = Main.self.getPlayerByLogin(credentials);
        player.setSessionId(sessionLeaveEmpty.getId());
        
        
        return new JsonWrapper(sessionLeaveEmpty.getId());
    }

    /**
     * Register a new player account with the given credentials and logs it in.
     * @param credentials
     * @param sessionLeaveEmpty auto injected.
     * @return
     * @throws exceptions.FailedRegisterException
     */
    @RequestMapping(value = "/login/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public JsonWrapper registerAndLogin(@RequestBody Credentials credentials, HttpSession sessionLeaveEmpty) throws FailedRegisterException {
        
        //after 2 minutes of inactivity, the session expires...
        sessionLeaveEmpty.setMaxInactiveInterval(60*2);
        
        //check if credentials are good and if so, make a new player with these credentials.
        Main.self.registerWithCredentials(credentials);
        
        return new JsonWrapper(sessionLeaveEmpty.getId());
    }

    
    
}
