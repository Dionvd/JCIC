package app.dto;

import app.entity.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to send a JSON object that contains moves, as opposed to
 * a raw list of moves where data might be lost on transfer.
 * @author dion
 */
public class PlayerList {
    
    List<Player> players;

    public PlayerList() {
        this.players = new ArrayList<>();
    }
    public PlayerList(List<Player> players) {
        this.players = new ArrayList<>(players);
    }
    
    
     public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
     
}
