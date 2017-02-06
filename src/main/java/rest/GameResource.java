package rest;

import entity.*;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for handling game data. does not hold player
 * details.
 *
 * @author dion
 */
@RestController
@RequestMapping(value = "/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class GameResource {

    /**
     * Get a list of all games.

     *
     * @return ArrayList of all games.
     */
    @RequestMapping(value = "")
    public Iterable<Game> getGames() {
        return Main.self.getGames();
    }

    /**
     * Get a specific game.

     *

     * @param gameId
     * @return game matching gameId.
     */
    @RequestMapping(value = "/{gameId}")
    public Game getGame(@PathVariable(value = "gameId") String gameId) {

        int i = ResourceMethods.parseInt(gameId);
        Game g = Main.self.getGameById(i);
        return g;
    }

    /**
     * Get a game map.
     *
     * @param gameId
     * @return map of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/map")
    public GameMap getGameMap(@PathVariable(value = "gameId") String gameId) {

        int i = ResourceMethods.parseInt(gameId);
        Game g = Main.self.getGameById(i);
        return g.getMap();
    }

    /**
     * Gets a row of nodes.
     *
     * @param gameId 
     * @param row of map
     * @return row of nodes at coordinate y of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/map/{row}")
    public Iterable<Node> getGameMapRow(
            @PathVariable(value = "gameId") String gameId,
            @PathVariable(value = "row") String row) {

        
        int i = ResourceMethods.parseInt(gameId);
        int iy = ResourceMethods.parseInt(row);
        
        Game g = Main.self.getGameById(i);
        return g.getMap().getNodes().get(iy);
    }

    /**
     * Get a single node of the game map.
     *
     * @param gameId
     * @param x coordinate of map
     * @param y coordinate of map
     * @return node with matching coordinates of game matching gameId. Y must be
     * given first, because Y represents the row number, while X represents
     * column.
     */
    @RequestMapping(value = "/{gameId}/map/{x}/{y}")
    public Node getGameMapNode(
            @PathVariable(value = "gameId") String gameId,
            @PathVariable(value = "x") String x,
            @PathVariable(value = "y") String y) {

        int i = ResourceMethods.parseInt(gameId);
        int ix = ResourceMethods.parseInt(x);
        int iy = ResourceMethods.parseInt(y);
        
        Game g = Main.self.getGameById(i);
        return g.getMap().getNode(ix, iy);
    }

    /**
     * Get the game turn count.
     *
     * @param gameId
     * @return turn count of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/turn")

    public JsonWrapper getGameTurn(@PathVariable(value = "gameId") String gameId) {

        int i = ResourceMethods.parseInt(gameId);
        Game g = Main.self.getGameById(i);
        return new JsonWrapper(g.getTurn());
    }

    /**
     * Get the game rules.
     *
     * @param gameId
     * @return rules of game matching gameId.
     */
    @RequestMapping(value = "/{gameId}/rules")
    public GameRules getGameRules(@PathVariable(value = "gameId") String gameId) {

        int i = ResourceMethods.parseInt(gameId);
        Game g = Main.self.getGameById(i);
        return g.getGameRules();
    }
}
