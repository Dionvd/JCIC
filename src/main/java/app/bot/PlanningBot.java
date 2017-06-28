package app.bot;

import app.dto.MoveList;
import app.entity.RoundMap;

/**
 * Stores up power for a single breakthrough attack.
 * ID 6
 * -Uses Spread to gain neutral territory.
 * -Uses Storage on nodes with full power.
 * -Uses Spread from Storage nodes whenever possible.
 * -Uses Discharge on all Storage nodes near an enemy player when one is full on power.
 * -Attacks enemy players with SpreadAll or Spread when enough energy for SpreadAll.
 * @author dion
 */
public class PlanningBot {

    final static long id = 6;
    
    private PlanningBot()
    {
        
    }
    
    public static MoveList calculateMoves(RoundMap map) {
        MoveList moves = new MoveList();
        
        
        
        return moves;
    }

    
}
