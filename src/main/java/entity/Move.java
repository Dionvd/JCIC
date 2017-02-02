package entity;

/**
 * A move contains the data allowing for actions to be performed by a node. 
 * Players send a list of moves during each turn.
 * The node itself is not sent, only it's coordinates.
 * @author dion
 */
public class Move {
    private int x;
    private int y;
    private Action action;
    private int direction;
}
