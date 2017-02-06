
package rest;

import exceptions.NotANumberException;

/**
 * Class that wraps common methods for the purpose of avoiding repetitive exception replacement.
 * The custom exceptions are annotated to give more appropriate HTTP errors.
 * @author dion
 */
public class ResourceMethods {
    
    /**
     * Converts a String to an int.
     * Replaces the NumberFormatException with a custom exception. (HTTP 500:Internal Server error -> 400:Bad Request)
     * @param s
     * @return s (int)
     * @throws NotANumberException when s contains characters that cannot be converted.
     */
    public static int parseInt (String s)
    {
        int i;
         try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new NotANumberException(e.getMessage());
        }
        return i;
    }
}
