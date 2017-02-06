package entity;

/**
 * The JsonWrapper class is very simple class to store a variable for the purpose of showing it in JSON format.
 * A single string or integer is not returned as JSON when JSON is requested, but if you use this wrapper it will be return in JSON as "{"value": x}".
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
