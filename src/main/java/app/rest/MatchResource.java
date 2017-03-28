package app.rest;

import app.util.Validate;
import app.entity.Match;
import app.entity.MatchRules;
import app.entity.Node;
import app.entity.MatchMap;
import app.entity.Player;
import app.exception.MatchHasEndedException;
import app.exception.NotANumberException;
import app.exception.NotFoundException;
import app.exception.ParameterOutOfBoundsException;
import app.exception.NotInMatchException;
import app.dto.JsonWrapper;
import app.dto.Move;
import app.dto.MoveList;
import app.service.MatchService;
import app.service.NodeService;
import java.util.List;
import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

        long i = Validate.parseInt(matchId);
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
    public MatchMap getMatchMap(@PathVariable(value = "matchId") String matchIdString) throws NotANumberException, NotFoundException {

        long matchId = Validate.parseInt(matchIdString);
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

        long nodeId = Validate.parseInt(nodeIdString);

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

        long matchId = Validate.parseInt(matchIdString);
        int x = Validate.parseInt(xString);
        int y = Validate.parseInt(yString);

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

        long i = Validate.parseLong(matchId);
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

        long matchId = Validate.parseLong(matchIdString);
        long playerId = Validate.parseLong(playerIdString);
        return matchService.findMatchPlayer(matchId, playerId);
        
    }
    
    /**
     * Post moves as player.
     * @param matchIdString
     * @param playerIdString
     * @param moves
     * @return message of success
     * @throws NotANumberException
     * @throws NotInMatchException
     * @throws ParameterOutOfBoundsException
     * @throws NotFoundException
     * @throws MatchHasEndedException
     */
    @RequestMapping(value = "/{matchId}/players/{playerId}/moves", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public JsonWrapper postMoves(
            @PathVariable(value = "matchId") String matchIdString, 
            @PathVariable(value = "playerId") String playerIdString,
            @RequestBody List<Move> moves) throws NotANumberException, NotInMatchException, ParameterOutOfBoundsException, NotFoundException, MatchHasEndedException, MatchHasEndedException {
        
        MoveList moveList = new MoveList(moves);
        
        long matchId = Validate.parseLong(matchIdString);
        long playerId = Validate.parseLong(playerIdString);
        matchService.postMoves(matchId, playerId, moveList);
        
        return new JsonWrapper(moves.size() + " moves for player" + playerId + " where succesfully posted!");

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

        long l = Validate.parseLong(matchId);
        Match m = matchService.findOne(l);
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

        long l = Validate.parseInt(matchId);
        Match m = matchService.findOne(l);
        return m.getMatchRules();
    }
    
    /**
     * Get all matches form a Player
     *
     * @param playerId
     * @return rules of game matching matchId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/withPlayer/{playerId}", method = RequestMethod.GET)
    public Iterable<Match> getMatchesWithPlayer(@PathVariable(value = "playerId") String playerId) throws NotANumberException, NotFoundException {

        long l = Validate.parseInt(playerId);
        Iterable<Match> matches = matchService.getMatchesWithPlayer(l);
        return matches;
    }
    
}
