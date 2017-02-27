package app.service;

import app.dao.MatchRepository;
import app.entity.Match;
import app.entity.Node;
import app.entity.WaitingQueue;
import app.exception.NotFoundException;
import java.util.ArrayList;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Responsible for creating and disposing of Matches.
 *
 * @author dion
 */
@Service
public class MatchService {

    @Inject
    MatchRepository matchRep;

    private static final int MAX_MATCHES = 5;
    private ArrayList<Match> activeMatches = new ArrayList<>();

    private MatchService() {
    }

    public void checkForNewMatch(WaitingQueue queue) {
        //TODO
    }

    public void hostNewMatch() {
        //TODO
    }

    public void save(Match match) {
        matchRep.save(match);
    }

    public Match findOne(long l) throws NotFoundException {
        Match match = matchRep.findOne(l);
        if (match == null) {
            throw new NotFoundException();
        }
        return match;
    }

    public Iterable<Match> findAll() {
        return matchRep.findAll();
    }

}
