package app.rest;

import app.entity.JsonWrapper;
import app.entity.LoginCredentials;
import app.entity.Player;
import app.entity.RegisterCredentials;
import app.exception.BlockedException;
import app.exception.FailedLoginException;
import app.exception.FailedRegisterException;
import app.service.PlayerService;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for handling player specific data. Is not
 * responsible for logging in or handling session data.
 *
 * @author dion
 */
//@Component
@RestController
@RequestMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerResource {

    @Inject
    private PlayerService playerService;

    /**
     * Get all players.
     *
     * @return players (all)
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public Iterable<Player> getAllPlayers() {

        return playerService.findAll();
    }

    /**
     * Get a specific player
     *
     * @param playerId
     * @return player matching id
     */
    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayer(@PathVariable(value = "playerId") String playerId) {

        long i = ResourceMethods.parseInt(playerId);

        Player player = playerService.findOne(i);
        return player;
    }

    /**
     * Logs in with the given credentials.
     *
     * @param credentials
     * @param sessionLeaveEmpty auto injected.
     * @return session token
     * @throws FailedLoginException on bad login.
     * @throws BlockedException on blocked email.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper login(@RequestBody LoginCredentials credentials, HttpSession sessionLeaveEmpty) throws FailedLoginException, BlockedException {

        //after 2 minutes of inactivity, the session expires...
        sessionLeaveEmpty.setMaxInactiveInterval(60 * 2);

        //check if credentials match
        Player player = playerService.getPlayerByLogin(credentials);
        player.setSessionId(sessionLeaveEmpty.getId());

        return new JsonWrapper(sessionLeaveEmpty.getId());
    }

    /**
     * Register a new player account with the given credentials. Throws an error
     * if the credentials are already in use.
     *
     * @param credentials
     * @return status message
     * @throws FailedRegisterException
     */
    @RequestMapping(value = "/login/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public JsonWrapper registerAndLogin(@RequestBody RegisterCredentials credentials) throws FailedRegisterException {

        //check if credentials are good and if so, make a new player with these credentials.
        playerService.registerWithCredentials(credentials);

        return new JsonWrapper("Registration Succesful! Welcome " + credentials.getName() + "!");
    }

}
