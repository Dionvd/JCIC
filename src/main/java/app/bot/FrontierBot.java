package app.bot;

import static app.bot.GuardianBot.id;
import app.dto.Move;
import app.dto.MoveList;
import app.entity.Node;
import app.entity.RoundMap;
import app.enums.Action;
import app.enums.MoveDirection;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Expands to safety and tries to overwhelm enemies by sheer force. ID 5 -Uses
 * Spread, towards the safest neutral area. -Uses Overclock on nodes 3 tiles
 * away from enemies. -Uses Spread, to take over adjacent enemies. -Uses
 * Overclock on nodes with full power. -Uses Empower on overclocked nodes that
 * cannot expand.
 *
 * @author dion
 */
public class FrontierBot {

    final static long id = 5;

    private FrontierBot() {

    }

    public static MoveList calculateMoves(RoundMap map) {
        List<Move> moves = new ArrayList<>();

        try {
            //TODO prioritize safer expansions
            int width = map.getWidth();
            int height = map.getHeight();
            MoveDirection[] directions = MoveDirection.values();
            ArrayList<Node> myNodes = new ArrayList<>();
            ArrayList<Node> enemyNodes = new ArrayList<>();
            ArrayList<Node> frontierNodes = new ArrayList<>();
            ArrayList<Node> frontier2Nodes = new ArrayList<>();

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

            //for every friendly node, check if it is adjacent to an enemy or neutral node.
            for (Node myNode : new ArrayList<>(myNodes)) {

                boolean adjacentEnemy = false;
                boolean adjacentNeutral = false;

                for (int i = 1; i < 7; i++) {

                    Point directionPoint = directions[i].getLocation(new Point(myNode.getX(), myNode.getY()));

                    if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                        continue;
                    }

                    Node adjNode = map.getNode(directionPoint.x, directionPoint.y);
                    if (adjNode.getOwnerId() != 0 && adjNode.getOwnerId() != id) {
                        adjacentEnemy = true;
                    } else if (adjNode.getOwnerId() == 0) {
                        adjacentNeutral = true;
                    }
                }

                if (adjacentEnemy || adjacentNeutral) {
                    frontierNodes.add(myNode);
                    myNodes.remove(myNode);
                }
            }

            //define second line frontier nodes
            for (Node frontierNode : frontierNodes) {

                for (int i = 1; i < 7; i++) {

                    Point directionPoint = directions[i].getLocation(new Point(frontierNode.getX(), frontierNode.getY()));

                    if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                        continue;
                    }

                    Node adjNode = map.getNode(directionPoint.x, directionPoint.y);

                    if (adjNode.getOwnerId() == id && frontierNodes.contains(adjNode) == false) {
                        if (frontier2Nodes.contains(adjNode) == false) {
                            frontier2Nodes.add(adjNode);
                            myNodes.remove(adjNode);
                        }
                    }
                }
            }

            //for all remaining nodes, see if they can be overclocked, otherwise uses empower
            for (Node myNode : myNodes) {
                if (myNode.getType() != 2) {
                    if (myNode.getPower() >= 70) {
                        moves.add(new Move(new Point(myNode.getX(), myNode.getY()), Action.OVERCLOCK, MoveDirection.CENTRAL));
                    }
                } else {
                    if (myNode.getPower() >= 10) {
                        moves.add(new Move(new Point(myNode.getX(), myNode.getY()), Action.EMPOWER, MoveDirection.CENTRAL));
                    }
                }
            }

            //use empower with second frontier nodes, but save power to spread back in case of a hostile take over.
            for (Node frontier2Node : frontier2Nodes) {
                if (frontier2Node.getPower() >= 20) {
                    moves.add(new Move(new Point(frontier2Node.getX(), frontier2Node.getY()), Action.EMPOWER, MoveDirection.CENTRAL));
                }
            }

            //use spread on the front
            for (Node frontierNode : frontierNodes) {

                if (frontierNode.getPower() < 10) continue;
                
                List<MoveDirection> unownedDirections = new ArrayList<>();
                List<MoveDirection> enemyDirections = new ArrayList<>();

                for (int i = 1; i < 7; i++) {

                    Point directionPoint = directions[i].getLocation(new Point(frontierNode.getX(), frontierNode.getY()));

                    if (directionPoint.x == -1 || directionPoint.y == -1 || directionPoint.x >= width || directionPoint.y >= height) {
                        continue;
                    }

                    Node adjNode = map.getNode(directionPoint.x, directionPoint.y);

                    if (adjNode.getOwnerId() == 0) {
                        unownedDirections.add(directions[i]);
                    } else if (adjNode.getOwnerId() != id) {
                        enemyDirections.add(directions[i]);
                    }
                }
                
                //spread in a random direction, power was already checked previously
                if (enemyDirections.size() > 0)
                {
                    MoveDirection randomDirection = enemyDirections.get((int) (Math.random() * unownedDirections.size()));
                    moves.add(new Move(new Point(frontierNode.getX(), frontierNode.getY()), Action.SPREAD, randomDirection));
                }
                else if (unownedDirections.size() > 0)
                {
                    MoveDirection randomDirection = unownedDirections.get((int) (Math.random() * unownedDirections.size()));
                    moves.add(new Move(new Point(frontierNode.getX(), frontierNode.getY()), Action.SPREAD, randomDirection));
                }

            }

        } catch (Exception ex) {
        }

        return new MoveList(moves);
    }

}
