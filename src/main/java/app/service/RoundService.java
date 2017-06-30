package app.service;

import app.util.Validate;
import app.bean.HostGame;
import app.dao.PlayerRepository;
import app.entity.Round;
import app.entity.Player;
import app.exception.RoundHasEndedException;
import app.exception.NotFoundException;
import app.exception.ParameterOutOfBoundsException;
import app.exception.NotInRoundException;
import app.dto.MoveList;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import app.dao.RoundRepository;

/**
 * Responsible for handling Round logic when a RESTful web service is called and
 * accessing related JPA repositories appropriately.
 *
 * @author dion
 */
@Service
public class RoundService {

    @Inject
    RoundRepository roundRep;
    
    @Inject
    PlayerRepository playerRep;
    
    /**
     * Find a single round.
     *
     * @param l
     * @return
     * @throws NotFoundException
     */
    public Round findOne(long l) throws NotFoundException {
        return Validate.notNull(roundRep.findOne(l));
    }

    /**
     * Find all Rounds.
     *
     * @return
     */
    public Iterable<Round> findAll() {
        return roundRep.findAll();
    }

    /**
     * Post the moves the player wants to take for his Round.
     * @param roundId
     * @param playerId
     * @param moves
     * @throws NotInRoundException
     * @throws ParameterOutOfBoundsException
     * @throws NotFoundException
     * @throws RoundHasEndedException
     */
    public void postMoves(long roundId, long playerId, MoveList moves) throws NotInRoundException, ParameterOutOfBoundsException, NotFoundException, RoundHasEndedException {
        
        HostGame.storeMoves(roundId, playerId, moves);
    }

    /**
     * Get a player from a round.
     * @param roundId
     * @param playerId
     * @return
     * @throws NotFoundException
     */
    public Player findRoundPlayer(long roundId, long playerId) throws NotFoundException {
        Round round = Validate.notNull(roundRep.findOne(roundId));
        if (round.getPlayerIds().contains(playerId))
        {
            Player findOne = playerRep.findOne(playerId);
            Validate.notNull(findOne);
            return findOne;
        }
        else
            throw new NotFoundException();
    }

    /**
     * Get all rounds with this player.
     * @param playerId
     * @return rounds that contain this player
     * @throws NotFoundException
     */
    public Iterable<Round> getRoundsWithPlayer(long playerId) throws NotFoundException {
        
        return roundRep.findByPlayerIds(playerId);
        
    }

}
