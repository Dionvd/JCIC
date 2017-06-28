package app.rest;

import app.util.Validate;
import app.entity.Round;
import app.entity.RoundRules;
import app.entity.Node;
import app.entity.RoundMap;
import app.entity.Player;
import app.exception.RoundHasEndedException;
import app.exception.NotANumberException;
import app.exception.NotFoundException;
import app.exception.ParameterOutOfBoundsException;
import app.exception.NotInRoundException;
import app.dto.JsonWrapper;
import app.dto.Move;
import app.dto.MoveList;
import app.exception.FailedLoginException;
import app.service.RoundService;
import app.service.NodeService;
import app.service.PlayerService;
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
@RequestMapping(value = "/rounds", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoundResource {

    @Inject
    private PlayerService playerService;
    
    @Inject
    private RoundService roundService;

    @Inject
    private NodeService nodeService;

    /**
     * Get a list of all rounds.
     *
     * @return ArrayList of all rounds.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Round> getRounds() {
        return roundService.findAll();
    }

    /**
     * Get a specific game.
     *
     * @param roundId
     * @return game matching roundId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{roundId}", method = RequestMethod.GET)
    public Round getRound(@PathVariable(value = "roundId") String roundId) throws NotANumberException, NotFoundException {

        long i = Validate.parseInt(roundId);
        return roundService.findOne(i);
    }

    /**
     * Get a game map.
     *
     * @param roundIdString
     * @return map of game matching roundId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{roundId}/map", method = RequestMethod.GET)
    public RoundMap getRoundMap(@PathVariable(value = "roundId") String roundIdString) throws NotANumberException, NotFoundException {

        long roundId = Validate.parseInt(roundIdString);
        Round m = roundService.findOne(roundId);
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
     * @param roundIdString
     * @param xString coordinate of map (left to right)
     * @param yString coordinate of map (up to down)
     * @return node with matching coordinates of game matching roundId.
     * @throws NotANumberException
     * @throws NotFoundException
     *
     */
    @RequestMapping(value = "/{roundId}/map/{x}/{y}", method = RequestMethod.GET)
    public Node getRoundMapNode(
            @PathVariable(value = "roundId") String roundIdString,
            @PathVariable(value = "x") String xString,
            @PathVariable(value = "y") String yString) throws NotANumberException, NotFoundException {

        long roundId = Validate.parseInt(roundIdString);
        int x = Validate.parseInt(xString);
        int y = Validate.parseInt(yString);

        return nodeService.getNode(roundId, x, y);
    }

    /**
     * Get the game players
     *
     * @param roundId
     * @return players that play in game with roundId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{roundId}/playerIds", method = RequestMethod.GET)
    public Iterable<Long> getRoundPlayersIds(@PathVariable(value = "roundId") String roundId) throws NotANumberException, NotFoundException {

        long i = Validate.parseLong(roundId);
        Round m = roundService.findOne(i);
        return m.getPlayerIds();
    }

    /**
     * Get a player
     *
     * @param roundIdString
     * @param playerIdString
     * @return player with matching playerId if player is inside game with
     * roundId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{roundId}/players/{playerId}", method = RequestMethod.GET)
    public Player getRoundPlayer(
            @PathVariable(value = "roundId") String roundIdString, 
            @PathVariable(value = "playerId") String playerIdString) throws NotFoundException, NotANumberException {

        long roundId = Validate.parseLong(roundIdString);
        long playerId = Validate.parseLong(playerIdString);
        return roundService.findRoundPlayer(roundId, playerId);
        
    }
    
    /**
     * Post moves as player.
     * @param roundIdString
     * @param playerIdString
     * @param tokenString
     * @param moves
     * @return message of success
     * @throws NotANumberException
     * @throws NotInRoundException
     * @throws ParameterOutOfBoundsException
     * @throws NotFoundException
     * @throws RoundHasEndedException
     */
    @RequestMapping(value = "/{roundId}/players/{playerId}/moves/{token}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public JsonWrapper postMoves(
            @PathVariable(value = "roundId") String roundIdString, 
            @PathVariable(value = "playerId") String playerIdString,
            @PathVariable(value = "token") String tokenString,
            @RequestBody List<Move> moves) throws NotANumberException, NotInRoundException, ParameterOutOfBoundsException, NotFoundException, RoundHasEndedException, FailedLoginException {
        
        MoveList moveList = new MoveList(moves);
        
        long roundId = Validate.parseLong(roundIdString);
        long playerId = Validate.parseLong(playerIdString);
        
        boolean checkedToken = playerService.checkToken(playerId, tokenString);
        
        if (checkedToken)
        {
            roundService.postMoves(roundId, playerId, moveList);
            return new JsonWrapper(moves.size() + " moves for player" + playerId + " where succesfully posted!");
        }
        else
        {
            throw new FailedLoginException();
        }

    }
    

    /**
     * Get the game turn count.
     *
     * @param roundId
     * @return turn count of game matching roundId.
     * @throws NotANumberException 
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{roundId}/turn", method = RequestMethod.GET)
    public JsonWrapper getRoundTurn(@PathVariable(value = "roundId") String roundId) throws NotANumberException, NotFoundException {

        long l = Validate.parseLong(roundId);
        Round m = roundService.findOne(l);
        return new JsonWrapper(m.getTurn());
    }

    /**
     * Get the game rules.
     *
     * @param roundId
     * @return rules of game matching roundId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{roundId}/rules", method = RequestMethod.GET)
    public RoundRules getRoundRules(@PathVariable(value = "roundId") String roundId) throws NotANumberException, NotFoundException {

        long l = Validate.parseInt(roundId);
        Round m = roundService.findOne(l);
        return m.getRoundRules();
    }
    
    /**
     * Get all rounds form a Player
     *
     * @param playerId
     * @return rules of game matching roundId.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/withPlayer/{playerId}", method = RequestMethod.GET)
    public Iterable<Round> getRoundsWithPlayer(@PathVariable(value = "playerId") String playerId) throws NotANumberException, NotFoundException {

        long l = Validate.parseInt(playerId);
        Iterable<Round> rounds = roundService.getRoundsWithPlayer(l);
        return rounds;
    }
    
}
