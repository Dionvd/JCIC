package entity;


import java.util.EnumMap;
import java.util.Map;

/**
 * Global Settings of all matches. 
 * Can be configured in the admin panel.

 * @author dion
 */
public class Settings {


   
    public final Boolean mockData = true;
    
    //Milliseconds per turn
    private int playSpeed = 300;

    private Map<Action, String> descriptions = new EnumMap<>(Action.class);
    private Map<Action, Integer> defaultActionCosts = new EnumMap<>(Action.class);
    
    private double gameRuleFluctuation = 0.2;
    
    
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
        descriptions.put(Action.DRAIN, "Consumes 5 power from an adjacent enemy node. ");
        descriptions.put(Action.EXPLODE, "This node and all other nodes within two hexes are neutralized. (Including your own).");
        
        defaultActionCosts.put(Action.SLEEP, 0);
        defaultActionCosts.put(Action.SPREAD, 10);
        defaultActionCosts.put(Action.SPREADALL, 40);
        defaultActionCosts.put(Action.SPREADLINE, 60);
        defaultActionCosts.put(Action.EMPOWER, 10);
        defaultActionCosts.put(Action.DISCHARGE, 30);
        defaultActionCosts.put(Action.POWERLINE, 50);
        defaultActionCosts.put(Action.OVERCLOCK, 70);
        defaultActionCosts.put(Action.GUARD, 30);
        defaultActionCosts.put(Action.STORAGE, 20);
        defaultActionCosts.put(Action.DRAIN, 5);
        defaultActionCosts.put(Action.EXPLODE, 80);
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

    public double getGameRuleFluctuation() {
        return gameRuleFluctuation;
    }

    public void setGameRuleFluctuation(double gameRuleFluctuation) {
        this.gameRuleFluctuation = gameRuleFluctuation;
    }
    

}
