package app.entity;


import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Global Settings of all matches. 
 * Can be configured in the admin panel.

 * @author dion
 */
@Entity
public class Settings implements Serializable {

    
    
    static final boolean MOCKDATA = true;
    
    static final int DEFAULT_COST_SPREAD = 10;
    static final int DEFAULT_COST_SPREADALL = 40;
    static final int DEFAULT_COST_SPREADLINE = 60;
    static final int DEFAULT_COST_EMPOWER = 10;
    static final int DEFAULT_COST_DISCHARGE = 30;
    static final int DEFAULT_COST_POWERLINE = 50;
    static final int DEFAULT_COST_OVERCLOCK = 70;
    static final int DEFAULT_COST_GUARD = 30;
    static final int DEFAULT_COST_STORAGE = 20;
    static final int DEFAULT_COST_DRAIN = 5;
    static final int DEFAULT_COST_EXPLODE = 80;
    
    
    //Milliseconds per turn
    private int playSpeed = 300;

    
    @ElementCollection
    private Map<Action, String> descriptions = new EnumMap<>(Action.class);

    @ElementCollection
    private Map<Action, Integer> defaultActionCosts = new EnumMap<>(Action.class);
    
    private double gameRuleFluctuation = 0.2;
  
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    /**
     * Default constructor
     * Initializes action costs and descriptions.
     */
    public Settings() {
        
        descriptions.put(Action.SLEEP, "Do nothing. (default)");
        descriptions.put(Action.SPREAD, "Spreads to 1 adjacent node.");
        descriptions.put(Action.SPREADALL, "Spreads to all adjacent nodes.");
        descriptions.put(Action.SPREADLINE, "Spreads to 5 nodes in a straight line from this node.");
        descriptions.put(Action.EMPOWER, "The node that is adjacent with the least power gains 5% power.");
        descriptions.put(Action.DISCHARGE, "All remaining power of this node is split evenly among adjacent nodes. It loses any special type it might have, half this type’s cost is returned as power.");
        descriptions.put(Action.POWERLINE, "Changes this node in to a powerline node (type=1). Nodes can consume power from any adjacent or connected powerline nodes when they have not enough power on their own.");
        descriptions.put(Action.OVERCLOCK, "Changes this node in to an overclocked node (type=2). An overclocked node permanently generates power three times as fast.");
        descriptions.put(Action.GUARD, "Changes this node to a guarded node (type=3). A guarded node is protected against 1 takeover, in which case it becomes a normal node again.");
        descriptions.put(Action.STORAGE, "Changes this node to a storage node (type=4). A storage node can store up to 300% energy.");
        descriptions.put(Action.DRAIN, "Consumes 5 power from an adjacent enemy node.");
        descriptions.put(Action.EXPLODE, "This node and all other nodes within two hexes are neutralized. (Including your own).");
        
        defaultActionCosts.put(Action.SLEEP, 0);
        defaultActionCosts.put(Action.SPREAD, DEFAULT_COST_SPREAD);
        defaultActionCosts.put(Action.SPREADALL, DEFAULT_COST_SPREADALL);
        defaultActionCosts.put(Action.SPREADLINE, DEFAULT_COST_SPREADLINE);
        defaultActionCosts.put(Action.EMPOWER, DEFAULT_COST_EMPOWER);
        defaultActionCosts.put(Action.DISCHARGE, DEFAULT_COST_DISCHARGE);
        defaultActionCosts.put(Action.POWERLINE, DEFAULT_COST_POWERLINE);
        defaultActionCosts.put(Action.OVERCLOCK, DEFAULT_COST_OVERCLOCK);
        defaultActionCosts.put(Action.GUARD, DEFAULT_COST_GUARD);
        defaultActionCosts.put(Action.STORAGE, DEFAULT_COST_STORAGE);
        defaultActionCosts.put(Action.DRAIN, DEFAULT_COST_DRAIN);
        defaultActionCosts.put(Action.EXPLODE, DEFAULT_COST_EXPLODE);
    }


    public int getPlaySpeed() {
        return playSpeed;
    }

    public void setPlaySpeed(int playSpeed) {
        this.playSpeed = playSpeed;
    }


    public Map<Action, String> getDescriptions() {
        return descriptions;
    }

    public Map<Action, Integer> getDefaultActionCosts() {
        return defaultActionCosts;
    }

    public void setDefaultActionCosts(Map<Action, Integer> defaultActionCosts) {
        this.defaultActionCosts = defaultActionCosts;
    }

    public double getMatchRuleFluctuation() {
        return gameRuleFluctuation;
    }

    public void setMatchRuleFluctuation(double gameRuleFluctuation) {
        this.gameRuleFluctuation = gameRuleFluctuation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

}
