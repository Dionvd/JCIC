package app.entity;

import app.service.NodeService;
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
     *  default constructor
     */
    public Node() {
    }
    
    /**
     * Constructor that automatically sets id based on mapId, x and y coordinate.
     * @param x
     * @param y
     * @param mapId
     */
    public Node(int x, int y, long matchId)
    {
        this.id = NodeService.calcNodeKey(x, y, matchId);
    }

    
    public int getX() {
        return (int)((id%10000) / 100);
    }
    
    public int getY() {
        return (int)(id%100);
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
