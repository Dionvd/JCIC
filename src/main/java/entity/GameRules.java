//http://stackoverflow.com/questions/12669497/using-enum-as-key-for-map
package entity;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author dion
 */
public class GameRules {

    private Map<Action, Integer> costs = new EnumMap<>(Action.class);

    private Map<Action, String> descriptions = new EnumMap<>(Action.class);
    
    /**
     *
     */
    public GameRules() {
        costs.put(Action.SLEEP, 0);
        costs.put(Action.SPREAD, 10);
        costs.put(Action.SPREADALL, 40);
        costs.put(Action.SPREADLINE, 60);
        costs.put(Action.EMPOWER, 10);
        costs.put(Action.DISCHARGE, 30);
        costs.put(Action.POWERLINE, 50);
        costs.put(Action.OVERCLOCK, 70);
        costs.put(Action.GUARD, 30);
        costs.put(Action.STORAGE, 20);
        costs.put(Action.DRAIN, 5);
        costs.put(Action.EXPLODE, 80);
        
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
        
    }

    /**
     *
     * @return
     */
    public Map<Action, Integer> getCosts() {
        return costs;
    }

    /**
     *
     * @return
     */
    public Map<Action, String> getDescriptions() {
        return descriptions;
    }

    
}
