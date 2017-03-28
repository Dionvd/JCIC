//http://javarevisited.blogspot.nl/2011/08/enum-in-java-example-tutorial.html
//http://stackoverflow.com/questions/5878952/cast-int-to-enum-in-java
package app.enums;

/**
 * The action that one of your nodes could take. A node can perform an action by
 * sending the service your list of moves. Actions are sometimes identified by
 * their Enum Ordinal (index of an enum). Therefor the order should remain the
 * same.
 *
 * @author dion
 */
public enum Action {

    /**
     * 0 : Do nothing.
     */
    SLEEP,
    /**
     * 1 : Spreads to 1 adjacent node.
     */
    SPREAD,
    /**
     * 2 : Spreads to all adjacent nodes.
     */
    SPREADALL,
    /**
     * 3 : Spreads to 5 nodes in a straight line from this node.
     */
    SPREADLINE,
    /**
     * 4 : Gives 5% power to adjacent node with lowest power.
     */
    EMPOWER,
    /**
     * 5 : Splits remaining power with adjacent node and sells special type.
     */
    DISCHARGE,
    /**
     * 6 : Nodes can consume the power from adjacent or connected powerline nodes.
     */
    POWERLINE,
    /**
     * 7 : This node becomes overclocked, an overclocked node generates power 3x as
     * fast.
     */
    OVERCLOCK,
    /**
     * 8 : A guarded node is protected against 1 takeover and blocks line spreads.
     */
    GUARD,
    /**
     * 9 : This node can store 3x as much power.
     */
    STORAGE,
    /**
     * 10 : Consumes 5 power from an adjacent enemy node.
     */
    DRAIN,
    /**
     * 11 : Neutralizes all nodes within two tiles of this node, including your own.
     */
    EXPLODE;

}
