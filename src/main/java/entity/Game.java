package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents the current state of a match being played.
 * Contains game data and references to players.
 * Map is a hidden value to avoid overhead.
 * @author dion
 */
public class Game {

    private long id = 0;
    private final AtomicLong idCounter = new AtomicLong(999);

    private ArrayList<Integer> playerIds;
    
    @JsonIgnore //Map won't be dragged in to game description this way, but can still be requested.
    private GameMap map;

    private GameRules gameRules;
    private int turn;

    /**
     *  Default game constructor.
     */
    public Game() {
        playerIds = new ArrayList<>();
        map = new GameMap();
        gameRules = new GameRules();
        turn = 0;
        id = idCounter.incrementAndGet();

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

    public void setId(long Id) {
        this.id = Id;
    }

    public ArrayList<Integer> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(ArrayList<Integer> playerIds) {
        this.playerIds = playerIds;
    }

    
}
