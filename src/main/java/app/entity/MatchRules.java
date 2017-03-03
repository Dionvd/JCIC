//http://stackoverflow.com/questions/12669497/using-enum-as-key-for-map
package app.entity;

import app.object.Action;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * MatchRules contains the selected rules of a single match. MatchRules is a sub
 * object of Match. Not to be confused with the Settings class, which contains
 * global settings and rules. Many game rules are derived from the Settings
 * class.
 *
 * @author dion
 */
@Entity
public class MatchRules implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    static final int MAX_POWER = 100;

    
    @ElementCollection
    private Map<Action, Integer> actionCosts = new EnumMap<>(Action.class);
    
    
    private double fluctuation;

    /**
     * Entity constructor. Do not use.
     */
    @Deprecated
    public MatchRules() {
    }

    /**
     * Default constructor.
     * @param settings
     */
    public MatchRules(Settings settings) {

        this.fluctuation = settings.getMatchRuleFluctuation();

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
     * Randomly adjusts the given number up or down depending on the fluctuation
     * amount.
     *
     * @param i
     * @return i (fluctuated)
     */
    private int fluctuate(int i) {
        int fluct = (int) (i * (Math.random() * fluctuation * 2 + 1 - fluctuation));
        if (fluct > MAX_POWER) {
            fluct = MAX_POWER;
        }
        return fluct;
    }

    public Map<Action, Integer> getActionCosts() {
        return actionCosts;
    }

    public void setActionCosts(Map<Action, Integer> actionCosts) {
        this.actionCosts = actionCosts;
    }

    public double getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(double fluctuation) {
        this.fluctuation = fluctuation;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
