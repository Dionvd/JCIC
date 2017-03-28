package app.entity;

import app.exception.ParameterOutOfBoundsException;
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

/**
 * MatchMap represents the current board of a single round of the game.  
 * This class contains each individual node on the map, stored as one big list.
 *
 * @author dion
 */
@Entity
public class MatchMap implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Y = index/width. X= index%width;
    @OneToMany(targetEntity = Node.class, cascade = CascadeType.ALL)
    private List<Node> nodes; 
    
    private int width;
    private int height;
    
    
    /**
     * Entity constructor. Do not use.
     */
    @Deprecated
    public MatchMap() {
        nodes = new ArrayList<>();
    }

    /**
     * Default constructor. generates nodes based on the given width and
     * height of the map.
     *
     * @param mapSize
     * @param matchId of the match that this map is part of.
     */
    public MatchMap(Point mapSize, long matchId) {

        this.width = mapSize.x;
        this.height = mapSize.y;
        nodes = new ArrayList<>();

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                nodes.add(new Node(new Point(x, y),matchId));
            }
        }
    }

    /**
     * @param x coordinate as integer.
     * @param y coordinate as integer.
     * @return Node located at coordinates x and y.
     * @throws ParameterOutOfBoundsException when index are out of bounds.
     */
    public Node getNode(int x, int y) throws ParameterOutOfBoundsException {

        try {
            if (x < 0 | x >= width) throw new ParameterOutOfBoundsException();
            return nodes.get(x + y*width);
        } catch (IndexOutOfBoundsException e) {
            throw new ParameterOutOfBoundsException(e);
        }

    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    

}
