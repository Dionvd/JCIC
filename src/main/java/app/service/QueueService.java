package app.service;

import app.bean.HostGame;
import app.bean.SocketToUnity;
import app.dao.PlayerRepository;
import app.dao.SettingsRepository;
import app.entity.Round;
import app.entity.Player;
import app.entity.Settings;
import app.dto.WaitingQueue;
import app.exception.NotFoundException;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import app.dao.RoundRepository;

/**
 * QueueService handles Queue Requests. Because the waiting queue does not need
 * to be stored in the database, it is stored as a variable without a dao repo.
 * @author dion
 */
@Service
public class QueueService {

    private static final WaitingQueue waitingQueue = new WaitingQueue();

    @Inject
    PlayerRepository playerRep;
    
    @Inject
    SettingsRepository settingsRep;
    
    @Inject
    RoundRepository roundRep;
    

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

        int i = -1;
        
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
            
            //check if new round can be made
            checkForNewRound();
            
            queuePos = waitingQueue.getSize();
        }
        
        return queuePos;
    }
    
    /**
     * Checks if a new Round can be created with the current players waiting
     * in line.
     * @return
     */
    public Round checkForNewRound() {
        
        if (waitingQueue.getSize() >= Settings.MAX_ROUND_PLAYER_SIZE && HostGame.activeRounds.size() < Settings.MAX_ROUNDS)
        {
            //make a new Round
            
            Round round = new Round(settingsRep.findOne(1L));
            
            //fill round with players from the Waiting Queue
            while (round.getPlayerCount() < round.getMaxPlayerSize()) {
                round.getPlayerIds().add(waitingQueue.RemoveFirst().getId());
            }
            SocketToUnity.setQueueUpdate(waitingQueue);

            roundRep.save(round);
            HostGame.storeRound(round);
            return round;
        }
        return null;
    }

    public static WaitingQueue getWaitingQueue() {
        return waitingQueue;
    }

    
    
}
