package app.entity;

import app.exceptions.NotFoundOutOfBoundsException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
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

    @OneToMany(targetEntity=MatchRow.class, cascade = CascadeType.ALL)
    private List<MatchRow> nodes;
               
    
    /**
     * default constructor.
     */
    public MatchMap() {
        nodes = new ArrayList<>();
    }

    /**
     * recommended constructor. 
     * generates nodes based on the given width and height.
     *
     * @param width
     * @param height
     */
    public MatchMap(int width, int height) {

        nodes = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            nodes.add(new MatchRow());

            for (int j = 0; j < width; j++) {
                nodes.get(i).nodes.add(new Node());
            }
        }

    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return nodes.size();
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return nodes.get(0).nodes.size();
    }

    /**
     * Gets an entire row of nodes from the map.
     * @param rowIndex
     * @return List of nodes.
     * @throws NotFoundOutOfBoundsException when index are out of bounds.
     */
    public MatchRow getNodesRow(int rowIndex) {
        try {
            return nodes.get(rowIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundOutOfBoundsException(e);
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
            return nodes.get(y).nodes.get(x);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundOutOfBoundsException(e);
        }

    }

    public List<MatchRow> getNodes() {
        return nodes;
    }

    public void setNodes(List<MatchRow> nodes) {
        this.nodes = nodes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
}
