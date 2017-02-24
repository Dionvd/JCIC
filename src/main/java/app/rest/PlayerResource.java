package app.rest;

import app.dao.PlayerRepository;
import app.entity.Player;
import java.util.Collection;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for handling player specific data.
 * Is not responsible for logging in or handling session data.
 * @author dion
 */
//@Component
@RestController
@RequestMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerResource {

    
    @Inject
    PlayerRepository playerRepository;
    
    /**
     * Get all players.
     * @return players (all)
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public Iterable<Player> getAllPlayers() {

        return playerRepository.findAll();
    }

    /**
     * Get a specific player
     * @param playerId
     * @return player matching id
     */
    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayer(@PathVariable(value = "playerId") String playerId) {

        
        long i = ResourceMethods.parseInt(playerId);
        
        Player player = playerRepository.findOne(i);
        return player;
    }

}
