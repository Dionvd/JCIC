package entity;

import exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Waiting Queue class is responsible for keeping track of bots currently waiting to playing a game.
 * When a game is created, it takes players from the waiting queue to decide which players are put together in a match.
 * It does not store player information, it only references them by playerIds.
 * @author dion
 */
public class WaitingQueue {

    private int maxCount = 100;
    private List<Integer> playerIds;

    /**
     *  Default constructor.
     */
    public WaitingQueue() {
        playerIds = new ArrayList<>();
    }

   
    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public List<Integer> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Integer> playerIds) {
        this.playerIds = playerIds;
    }

    /**
     * Gets and returns the position (index+1) of playerId in the waiting queue. 
     * @param playerId
     * @return position in the waiting queue
     * @throws NotFoundException when playerId cannot be found in the queue.
     */
    public int getPositionOfPlayer(int playerId) {

        int i = this.playerIds.indexOf(playerId);

        if (i == -1) {
            throw new NotFoundException();
        }
        return i+1;
    }

    /**
     * Used during game creation, Removes the first waiting player from the queue.
     * @return playerId that was removed from the list
     */
    public int getAndRemoveFirst() {
        return this.playerIds.remove(0);
    }
}
