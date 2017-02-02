package rest;

import entity.Main;
import entity.Player;
import java.util.ArrayList;
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
    public ArrayList<Player> getAllPlayers() {
        return Main.self.getPlayers();
    }

    /**
     * Get a specific player
     * @param id
     * @return player matching id
     */
    @RequestMapping(value = "/players/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPlayer(@PathVariable(value = "id") int id) {

        Player p = Main.self.getPlayerById(id);

        if (p != null) {
            return ResponseEntity.status(HttpStatus.OK).body(p);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error Message");
        }

    }

}
