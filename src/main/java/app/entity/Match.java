package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * The Match class represents the current state of a single match of the game
 * being played. Contains match data and references to players. Map is a hidden
 * JSON value to avoid overhead.
 *
 * @author dion
 */
@Entity
public class Match implements Serializable {

    private static final int PLAYER_SIZE_MAX = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection
    private List<Long> playerIds;

    private int playerSize = PLAYER_SIZE_MAX;

    //Map won't be dragged in to game description this way, but can still be requested.
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private MatchMap map;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private MatchRules gameRules;
    private int turn;

    public Match() {
    }

    /**
     * Default game constructor.
     *
     * @param settings to be used to decide on game rules.
     */
    public Match(Settings settings) {
        playerIds = new ArrayList<>();
        map = new MatchMap();
        gameRules = new MatchRules(settings);
        turn = 0;
    }

    /**
     * Match constructor that picks and removes players from the WaitingQueue
     *
     * @param settings
     * @param waitingQueue
     */
    public Match(Settings settings, WaitingQueue waitingQueue) {
        playerIds = new ArrayList<>();
        map = new MatchMap();
        gameRules = new MatchRules(settings);
        turn = 0;

        while (playerIds.size() < playerSize) {
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

    public MatchRules getMatchRules() {
        return gameRules;
    }

    public void setMatchRules(MatchRules gameRules) {
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

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

}
