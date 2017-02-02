package entity;

import exceptions.NotFoundException;
import java.util.ArrayList;

/**
 *
 * @author dion
 */
public class WaitingQueue {

    private int maxCount = 100;
    private ArrayList<Integer> playerIds;

    /**
     *
     */
    public WaitingQueue() {
        playerIds = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     *
     * @param maxCount
     */
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getPlayerIds() {
        return playerIds;
    }

    /**
     *
     * @param playerIds
     */
    public void setPlayerIds(ArrayList<Integer> playerIds) {
        this.playerIds = playerIds;
    }

    /**
     *
     * @param playerId
     * @return
     */
    public int getPositionOfPlayer(int playerId) {

        int i = this.playerIds.indexOf(playerId);

        if (i == -1) {
            throw new NotFoundException();
        }
        return i+1;
    }
}
