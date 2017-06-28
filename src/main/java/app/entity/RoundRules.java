//http://stackoverflow.com/questions/12669497/using-enum-as-key-for-map
package app.entity;

import app.enums.Action;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * RoundRules contains the selected rules of a single round. RoundRules is a sub
 * object of Round. Not to be confused with the Settings class, which contains
 * global settings and rules. Many game rules are derived from the Settings
 * class.
 *
 * @author dion
 */
@Entity
public class RoundRules implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    static final int MAX_POWER = 100;

    
    @ElementCollection
    private Map<Action, Integer> actionCosts = new EnumMap<>(Action.class);
    
    /**
     * Entity constructor. Do not use.
     */
    @Deprecated
    public RoundRules() {
    }

    /**
     * Default constructor.
     * @param settings
     */
    public RoundRules(Settings settings) {

        actionCosts.put(Action.SLEEP, 0);
        actionCosts.put(Action.SPREAD, settings.getDefaultActionCosts().get(Action.SPREAD));
        actionCosts.put(Action.SPREADALL, settings.getDefaultActionCosts().get(Action.SPREADALL));
        actionCosts.put(Action.SPREADLINE, settings.getDefaultActionCosts().get(Action.SPREADLINE));
        actionCosts.put(Action.EMPOWER, settings.getDefaultActionCosts().get(Action.EMPOWER));
        actionCosts.put(Action.DISCHARGE, settings.getDefaultActionCosts().get(Action.DISCHARGE));
        actionCosts.put(Action.POWERLINE, settings.getDefaultActionCosts().get(Action.POWERLINE));
        actionCosts.put(Action.OVERCLOCK, settings.getDefaultActionCosts().get(Action.OVERCLOCK));
        actionCosts.put(Action.GUARD, settings.getDefaultActionCosts().get(Action.GUARD));
        actionCosts.put(Action.STORAGE, settings.getDefaultActionCosts().get(Action.STORAGE));
        actionCosts.put(Action.DRAIN, settings.getDefaultActionCosts().get(Action.DRAIN));
        actionCosts.put(Action.EXPLODE, settings.getDefaultActionCosts().get(Action.EXPLODE));
    }


    public Map<Action, Integer> getActionCosts() {
        return actionCosts;
    }

    public void setActionCosts(Map<Action, Integer> actionCosts) {
        this.actionCosts = actionCosts;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
