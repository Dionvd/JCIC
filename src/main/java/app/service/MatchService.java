package app.service;

import app.FindException;
import app.dao.MatchRepository;
import app.entity.Match;
import app.entity.Player;
import app.object.WaitingQueue;
import app.exception.NotFoundException;
import app.object.Move;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    Map<Long, Iterable<Move>> playerMoves = new HashMap<>();
    
    /**
     * Find a single match.
     *
     * @param l
     * @return
     * @throws NotFoundException
     */
    public Match findOne(long l) throws NotFoundException {
        return FindException.notFoundOnNull(matchRep.findOne(l));
    }

    /**
     * Find all matches.
     *
     * @return
     */
    public Iterable<Match> findAll() {
        return matchRep.findAll();
    }

    public void postMoves(long matchId, long playerId, Iterable moves) {
        
        playerMoves.put(playerId, moves);
    }

    public Player findMatchPlayer(long matchId, long playerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
