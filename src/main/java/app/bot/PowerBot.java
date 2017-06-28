package app.bot;

import app.dto.Move;
import app.dto.MoveList;
import app.entity.Node;
import app.entity.RoundMap;
import app.enums.Action;
import app.enums.MoveDirection;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Unifies the power usage of its territory.
 * ID 3
 * -Uses Spread and SpreadAll to gain territory.
 * -Uses Powerline on centered territory.
 * -Uses Storage on excess power lined centered territory when they are full on power.
 * @author dion
 */
public class PowerBot {

    final static long id = 3;
    
    private PowerBot()
    {
        
    }
    
    public static MoveList calculateMoves(RoundMap map) {
        List<Move> moves = new ArrayList<>();

        try {

            int width = map.getWidth();
            int height = map.getHeight();
            MoveDirection[] directions = MoveDirection.values();
            ArrayList<Node> myNodes = new ArrayList<>();
            ArrayList<Node> enemyNodes = new ArrayList<>();
            ArrayList<Node> connectedNodes = new ArrayList<>();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {

                    Node node = map.getNode(x, y);

                    if (node.getOwnerId() == 1) {
                        myNodes.add(node);
                    } else if (node.getOwnerId() != 0) {
                        enemyNodes.add(node);

                    }
                }
            }
            
            

        } catch (Exception ex) {
            Logger.getLogger(GuardianBot.class.getName()).log(Level.SEVERE, null, ex);    
        }
        
        return new MoveList(moves);
    }

    
}
