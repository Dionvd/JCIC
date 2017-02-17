package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The Match class represents the current state of a single match of the game being played.
 * Contains match data and references to players.
 * Map is a hidden JSON value to avoid overhead.
 * @author dion
 */
public class Match {

    private static final int PLAYER_SIZE_MAX = 4;
    
    private long id = 0;
    private static final AtomicLong ID_COUNTER = new AtomicLong(999);

    private List<Integer> playerIds;
    private int playerSize = PLAYER_SIZE_MAX;
    
    //Map won't be dragged in to game description this way, but can still be requested.
    @JsonIgnore 
    private MatchMap map;

    @JsonIgnore 

    private MatchRules gameRules;
    private int turn;

    /**
     *  Default game constructor.

     * @param settings to be used to decide on game rules.
     */
    public Match(Settings settings) {
        playerIds = new ArrayList<>();
        map = new MatchMap();
        gameRules = new MatchRules(settings);
        turn = 0;
        id = ID_COUNTER.incrementAndGet();
    }
    
    /**
     * Game constructor that picks and removes players from the WaitingQueue
     * @param settings
     * @param waitingQueue
     */
    public Match(Settings settings, WaitingQueue waitingQueue)
    {
        playerIds = new ArrayList<>();
        map = new MatchMap();
        gameRules = new MatchRules(settings);
        turn = 0;
        id = ID_COUNTER.incrementAndGet();
        
        while (playerIds.size() < playerSize)
        {
            //TODO Exception handling
            playerIds.add(waitingQueue.getAndRemoveFirst());
        }
    }

    /**
     *
     * @return size of playerIds in this game.
     */
    public int getPlayerCount() {
        return playerIds.size();
    }

  
    public MatchMap getMap() {
        return map;
    }

    public void setMap(MatchMap map) {
        this.map = map;
    }

    public MatchRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(MatchRules gameRules) {
        this.gameRules = gameRules;
    }
  
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Integer> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Integer> playerIds) {
        this.playerIds = playerIds;
    }

    
}
