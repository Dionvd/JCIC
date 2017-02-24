//http://javarevisited.blogspot.nl/2011/08/enum-in-java-example-tutorial.html
package app.entity;

/**
 * The action that one of your nodes could take.
 * A node can perform an action by sending the service your list of moves. 
 * @author dion
 */
public enum Action {

    /**
     * Do nothing.
     */
    SLEEP,      

    /**
     * Spreads to 1 adjacent node.
     */
    SPREAD,     

    /**
     * Spreads to all adjacent nodes.
     */
    SPREADALL,  

    /**
     * Spreads to 5 nodes in a straight line from this node.
     */
    SPREADLINE, 

    /**
     * Gives 5% power to adjacent node with lowest power.
     */
    EMPOWER,    

    /**
     * Splits remaining power with adjacent node and sells special type.
     */
    DISCHARGE,  

    /**
     * Nodes can consume the power from adjacent or connected powerline nodes. 
     */
    POWERLINE,  

    /**
     * This node becomes overclocked, an overclocked node generates power 3x as fast.
     */
    OVERCLOCK,  

    /**
     * A guarded node is protected against 1 takeover and blocks line spreads. 
     */
    GUARD,        

    /**
     * This node can store 3x as much energy.
     */
    STORAGE,    

    /**
     * Consumes 5 power from an adjacent enemy node.
     */
    DRAIN,      

    /**
     * Neutralizes all nodes within two tiles of this node, including your own. 
     */
    EXPLODE;    

}
