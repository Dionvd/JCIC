package rest;

import entity.Main;
import entity.Player;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for handling player specific data.
 * Is not responsible for logging in or handling session data.
 * @author dion
 */
@RestController
public class PlayerResource {

    /**
     * Get all players.
     * @return players (all)
     */
    @RequestMapping(value = "/players", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public Collection<Player> getAllPlayers() {

        return Main.self.getPlayers();
    }

    /**
     * Get a specific player
     * @param playerId
     * @return player matching id
     */
    @RequestMapping(value = "/players/{playerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayer(@PathVariable(value = "playerId") String playerId) {

        int i = ResourceMethods.parseInt(playerId);
        return Main.self.getPlayerById(i);

    }

}
