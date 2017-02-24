//http://stackoverflow.com/questions/12669497/using-enum-as-key-for-map
package app.entity;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * MatchRules contains the selected rules of a single match. 
 * MatchRules is a sub object of Match.
 * Not to be confused with the Settings class, which contains global settings and rules.
 * Many game rules are derived from the Settings class.
 * @author dion
 */

@Entity
public class MatchRules implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    static final int MAX_POWER = 100;
    
    //private Map<Action, Integer> actionCosts = new EnumMap<>(Action.class);
    private double fluctuation;
    
    public MatchRules()
    {
    }
    
    public MatchRules(Settings settings) {

        this.fluctuation = settings.getMatchRuleFluctuation();

//        actionCosts.put(Action.SLEEP, 0);
//        actionCosts.put(Action.SPREAD, fluctuate(settings.getDefaultActionCosts().get(Action.SPREAD)));
//        actionCosts.put(Action.SPREADALL, fluctuate(settings.getDefaultActionCosts().get(Action.SPREADALL)));
//        actionCosts.put(Action.SPREADLINE, fluctuate(settings.getDefaultActionCosts().get(Action.SPREADLINE)));
//        actionCosts.put(Action.EMPOWER, fluctuate(settings.getDefaultActionCosts().get(Action.EMPOWER)));
//        actionCosts.put(Action.DISCHARGE, fluctuate(settings.getDefaultActionCosts().get(Action.DISCHARGE)));
//        actionCosts.put(Action.POWERLINE, fluctuate(settings.getDefaultActionCosts().get(Action.POWERLINE)));
//        actionCosts.put(Action.OVERCLOCK, fluctuate(settings.getDefaultActionCosts().get(Action.OVERCLOCK)));
//        actionCosts.put(Action.GUARD, fluctuate(settings.getDefaultActionCosts().get(Action.GUARD)));
//        actionCosts.put(Action.STORAGE, fluctuate(settings.getDefaultActionCosts().get(Action.STORAGE)));
//        actionCosts.put(Action.DRAIN, fluctuate(settings.getDefaultActionCosts().get(Action.DRAIN)));
//        actionCosts.put(Action.EXPLODE, fluctuate(settings.getDefaultActionCosts().get(Action.EXPLODE)));
    }

    /**
     * Randomly adjusts the given number up or down depending on the fluctuation
     * amount.
     *
     * @param i
     * @return i (fluctuated)
     */
    private int fluctuate(int i) {
        int fluctI = (int) (i * (Math.random() * fluctuation * 2 + 1 - fluctuation));
        if (fluctI > MAX_POWER) {
            fluctI = MAX_POWER;
        }
        return fluctI;
    }
//
//    public Map<Action, Integer> getActionCosts() {
//        return actionCosts;
//    }

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    

}
