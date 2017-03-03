package app.rest;

import app.FindException;
import app.entity.Match;
import app.entity.MatchRules;
import app.entity.Node;
import app.entity.MatchMap;
import app.entity.Player;
import app.exception.NotANumberException;
import app.exception.NotFoundException;
import app.object.JsonWrapper;
import app.object.Move;
import app.service.MatchService;
import app.service.NodeService;
import java.util.ArrayList;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for handling game data. does not hold player
 * details.
 *
 * @author dion
 */
@RestController
@RequestMapping(value = "/matches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchResource {

    @Inject
    private MatchService matchService;

    @Inject
    private NodeService nodeService;

    /**
     * Get a list of all matches.
     *
     * @return ArrayList of all matches.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Match> getMatches() {
        return matchService.findAll();
    }

    /**
     * Get a specific game.
     *
     * @param matchId
     * @return game matching matchId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{matchId}", method = RequestMethod.GET)
    public Match getMatch(@PathVariable(value = "matchId") String matchId) throws NotANumberException, NotFoundException {

        long i = FindException.parseInt(matchId);
        return matchService.findOne(i);
    }

    /**
     * Get a game map.
     *
     * @param matchIdString
     * @return map of game matching matchId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{matchId}/map", method = RequestMethod.GET)
    public MatchMap getMatchMap(@PathVariable(value = "v") String matchIdString) throws NotANumberException, NotFoundException {

        long matchId = FindException.parseInt(matchIdString);
        Match m = matchService.findOne(matchId);
        return m.getMap();
    }

    /**
     * Get a single node.
     *
     * @param nodeIdString
     * @return node with matching nodeId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/nodes/{nodeId}", method = RequestMethod.GET)
    public Node getNode(@PathVariable(value = "nodeId") String nodeIdString) throws NotANumberException, NotFoundException {

        long nodeId = FindException.parseInt(nodeIdString);

        return nodeService.getNode(nodeId);
    }

    /**
     * Get a single node of a game map.
     *
     * @param matchIdString
     * @param xString coordinate of map (left to right)
     * @param yString coordinate of map (up to down)
     * @return node with matching coordinates of game matching matchId.
     * @throws NotANumberException
     * @throws NotFoundException
     *
     */
    @RequestMapping(value = "/{matchId}/map/{x}/{y}", method = RequestMethod.GET)
    public Node getMatchMapNode(
            @PathVariable(value = "matchId") String matchIdString,
            @PathVariable(value = "x") String xString,
            @PathVariable(value = "y") String yString) throws NotANumberException, NotFoundException {

        long matchId = FindException.parseInt(matchIdString);
        int x = FindException.parseInt(xString);
        int y = FindException.parseInt(yString);

        return nodeService.getNode(matchId, x, y);
    }

    /**
     * Get the game players
     *
     * @param matchId
     * @return players that play in game with matchId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{matchId}/players", method = RequestMethod.GET)
    public Iterable<Player> getMatchPlayers(@PathVariable(value = "matchId") String matchId) throws NotANumberException, NotFoundException {

        long i = FindException.parseLong(matchId);
        Match m = matchService.findOne(i);
        return m.getPlayers();
    }

    /**
     * Get a player
     *
     * @param matchIdString
     * @param playerIdString
     * @return player with matching playerId if player is inside game with
     * matchId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{matchId}/players/{playerId}", method = RequestMethod.GET)
    public Player getMatchPlayer(
            @PathVariable(value = "matchId") String matchIdString, 
            @PathVariable(value = "playerId") String playerIdString) throws NotFoundException, NotANumberException {

        long matchId = FindException.parseLong(matchIdString);
        long playerId = FindException.parseLong(playerIdString);
        return matchService.findMatchPlayer(matchId, playerId);
        
    }
    
    /**
     * Post moves as player.
     * @param matchIdString
     * @param playerIdString
     * @param moves
     * @throws NotANumberException
     */
    @RequestMapping(value = "/{matchId}/players/{playerId}/moves", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public JsonWrapper postMoves(
            @PathVariable(value = "matchId") String matchIdString, 
            @PathVariable(value = "playerId") String playerIdString,
            @RequestBody ArrayList<Move> moves) throws NotANumberException {
        
        long matchId = FindException.parseLong(matchIdString);
        long playerId = FindException.parseLong(playerIdString);
        matchService.postMoves(matchId, playerId, moves);
        
        return new JsonWrapper("Moves for " + playerId + " where succesfully posted!");
    }
    

    /**
     * Get the game turn count.
     *
     * @param matchId
     * @return turn count of game matching matchId.
     * @throws NotANumberException 
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{matchId}/turn", method = RequestMethod.GET)
    public JsonWrapper getMatchTurn(@PathVariable(value = "matchId") String matchId) throws NotANumberException, NotFoundException {

        long i = FindException.parseLong(matchId);
        Match m = matchService.findOne(i);
        return new JsonWrapper(m.getTurn());
    }

    /**
     * Get the game rules.
     *
     * @param matchId
     * @return rules of game matching matchId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{matchId}/rules", method = RequestMethod.GET)
    public MatchRules getMatchRules(@PathVariable(value = "matchId") String matchId) throws NotANumberException, NotFoundException {

        long i = FindException.parseInt(matchId);
        Match m = matchService.findOne(i);
        return m.getMatchRules();
    }
}
