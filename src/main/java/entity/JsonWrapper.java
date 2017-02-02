package entity;

/**
 *
 * @author dion
 */
public class JsonWrapper {

    private Object value;

    /**
     *
     * @param obj
     */
    public JsonWrapper(Object obj) {
        value = obj;
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
