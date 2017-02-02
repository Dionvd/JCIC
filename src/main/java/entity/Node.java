package entity;

/**
 *
 * @author dion
 */
public class Node {

    private int power = 0;
    private int ownerId = 0;
    private int type = 0; //0 = normal; 1 = powerline; 2 = overclocked; 3 = guarded; 4 = storage;

    /**
     *
     */
    public Node() {
    }

    /**
     *
     * @return
     */
    public int getPower() {
        return power;
    }

    /**
     *
     * @param power
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     *
     * @return
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @param ownerId
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    
}
