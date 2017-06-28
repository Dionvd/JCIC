package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.Point;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A Node is one of the hexagons on one of the maps of a single game. RoundMap
 * contains a grid of nodes, where the coordinates of a Node are stored as the
 * indexes. The current owner of this node can be a player, in which an instance
 * of Player is referenced with ownerId.
 *
 * @author dion
 */
@Entity
public class Node implements Serializable {

    private final static int MAX_POWER = 100;

    @Id
    private long id; //MAPIDXXYY

    private int power = 0;
    private long ownerId = 0;

    //-1 = blocked, 0 = normal, 1 = powerline, 2 = overclocked, 3 = guarded, 4 = storage
    private int type = 0;

    /**
     * Entity constructor. Do not use.
     */
    @Deprecated
    public Node() {
    }

    /**
     * Default Constructor. Automatically sets id.
     *
     * @param location
     * @param roundId
     */
    public Node(Point location, long roundId) {
        this.id = Node.calcKey(roundId, location);
    }

    /**
     * Calculates the node key by combining the mapId, x coordinate and y
     * coordinate.
     *
     * @param roundId
     * @param location
     * @return id
     */
    public static Long calcKey(Long roundId, Point location) {
        return roundId * 10000 + location.x * 100 + location.y;
    }

    /**
     * called when a node is attacked.
     *
     * @param attackerId
     * @return attack went through
     */
    public boolean attackedBy(long attackerId) {

        //if node is already owned, do nothing.
        if (this.ownerId == attackerId) 
        {
            return false;
        }

        //if node is blocked, do nothing.
        if (this.type == -1) 
        { 
            return false;
        }

        //if node is guarded, destroy guard.
        if (this.type == 3) 
        { 
            this.type = 0;
            return true;
        }

        //if node is neutral, take over node.
        if (this.ownerId == 0) 
        {
            this.ownerId = attackerId;  
        } 
        //else the node is an enemy node, destroy it.
        else 
        {
            ownerId = 0; 
        }
        this.power = 0;
        this.type = 0;
        return true;
    }

    /**
     * Reads the X coordinate from the id.
     *
     * @return X coordinate
     */
    @JsonIgnore
    public int getX() {
        return (int) ((id % 10000) / 100);
    }

    /**
     * Reads the Y coordinate from the id.
     *
     * @return Y coordinate
     */
    @JsonIgnore
    public int getY() {
        return (int) (id % 100);
    }

    public void adjustPower(int amount) {
        this.power += amount;
        if (power > MAX_POWER * (this.type==4?3:1) ) {
            power = MAX_POWER;
        }
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
