package app.rest;

import app.dao.MatchRepository;
import app.entity.Player;
import app.entity.Match;
import app.entity.MatchRules;
import app.entity.Node;
import app.entity.MatchMap;
import app.entity.JsonWrapper;
import app.exceptions.NotFoundException;
import java.util.Collection;
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
    MatchRepository mRep;
    
    
    /**
     * Get a list of all matches.
     *
     *
     * @return ArrayList of all matches.
     */
    @RequestMapping(value = "")
    public Iterable<Match> getMatches() {
        return mRep.findAll();
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
        return mRep.findOne(i);
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
        Match m = mRep.findOne(i);
        return m.getMap();
    }

    /**
     * Gets a row of nodes.
     *
     * @param matchId
     * @param row of map
     * @return row of nodes at coordinate y of game matching matchId.
     */
    @RequestMapping(value = "/{matchId}/map/{row}")
    public Collection<Node> getMatchMapRow(
            @PathVariable(value = "matchId") String matchId,
            @PathVariable(value = "row") String row) {

        long i = ResourceMethods.parseInt(matchId);
        int iy = ResourceMethods.parseInt(row);

        Match m = mRep.findOne(i);
        //return m.getMap().getNodesRow(iy);
        return null;
    }

    /**
     * Get a single node of the game map.
     *
     * @param matchId
     * @param x coordinate of map
     * @param y coordinate of map
     * @return node with matching coordinates of game matching matchId. Y must be
     * given first, because Y represents the row number, while X represents
     * column.
     */
    @RequestMapping(value = "/{matchId}/map/{x}/{y}")
    public Node getMatchMapNode(
            @PathVariable(value = "matchId") String matchId,
            @PathVariable(value = "x") String x,
            @PathVariable(value = "y") String y) {

        long i = ResourceMethods.parseInt(matchId);
        int ix = ResourceMethods.parseInt(x);
        int iy = ResourceMethods.parseInt(y);

        Match m = mRep.findOne(i);
        return m.getMap().getNode(ix, iy);
    }

    /**
     * Get the game players
     *
     * @param matchId
     * @return players that play in game with matchId.
     */
    @RequestMapping(value = "/{matchId}/players")
    public Collection<Long> getMatchPlayers(@PathVariable(value = "matchId") String matchId) {

        long i = ResourceMethods.parseLong(matchId);
        Match m = mRep.findOne(i);
        return m.getPlayerIds();
    }

    /**
     * Get a player
     *
     * @param matchId
     * @param playerId
     * @return player with matching playerId if player is inside game with matchId.
     */
    @RequestMapping(value = "/{matchId}/players/{playerId}")
    public JsonWrapper getMatchPlayer(@PathVariable(value = "matchId") String matchId, @PathVariable(value = "playerId") String playerId) {

        long mID = ResourceMethods.parseLong(matchId);
        Match m = mRep.findOne(mID);
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
        Match m = mRep.findOne(i);
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
        Match m = mRep.findOne(i);
        return m.getMatchRules();
    }
}
