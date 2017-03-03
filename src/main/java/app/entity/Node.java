package app.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A Node is one of the hexagons on one of the maps of a single game. MatchMap
 * contains a grid of nodes, where the coordinates of a Node are stored as the
 * indexes. The current owner of this node can be a player, in which an instance
 * of Player is referenced with ownerId.
 *
 * @author dion
 */
@Entity
public class Node implements Serializable {

    @Id
    private long id; //MAPIDXXYY

    private int power = 0;
    private long ownerId = 0;

    //-1 = empty, 0 = normal, 1 = powerline, 2 = overclocked, 3 = guarded, 4 = storage
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
     * @param matchId
     * @param x
     * @param y
     */
    public Node(int x, int y, long matchId) {
        this.id = Node.calcKey(matchId, x, y);
    }

    /**
     * Calculates the node key by combining the mapId, x coordinate
     * and y coordinate.
     * @param matchId
     * @param x
     * @param y
     * @return id
     */
    public static Long calcKey(Long matchId, int x, int y)
    {
        return matchId * 10000 + x*100+y;
    }
    
    /**
     * Reads the X coordinate from the id.
     * @return X coordinate
     */
    public int getX() {
        return (int) ((id % 10000) / 100);
    }

    
    
    /**
     * Reads the Y coordinate from the id.
     * @return Y coordinate
     */
    public int getY() {
        return (int) (id % 100);
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
