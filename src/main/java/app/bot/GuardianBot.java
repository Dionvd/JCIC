package app.bot;

import app.dto.Move;
import app.dto.MoveList;
import app.entity.Node;
import app.entity.RoundMap;
import app.enums.Action;
import app.enums.MoveDirection;
import app.exception.ParameterOutOfBoundsException;
import java.awt.Point;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Obtains a big territory and defends it against other players. ID 1 -Uses
 * Spread and SpreadAll to get big territories, regardless if they are neutral
 * or player territory. -Uses Guard on nodes that are near a player. -Uses
 * Empower to spread power around.
 *
 * @author dion
 */
public class GuardianBot {

    final static long id = 1;
    
    private GuardianBot() {

    }

    public static MoveList calculateMoves(RoundMap map) {

        List<Move> moves = new ArrayList<>();

        try {

            int width = map.getWidth();
            int height = map.getHeight();
            MoveDirection[] directions = MoveDirection.values();
            ArrayList<Node> myNodes = new ArrayList<>();
            ArrayList<Node> enemyNodes = new ArrayList<>();
            ArrayList<Node> guardingNodes = new ArrayList<>();
            ArrayList<Node> spreadingNodes = new ArrayList<>();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {

                    Node node = map.getNode(x, y);

                    if (node.getOwnerId() == id) {
                        myNodes.add(node);
                    } else if (node.getOwnerId() != 0) {
                        enemyNodes.add(node);

                    }
                }
            }

            //for every enemy node, check if it is adjacent to a friendly node.
            for (Node enemyNode : enemyNodes) {
                for (int i = 1; i < 7; i++) {

                    Point directionPoint = directions[i].getLocation(new Point(enemyNode.getX(), enemyNode.getY()));

                    if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                        continue;
                    }

                    Node adjNode = map.getNode(directionPoint.x, directionPoint.y);
                    if (adjNode.getOwnerId() == id) {
                        if (guardingNodes.contains(adjNode) == false) {
                            guardingNodes.add(adjNode);
                            myNodes.remove(adjNode);
                        }
                    }
                }
            }

            //for every guardingNode, guard if enough power.
            for (Node guardingNode : guardingNodes) {

                if (guardingNode.getType() != 3) {
                    if (guardingNode.getPower() >= 30) {
                        moves.add(new Move(new Point(guardingNode.getX(), guardingNode.getY()), Action.GUARD, MoveDirection.CENTRAL));
                    }
                } else {   //node is already protected, add it so that it spreads.
                    if (guardingNode.getPower() >= 10) {
                        spreadingNodes.add(guardingNode);
                    }
                }

            }

            //for every other owned node, empower if too much power, potentially spread if enough power.
            for (Node myNode : myNodes) {
                if (myNode.getPower() >= 50 && myNodes.size() > 1) {
                    moves.add(new Move(new Point(myNode.getX(), myNode.getY()), Action.EMPOWER, MoveDirection.CENTRAL));
                } else if (myNode.getPower() >= 10) {
                    spreadingNodes.add(myNode);
                }
            }

            //wait for spreadAll if 4 or more neutral/enemy adjacent nodes.
            //otherwise, spread to a random nearby neutral/enemy node
            for (Node spreadingNode : spreadingNodes) {

                List<MoveDirection> unownedDirections = new ArrayList<>();

                for (int i = 1; i < 7; i++) {
                    Point directionPoint = directions[i].getLocation(new Point(spreadingNode.getX(), spreadingNode.getY()));

                    if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                        continue;
                    }

                    Node adjNode = map.getNode(directionPoint.x, directionPoint.y);
                    if (adjNode.getOwnerId() != id) {
                        unownedDirections.add(directions[i]);
                    }
                }

                if (unownedDirections.size() >= 4) {
                    //spreadAll if possible
                    if (spreadingNode.getPower() > 40) {
                        moves.add(new Move(new Point(spreadingNode.getX(), spreadingNode.getY()), Action.SPREADALL, MoveDirection.CENTRAL));
                    }
                } else if (unownedDirections.size() >= 1) {
                    //spread in a random direction, power was already checked previously
                    MoveDirection randomDirection = unownedDirections.get((int) (Math.random() * unownedDirections.size()));
                    moves.add(new Move(new Point(spreadingNode.getX(), spreadingNode.getY()), Action.SPREAD, randomDirection));
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(GuardianBot.class.getName()).log(Level.SEVERE, null, ex);    
        }

        
        
        return new MoveList(moves);
    }

}
