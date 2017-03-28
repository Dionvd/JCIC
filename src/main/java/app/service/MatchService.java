package app.service;

import app.util.Validate;
import app.bean.HostGame;
import app.dao.MatchRepository;
import app.dao.PlayerRepository;
import app.entity.Match;
import app.entity.Player;
import app.exception.MatchHasEndedException;
import app.exception.NotFoundException;
import app.exception.ParameterOutOfBoundsException;
import app.exception.NotInMatchException;
import app.dto.MoveList;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Responsible for handling Match logic when a RESTful web service is called and
 * accessing related JPA repositories appropriately.
 *
 * @author dion
 */
@Service
public class MatchService {

    @Inject
    MatchRepository matchRep;
    
    @Inject
    PlayerRepository playerRep;
    
    /**
     * Find a single match.
     *
     * @param l
     * @return
     * @throws NotFoundException
     */
    public Match findOne(long l) throws NotFoundException {
        return Validate.notNull(matchRep.findOne(l));
    }

    /**
     * Find all matches.
     *
     * @return
     */
    public Iterable<Match> findAll() {
        return matchRep.findAll();
    }

    /**
     * Post the moves the player wants to take for his Match..
     * @param matchId
     * @param playerId
     * @param moves
     * @throws NotInMatchException
     * @throws ParameterOutOfBoundsException
     * @throws NotFoundException
     * @throws MatchHasEndedException
     */
    public void postMoves(long matchId, long playerId, MoveList moves) throws NotInMatchException, ParameterOutOfBoundsException, NotFoundException, MatchHasEndedException {
        
        HostGame.storeMoves(matchId, playerId, moves);
    }

    /**
     * Get a player from a match.
     * @param matchId
     * @param playerId
     * @return
     * @throws NotFoundException
     */
    public Player findMatchPlayer(long matchId, long playerId) throws NotFoundException {
        Match match = Validate.notNull(matchRep.findOne(matchId));
        return Validate.notNull(match.getPlayer(playerId));
    }

    /**
     * Get all matches with this player.
     * @param playerId
     * @return matches that contain this player
     * @throws NotFoundException
     */
    public Iterable<Match> getMatchesWithPlayer(long playerId) throws NotFoundException {
        
        Player player = Validate.notNull(playerRep.findOne(playerId));
        return matchRep.findByPlayers(player);
        
    }

}
