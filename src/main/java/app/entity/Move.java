package app.entity;

/**
 * A move contains the data allowing for actions to be performed by a node. 
 * Players send a list of moves during each turn.
 * The node itself is not a sub object, only it's coordinates are sent.
 * @author dion
 */
public class Move {
    private int x;
    private int y;
    private Action action;
    private int direction;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Action getAction() {
        return action;
    }

    public int getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
}
