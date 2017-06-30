package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * The Round class represents the current state of a single round of the game
 * being played. Contains round data and references to playerIds. Map is a hidden
 JSON value to avoid overhead.
 *
 * @author dion
 */
@Entity
public class Round implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> playerIds;

    private final int maxPlayerSize = Settings.MAX_ROUND_PLAYER_SIZE;

    //Map won't be dragged in to game description this way, but can still be requested.
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private RoundMap map;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private RoundRules gameRules;
    
    private int turn = 0;

    private boolean ended = false;
    
    /**
     * Entity constructor. Do not use.
     */
    @Deprecated
    public Round() {
    }

    /**
     * Default constructor with no playerIds. 
     *
     * @param settings to be used to decide on game rules.
     */
    public Round(Settings settings) {
        playerIds = new HashSet<>();
        //map = new RoundMap(new Point(Settings.DEFAULT_MAP_WIDTH, Settings.DEFAULT_MAP_HEIGHT), id);
        gameRules = new RoundRules(settings);
        turn = 0;
    }

    /**
     *
     * @return amount of playerIds in this game.
     */
    public int getPlayerCount() {
        return playerIds.size();
    }

    public RoundMap getMap() {
        return map;
    }

    public void setMap(RoundMap map) {
        this.map = map;
    }

    public RoundRules getRoundRules() {
        return gameRules;
    }

    public void setRoundRules(RoundRules gameRules) {
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

    public Set<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(Set<Long> playerIds) {
        this.playerIds = playerIds;
    }


    public int getMaxPlayerSize() {
        return maxPlayerSize;
    }

    public RoundRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(RoundRules gameRules) {
        this.gameRules = gameRules;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean b) {
        ended = b;
    }
    
}
