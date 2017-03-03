package app.service;

import app.dao.MatchRepository;
import app.dao.PlayerRepository;
import app.dao.SettingsRepository;
import app.entity.Match;
import app.entity.Player;
import app.entity.Settings;
import app.object.WaitingQueue;
import app.exception.NotFoundException;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * QueueService handles Queue Requests. Because the waiting queue does not need
 * to be stored in the database, it is stored as a variable without a dao repo.
 * @author dion
 */
@Service
public class QueueService {

    private static WaitingQueue waitingQueue = new WaitingQueue();

    @Inject
    PlayerRepository playerRep;
    
    @Inject
    SettingsRepository settingsRep;
    
    @Inject
    MatchRepository matchRep;
    
    int openMatchSpots = Settings.MAX_MATCHES;

    /**
     * Gets the current WaitingQueue.
     * @return
     */
    public WaitingQueue get() {
        return waitingQueue;
    }
   
    
    /**
     * Gets and returns the position (index+1) of playerId in the waiting queue.
     *
     * @param playerId
     * @return position in the waiting queue
     * @throws NotFoundException when playerId cannot be found in the queue.
     */
    public int getPositionOfPlayer(long playerId) throws NotFoundException {

        int i = 0;
        
        for (Player p : waitingQueue.getPlayers())
        {
            if (p.getId() == playerId)
                return waitingQueue.getPlayers().indexOf(p)+1;
        }

        if (i == -1) {
            throw new NotFoundException();
        }
        return i + 1;
    }

    /**
     * Joins the Queue as a player.
     * @param playerId
     * @return queue position
     */
    public int joinQueue(long playerId) {
        
        int queuePos;
        
        try {
            queuePos = getPositionOfPlayer(playerId);
        } catch (Exception e) {
            //Player is not yet in queue, add him.
            waitingQueue.getPlayers().add(playerRep.findOne(playerId));
            queuePos = waitingQueue.getSize();
        }
        
        return queuePos;
    }
    
    /**
     * Checks if a new Match can be created with the current players waiting
     * in line.
     * @return
     */
    public Match checkForNewMatch() {
        
        if (waitingQueue.getSize() > Settings.MAX_MATCH_PLAYER_SIZE && openMatchSpots > 0)
        {
            //make a new Match
            openMatchSpots--;
            
            Match match = new Match(settingsRep.findOne(0L));
            
            //fill match with players from the Waiting Queue
            while (match.getPlayerCount() < match.getMaxPlayerSize()) {
                match.getPlayers().add(waitingQueue.getAndRemoveFirst());
            }
            
            matchRep.save(match);
            return match;
        }
        return null;
    }

    public static WaitingQueue getWaitingQueue() {
        return waitingQueue;
    }

    
    
}
