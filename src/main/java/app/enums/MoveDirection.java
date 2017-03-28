package app.enums;

import java.awt.Point;

/**
 *   1 2
 *  3 0 4
 *   5 6
 * @author dion
 */
public enum MoveDirection {

    /**
     * Enum Ordinal 0: 
     * X, Y
     */
    CENTRAL,

    /**
     * Enum Ordinal 1:
     * Even Y  : X-1, Y-1
     * Uneven Y: X  , Y-1
     */
    NORTH_WEST,

    /**
     * Enum Ordinal 2:
     * Even Y  : X  , Y-1
     * Uneven Y: X+1, Y-1
     */
    NORTH_EAST,

    /**
     * Enum Ordinal 3:
     * X-1, Y
     */
    WEST,

    /**
     * Enum Ordinal 4:
     * X+1, Y
     */
    EAST,

    /**
     * Enum Ordinal 5:
     * Even Y  : X-1, Y+1;
     * Uneven Y: X  , Y+1
     */
    SOUTH_WEST,

    /**
     * Enum Ordinal 6:
     * Even Y  : X  , Y+1;
     * Uneven Y: X+1, Y+1
     */
    SOUTH_EAST;

    public Point getLocation(Point location)
    {
        int x = location.x;
        int y = location.y;
        
        switch (this)
        {
            case NORTH_WEST:
                if (y%2==0) x--; 
                y--;
                break;
            case NORTH_EAST:
                if (y%2==1) x++;
                y--;
                break;
            case WEST:
                x--;
                break;
            case EAST:
                x++;
                break;
            case SOUTH_WEST:
                if (y%2==0) x--; 
                y++;
                break;
            case SOUTH_EAST:
                if (y%2==1) x++;
                y++;
                break;
        }
        
        return new Point(x,y);
    }
    
}
