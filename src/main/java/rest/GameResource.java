package rest;

import entity.*;
import java.util.ArrayList;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for handling game data.
 * does not hold player details.
 * @author dion
 */
@RestController
@RequestMapping(value = "/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class GameResource {

    /**
     * Get a list of all games.
     * @return ArrayList of all games.
     */
    @RequestMapping(value = "")
    public ArrayList<Game> getGames() {
        return Main.self.getGames();
    }

    /**
     * Get a specific game.
     * @param gameId
     * @return game matching gameId.
     */
    @RequestMapping(value = "/{gameId}")
    public Game getGame(@PathVariable(value = "gameId") int gameId) {

        Game g = Main.self.getGameById(gameId);
        return g;
    }

    /**
     * Get a game map.
     * @param gameId
     * @return map of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/map")
    public GameMap getGameMap(@PathVariable(value = "gameId") int gameId) {

        Game g = Main.self.getGameById(gameId);
        return g.getMap();
    }
    
    /**
     * Gets a row of nodes. 
     * @param gameId
     * @param y
     * @return row of nodes at coordinate y of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/map/{y}")
    public ArrayList<Node> getGameMapRow(
            @PathVariable(value = "gameId") int gameId,
            @PathVariable(value = "y") int y) {

        Game g = Main.self.getGameById(gameId);
        return g.getMap().getNodes().get(y);
    }
    
    
    /**
     * Get a single node of the game map.
     * @param gameId
     * @param y
     * @param x
     * @return node with matching coordinates of game matching gameId.
     * Y must be given first, because Y represents the row number, while X represents column.
     */
    @RequestMapping(value = "/{gameId}/map/{y}/{x}")
    public Node getGameMapNode(
            @PathVariable(value = "gameId") int gameId,
            @PathVariable(value = "y") int y,
            @PathVariable(value = "x") int x) {

        Game g = Main.self.getGameById(gameId);
        return g.getMap().getNode(x,y);
    }
    
    /**
     * Get the game turn count.
     * @param gameId
     * @return turn count of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/turn")
    public JsonWrapper getGameTurn(@PathVariable(value = "gameId") int gameId) {

        Game g = Main.self.getGameById(gameId);
        return new JsonWrapper(g.getTurn());
    }

    /**
     * Get the game rules.
     * @param gameId
     * @return rules of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/rules")
    public GameRules getGameRules(@PathVariable(value = "gameId") int gameId) {

        Game g = Main.self.getGameById(gameId);
        return g.getGameRules();
    }
}
