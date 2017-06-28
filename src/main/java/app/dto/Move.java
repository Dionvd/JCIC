package app.dto;

import app.enums.Action;
import app.enums.MoveDirection;
import java.awt.Point;
import java.io.Serializable;

/**
 * A move contains the data allowing for actions to be performed by a node.
 * Players send a list of moves during each turn. The node itself is not a sub
 * object, only it's coordinates are sent.
 *
 * @author dion
 */
public class Move implements Serializable {

    private Point location;
    private Action action;
    private MoveDirection direction;

    /**
     * Empty constructor. Do not use.
     */
    @Deprecated
    public Move() {
        location = new Point();
        action = Action.SLEEP;
        direction = MoveDirection.CENTRAL;
    }

    /**
     * Default constructor.
     *
     * @param location
     * @param action
     * @param direction
     */
    public Move(Point location, Action action, MoveDirection direction) {
        
        this();

        if (location != null) {
            this.location = location;
        }
        if (action != null) {
            this.action = action;
        }
        if (direction != null) {
            this.direction = direction;
        }
    }

    public int getAction() {
        return action.ordinal();
    }

    /**
     * Sets the action by giving its ordinal. The ordinal is the index of an
     * enum value as it appears in the class.
     *
     * @param action
     */
    public void setAction(int action) {
        this.action = Action.values()[action];
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getDirection() {
        return direction.ordinal();
    }

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }

    public void setDirection(int direction) {
        this.direction = MoveDirection.values()[direction];
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    public void setX(int x) {
        location.x = x;
    }

    public void setY(int y) {
        location.y = y;
    }
}
