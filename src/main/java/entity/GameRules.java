//http://stackoverflow.com/questions/12669497/using-enum-as-key-for-map
package entity;

import java.util.EnumMap;
import java.util.Map;


/**
 * Contains the selected rules of a single match.
 * not to be confused with Settings, which contains global settings and rules.
 * @author dion
 */
public class GameRules {

    private Map<Action, Integer> actionCosts = new EnumMap<>(Action.class);
    double fluctuation = 0.5;
    
    public GameRules(Settings settings) {
        
        this.fluctuation = settings.getGameRuleFluctuation();
        
        actionCosts.put(Action.SLEEP, 0);
        actionCosts.put(Action.SPREAD, fluctuate(settings.getDefaultActionCosts().get(Action.SPREAD)));
        actionCosts.put(Action.SPREADALL, fluctuate(settings.getDefaultActionCosts().get(Action.SPREADALL)));
        actionCosts.put(Action.SPREADLINE, fluctuate(settings.getDefaultActionCosts().get(Action.SPREADLINE)));
        actionCosts.put(Action.EMPOWER, fluctuate(settings.getDefaultActionCosts().get(Action.EMPOWER)));
        actionCosts.put(Action.DISCHARGE, fluctuate(settings.getDefaultActionCosts().get(Action.DISCHARGE)));
        actionCosts.put(Action.POWERLINE, fluctuate(settings.getDefaultActionCosts().get(Action.POWERLINE)));
        actionCosts.put(Action.OVERCLOCK, fluctuate(settings.getDefaultActionCosts().get(Action.OVERCLOCK)));
        actionCosts.put(Action.GUARD, fluctuate(settings.getDefaultActionCosts().get(Action.GUARD)));
        actionCosts.put(Action.STORAGE, fluctuate(settings.getDefaultActionCosts().get(Action.STORAGE)));
        actionCosts.put(Action.DRAIN, fluctuate(settings.getDefaultActionCosts().get(Action.DRAIN)));
        actionCosts.put(Action.EXPLODE, fluctuate(settings.getDefaultActionCosts().get(Action.EXPLODE)));
    }

    /**
     * Randomly adjusts the given number up or down depending on the fluctuation amount.
     * @param i
     * @return i (fluctuated)
     */
    private int fluctuate(int i)
    {
        i = (int)(i * (Math.random()*fluctuation*2+1-fluctuation));
        if (i > 100) i = 100;
        return i;
    }
    
    
    public Map<Action, Integer> getActionCosts() {
        return actionCosts;
    }
    
}
