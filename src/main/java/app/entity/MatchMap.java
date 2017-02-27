package app.entity;

import app.exception.NotFoundOutOfBoundsException;
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
 * MatchMap represents the current board of a round of the game, which is an
 * ArrayList of ArrayLists of nodes. The first index represents y while the
 * second index represents x. This way, when converted to JSON, the JSON is
 * constructed as a collection of rows of nodes. MatchMap is a sub object of
 * Match.
 *
 * @author dion
 */
@Entity
public class MatchMap implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Y = index/width. X= index%width;
    @OneToMany(targetEntity = Node.class, cascade = CascadeType.ALL)
    private List<Node> nodes; 
    
    private int width;
    private int height;
    
    
    /**
     * default constructor.
     */
    public MatchMap() {
        nodes = new ArrayList<>();
    }

    /**
     * recommended constructor. generates nodes based on the given width and
     * height.
     *
     * @param width
     * @param height
     */
    public MatchMap(int width, int height, long mapId) {

        this.width = width;
        this.height = height;
        nodes = new ArrayList<>();

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                nodes.add(new Node(x,y,mapId));
            }
        }

    }

    /**
     * @param x coordinate as integer.
     * @param y coordinate as integer.
     * @return Node located at coordinates x and y.
     * @throws NotFoundOutOfBoundsException when index are out of bounds.
     */
    public Node getNode(int x, int y) {

        try {
            return nodes.get(x + y*width);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundOutOfBoundsException(e);
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
