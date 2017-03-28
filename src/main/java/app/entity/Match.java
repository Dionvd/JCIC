package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    private List<Player> players;

    private final int maxPlayerSize = Settings.MAX_MATCH_PLAYER_SIZE;

    //Map won't be dragged in to game description this way, but can still be requested.
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private MatchMap map;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private MatchRules gameRules;
    private int turn;

    private boolean ended = false;
    
    /**
     * Entity constructor. Do not use.
     */
    @Deprecated
    public Match() {
    }

    /**
     * Default constructor with no players. 
     *
     * @param settings to be used to decide on game rules.
     */
    public Match(Settings settings) {
        players = new ArrayList<>();
        map = new MatchMap(new Point(Settings.DEFAULT_MAP_WIDTH, Settings.DEFAULT_MAP_HEIGHT), id);
        gameRules = new MatchRules(settings);
        turn = 0;
    }

    /**
     *
     * @return amount of players in this game.
     */
    public int getPlayerCount() {
        return players.size();
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getPlayer(long pID) {
        for (Player p : players)
        {
            if (p.getId() == pID)
                return p;
        }
        return null;
    }

    public int getMaxPlayerSize() {
        return maxPlayerSize;
    }

    public MatchRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(MatchRules gameRules) {
        this.gameRules = gameRules;
    }

    public boolean isEnded() {
        return ended;
    }
    
}
