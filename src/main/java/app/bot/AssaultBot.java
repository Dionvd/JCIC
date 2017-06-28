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
 * Suppresses other players while taking their territory. ID 2 -Uses Spread to
 * gain territory. -Uses Empower to spread power around. -Uses Drain on outer
 * rim to drain power from enemy nodes. -Uses SpreadLine from first or second
 * row to attack players.
 *
 * @author dion
 */
public class AssaultBot {

    final static long id = 2;
    
    private AssaultBot() {

    }

    public static MoveList calculateMoves(RoundMap map) {

        List<Move> moves = new ArrayList<>();

        ArrayList<Node> myNodes = new ArrayList<>();
        ArrayList<Node> enemyNodes = new ArrayList<>();
        ArrayList<Node> drainNodes = new ArrayList<>();
        ArrayList<Node> attackingNodes = new ArrayList<>();
        try {
            int width = map.getWidth();
            int height = map.getHeight();
            MoveDirection[] directions = MoveDirection.values();
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
                        if (drainNodes.contains(adjNode) == false) {
                            drainNodes.add(adjNode);
                            myNodes.remove(adjNode);
                        }
                    }
                }
            }

            //for every node adjacent to an enemy, check if there are friendly nodes not adjacent to an enemy, te be used as artillery.
            for (Node drainNode : drainNodes) {
                for (int i = 1; i < 7; i++) {

                    Point directionPoint = directions[i].getLocation(new Point(drainNode.getX(), drainNode.getY()));

                    if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                        continue;
                    }

                    Node adjNode = map.getNode(directionPoint.x, directionPoint.y);
                    if (adjNode.getOwnerId() == 2) {
                        if (myNodes.contains(adjNode)) {
                            myNodes.remove(adjNode);
                            attackingNodes.add(adjNode);
                        }
                    }
                }
            }

            //for every draining node, send move to drain if not enough power to attack.
            for (Node drainNode : drainNodes) {
                if (drainNode.getPower() < 60) {
                    moves.add(new Move(new Point(drainNode.getX(), drainNode.getY()), Action.DRAIN, MoveDirection.CENTRAL));
                } else {
                    attackingNodes.add(drainNode);
                }
            }

            //for every attacking node, attack if enough power!
            for (Node attackingNode : attackingNodes) {
                if (attackingNode.getPower() >= 60) {

                    //decide direction
                    List<MoveDirection> unownedDirections = new ArrayList<>();

                    for (int i = 1; i < 7; i++) {
                        Point directionPoint = directions[i].getLocation(new Point(attackingNode.getX(), attackingNode.getY()));

                        if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                            continue;
                        }

                        Node adjNode = map.getNode(directionPoint.x, directionPoint.y);
                        if (adjNode.getOwnerId() != id) {
                            unownedDirections.add(directions[i]);
                        }
                    }
                    
                    if (unownedDirections.size() > 0)
                    {
                        //attack!
                        MoveDirection randomDirection = unownedDirections.get((int) (Math.random() * unownedDirections.size()));
                        moves.add(new Move(new Point(attackingNode.getX(), attackingNode.getY()), Action.SPREADLINE, randomDirection));
                    }
                }
            }

            //for every other owned node, empower if too much power, potentially spread if enough power.
            for (Node myNode : myNodes) {
                if (myNode.getPower() >= 50 && myNodes.size() > 1) {
                    //empower
                    moves.add(new Move(new Point(myNode.getX(), myNode.getY()), Action.EMPOWER, MoveDirection.CENTRAL));
                } else if (myNode.getPower() >= 10) {

                    //spread
                    List<MoveDirection> unownedDirections = new ArrayList<>();
                    for (int i = 1; i < 7; i++) {
                        Point directionPoint = directions[i].getLocation(new Point(myNode.getX(), myNode.getY()));

                        if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                            continue;
                        }

                        Node adjNode = map.getNode(directionPoint.x, directionPoint.y);
                        if (adjNode.getOwnerId() != id) {
                            unownedDirections.add(directions[i]);
                        }
                    }

                    if (unownedDirections.size() > 0) {
                        //spread in a random direction, power was already checked previously
                        MoveDirection randomDirection = unownedDirections.get((int) (Math.random() * unownedDirections.size()));
                        moves.add(new Move(new Point(myNode.getX(), myNode.getY()), Action.SPREAD, randomDirection));
                    }

                }
            }

        } catch (Exception ex) {
            Logger.getLogger(GuardianBot.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new MoveList(moves);
    }

}
