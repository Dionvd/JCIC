package app.object;

/**
 * A move contains the data allowing for actions to be performed by a node.
 * Players send a list of moves during each turn. The node itself is not a sub
 * object, only it's coordinates are sent.
 *
 * @author dion
 */
public class Move {

    private int x;
    private int y;
    private Action action;
    private int direction;

    /**
     * Empty constructor. Do not use.
     */
    @Deprecated
    public Move() {
    }
            
    
    /**
     * Default constructor.
     * @param x
     * @param y
     * @param action
     * @param direction
     */
    public Move(int x, int y, Action action, int direction) {
        this.x = x;
        this.y = y;
        this.action = action;
        this.direction = direction;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Action getAction() {
        return action;
    }
    
    /**
     * Sets the action by giving its ordinal. 
     * The ordinal is the index of an enum value as it appears in the class.
     * @param action
     */
    public void setAction(int action) {
        this.action = Action.values()[action];
    }
    
    public void setAction(Action action) {
        this.action = action;
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


    public void setDirection(int direction) {
        this.direction = direction;
    }

}
