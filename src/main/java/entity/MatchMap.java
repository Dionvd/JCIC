package entity;

import exceptions.NotFoundOutOfBoundsException;
import java.util.ArrayList;
import java.util.List;

/**
 * MatchMap represents the current board of a round of the game, which is an
 * ArrayList of ArrayLists of nodes. The first index represents y while the
 * second index represents x. This way, when converted to JSON, the JSON is
 * constructed as a collection of rows of nodes. GameMap is a sub object of
 * Game.
 *
 * @author dion
 */
public class MatchMap {

    private List<List<Node>> nodes;

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
            nodes.add(new ArrayList<>());

            for (int j = 0; j < width; j++) {
                nodes.get(i).add(new Node());
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
        return nodes.get(0).size();
    }

    /**
     * Gets an entire row of nodes from the map.
     * @param rowIndex
     * @return List of nodes.
     * @throws NotFoundOutOfBoundsException when index are out of bounds.
     */
    public List<Node> getNodesRow(int rowIndex) {
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
            return nodes.get(y).get(x);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundOutOfBoundsException(e);
        }

    }

    public List<List<Node>> getNodes() {
        return nodes;
    }

    public void setNodes(List<List<Node>> nodes) {
        this.nodes = nodes;
    }

}
