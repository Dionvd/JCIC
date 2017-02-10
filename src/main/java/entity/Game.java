package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The Game class represents the current state of a single match being played.
 * Contains game data and references to players.
 * Map is a hidden value to avoid overhead.
 * @author dion
 */
public class Game {

    private long id = 0;
    private static final AtomicLong ID_COUNTER = new AtomicLong(999);

    private List<Integer> playerIds;
    
    //Map won't be dragged in to game description this way, but can still be requested.
    @JsonIgnore 
    private GameMap map;

    @JsonIgnore 

    private GameRules gameRules;
    private int turn;

    /**
     *  Default game constructor.

     * @param settings to be used to decide on game rules.
     */
    public Game(Settings settings) {
        playerIds = new ArrayList<>();
        map = new GameMap();
        gameRules = new GameRules(settings);
        turn = 0;
        id = ID_COUNTER.incrementAndGet();

    }

    /**
     *
     * @return size of playerIds in this game.
     */
    public int getPlayerCount() {
        return playerIds.size();
    }

  
    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
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
