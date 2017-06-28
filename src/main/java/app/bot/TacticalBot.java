package app.bot;

import app.dto.MoveList;
import app.entity.RoundMap;

/**
 * Lures the enemy in, then explodes them.
 * ID 4
 * -Uses Spread and SpreadLine to take neutral territory.
 * -Uses Discharge to spread power around when full on power.
 * -Uses Guard on one third of its border.
 * -Uses Explode to attack players when the damage dealt is twice as much as damage taken.
 * @author dion
 */
public class TacticalBot {

    final static long id = 4;
    
    private TacticalBot()
    {
        
    }
    
    public static MoveList calculateMoves(RoundMap map) {
        MoveList moves = new MoveList();
        
        return moves;
    }

    
}
