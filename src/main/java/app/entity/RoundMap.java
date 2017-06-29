package app.entity;

import app.exception.ParameterOutOfBoundsException;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * RoundMap represents the current board of a single round of the game.  
 * This class contains each individual node on the map, stored as one big list.
 *
 * @author dion
 */
@Entity
public class RoundMap implements Serializable  {

    @Id
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
    public RoundMap() {
        nodes = new ArrayList<>();
    }

    /**
     * Default constructor. generates nodes based on the given width and
     * height of the map.
     *
     * @param mapSize
     * @param roundId of the round that this map is part of.
     */
    public RoundMap(Point mapSize, long roundId) {

        this.width = mapSize.x;
        this.height = mapSize.y;
        nodes = new ArrayList<>();
        this.setId(roundId);

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                nodes.add(new Node(new Point(x, y),roundId));
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
    
    public void generate(Set<Long> playerIds)
    {
        try {
            
            Iterator<Long> iterator = playerIds.iterator();
            
            for (int i = 0; i < playerIds.size(); i++) {
                
                switch (i)
                {
                    case 0:
                        getNode(0, 0).setOwnerId(iterator.next());
                        break;
                    case 1:
                        getNode(9, 9).setOwnerId(iterator.next());
                        break;
                    case 2:
                        getNode(9, 0).setOwnerId(iterator.next());
                        break;
                    case 3:
                        getNode(0, 9).setOwnerId(iterator.next());
                        break;
                }
            }
        
            for (int i = 0; i < 7; i++) {
                
                int x = (int)(Math.random()*8)+1;
                int y = (int)(Math.random()*8)+1;

                getNode(x, y).setType(-1);
                getNode(9-x, 9-y).setType(-1);
            }
        
        }
        catch(Exception e) { }
    }

    public long getPlayerWithMostNodes() {
        
        if (nodes == null) return -1;
        
        Map<Long, Long> countMap = nodes.stream()
            .collect(Collectors.groupingBy(p -> p.getOwnerId(), 
         Collectors.counting()));
        
        Long winnerId = 0L;
        Long winnerCount = 0L;
        
        for (Map.Entry<Long, Long> entry : countMap.entrySet()) {
            if (entry.getKey() == 0L || entry.getKey() == -1L) continue;
            
            if (entry.getValue() > winnerCount)
            {
                winnerId = entry.getKey();
                winnerCount = entry.getValue();
            }
        }
        
        return winnerId;
    }

}
