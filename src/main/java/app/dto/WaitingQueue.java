package app.dto;

import app.entity.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * The Waiting Queue class is responsible for keeping track of bots currently
 * waiting to playing a game. When a game is created, it takes players from the
 * waiting queue to decide which players are put together in a match. It does
 * not store player information, it only references them by playerIds.
 *
 * @author dion
 */
public class WaitingQueue {

    private int maxCount = 100;
    private List<Player> players;

    /**
     * Default constructor.
     */
    public WaitingQueue() {
        players = new ArrayList<>();
    }

    /**
     * Used during game creation, Removes the first waiting player from the
     * queue.
     *
     * @return playerId that was removed from the list
     */
    public Player getAndRemoveFirst() {
        return players.remove(0);
    }

    
    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


    public int getSize() {
        return players.size();
    }
}
