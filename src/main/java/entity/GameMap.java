package entity;

import java.util.ArrayList;

/**
 * GameMap represents the current board of a round of a game, which is an ArrayList of ArrayLists of nodes.
 * This way, the first index represents y while the second index represents x. 
 * This way, when converted to JSON, the JSON is constructed as a collection of rows of nodes.
 * @author dion
 */
public class GameMap {

    private ArrayList<ArrayList<Node>> nodes;

    /**
     * default constructor.
     */
    public GameMap() {
        nodes = new ArrayList<>();
    }
    
    /**
     * recommended constructor.
     * generates nodes based on the given width and height
     */
    public GameMap(int width, int height) {
        
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
     * @param x coordinate as integer.
     * @param y coordinate as integer.
     * @return Node located at coordinates x and y.
     */
    public Node getNode(int x, int y) {
        return nodes.get(y).get(x);
    }

    
    
    public ArrayList<ArrayList<Node>> getNodes() {
        return nodes;
    }

   
    public void setNodes(ArrayList<ArrayList<Node>> nodes) {
        this.nodes = nodes;
    }
    
  

}
