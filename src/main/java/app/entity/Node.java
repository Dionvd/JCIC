package app.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A Node is one of the hexagons on one of the maps of a single game.
 * MatchMap contains a grid of nodes, where the coordinates of a Node are stored as the indexes.
 * The current owner of this node can be a player, in which an instance of Player is referenced with ownerId.
 * @author dion
 */
public class Node implements Serializable {

  
    private int power = 0;
    private long ownerId = 0;
    
    //-1 = empty, 0 = normal, 1 = powerline, 2 = overclocked, 3 = guarded, 4 = storage
    private int type = 0; 

    /**
     *
     */
    public Node() {
    }

    /**
     *
     * @return
     */
    public int getPower() {
        return power;
    }

    /**
     *
     * @param power
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     *
     * @return
     */
    public long getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @param ownerId
     */
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    
}