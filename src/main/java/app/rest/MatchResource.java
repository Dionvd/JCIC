package app.rest;

import app.entity.Match;
import app.entity.MatchRules;
import app.entity.Node;
import app.entity.MatchMap;
import app.entity.JsonWrapper;
import app.exception.NotFoundException;
import app.service.MatchService;
import app.service.NodeService;
import javax.inject.Inject;

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
@RequestMapping(value = "/matches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchResource {

    @Inject
    private MatchService matchService;
    
    @Inject
    private NodeService nodeService;

    /**
     * Get a list of all matches.
     *
     *
     * @return ArrayList of all matches.
     */
    @RequestMapping(value = "")
    public Iterable<Match> getMatches() {
        return matchService.findAll();
    }

    /**
     * Get a specific game.
     *
     *
     *
     * @param matchId
     * @return game matching matchId.
     */
    @RequestMapping(value = "/{matchId}")
    public Match getMatch(@PathVariable(value = "matchId") String matchId) {

        long i = ResourceMethods.parseInt(matchId);
        return matchService.findOne(i);
    }

    /**
     * Get a game map.
     *
     * @param matchId
     * @return map of game matching matchId.
     */
    @RequestMapping(value = "/{matchId}/map")
    public MatchMap getMatchMap(@PathVariable(value = "v") String matchId) {

        long i = ResourceMethods.parseInt(matchId);
        Match m = matchService.findOne(i);
        return m.getMap();
    }

    
    /**
     * Get a single node.
     *
     * @param nodeId
     * @return node with matching nodeId.
     */
    @RequestMapping(value = "/nodes/{nodeId}")
    public Node getNode(
            @PathVariable(value = "nodeId") String nodeId) {

        long i = ResourceMethods.parseInt(nodeId);

        return nodeService.getNode(i);
    }
    
    
    
    /**
     * Get a single node of a game map.
     *
     * @param matchId
     * @param x coordinate of map (left to right)
     * @param y coordinate of map (up to down)
     * @return node with matching coordinates of game matching matchId.
     * 
     */
    @RequestMapping(value = "/{matchId}/map/{x}/{y}")
    public Node getMatchMapNode(
            @PathVariable(value = "matchId") String matchId,
            @PathVariable(value = "x") String x,
            @PathVariable(value = "y") String y) {

        long i = ResourceMethods.parseInt(matchId);
        int ix = ResourceMethods.parseInt(x);
        int iy = ResourceMethods.parseInt(y);
        
        return nodeService.getNode(ix, iy, i);
    }

    /**
     * Get the game players
     *
     * @param matchId
     * @return players that play in game with matchId.
     */
    @RequestMapping(value = "/{matchId}/players")
    public Iterable<Long> getMatchPlayers(@PathVariable(value = "matchId") String matchId) {

        long i = ResourceMethods.parseLong(matchId);
        Match m = matchService.findOne(i);
        return m.getPlayerIds();
    }

    /**
     * Get a player
     *
     * @param matchId
     * @param playerId
     * @return player with matching playerId if player is inside game with
     * matchId.
     */
    @RequestMapping(value = "/{matchId}/players/{playerId}")
    public JsonWrapper getMatchPlayer(@PathVariable(value = "matchId") String matchId, @PathVariable(value = "playerId") String playerId) {

        long mID = ResourceMethods.parseLong(matchId);
        Match m = matchService.findOne(mID);
        long pID = ResourceMethods.parseLong(playerId);

        if (m.getPlayerIds().contains(pID)) {
            return new JsonWrapper(pID);
        }

        throw new NotFoundException();
    }

    /**
     * Get the game turn count.
     *
     * @param matchId
     * @return turn count of game matching matchId.
     */
    @RequestMapping(value = "/{matchId}/turn")
    public JsonWrapper getMatchTurn(@PathVariable(value = "matchId") String matchId) {

        long i = ResourceMethods.parseLong(matchId);
        Match m = matchService.findOne(i);
        return new JsonWrapper(m.getTurn());
    }

    /**
     * Get the game rules.
     *
     * @param matchId
     * @return rules of game matching matchId.
     */
    @RequestMapping(value = "/{matchId}/rules")
    public MatchRules getMatchRules(@PathVariable(value = "matchId") String matchId) {

        long i = ResourceMethods.parseInt(matchId);
        Match m = matchService.findOne(i);
        return m.getMatchRules();
    }
}
