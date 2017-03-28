package app.util;

import app.exception.NotANumberException;
import app.exception.NotFoundException;
import com.google.common.collect.Iterables;

/**
 * Utility class that wraps common methods for the purpose of avoiding
 * repetitive exception replacement. The custom exceptions are annotated to give
 * more appropriate HTTP errors.
 *
 * @author dion
 */
public final class Validate {

   
    /**
     * Private constructor of utility class. 
     * This makes it impossible to instantiate this class.
     */
    private Validate() {
    }

    /**
     * Converts a String to an int. Replaces the NumberFormatException with a
     * custom exception. (HTTP 500:Internal Server error -> 400:Bad Request)
     *
     * @param s
     * @return s (int)
     * @throws NotANumberException when s contains characters that cannot be
     * converted.
     */
    public static int parseInt(String s) throws NotANumberException {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new NotANumberException(e);
        }
        return i;
    }

    /**
     * Converts a String to an int. Replaces the NumberFormatException with a
     * custom exception. (HTTP 500:Internal Server error -> 400:Bad Request)
     *
     * @param s
     * @return s (int)
     * @throws NotANumberException when s contains characters that cannot be
     * converted.
     */
    public static long parseLong(String s) throws NotANumberException {
        long i;
        try {
            i = Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new NotANumberException(e);
        }
        return i;
    }
    
    /**
     * Wrapper method that throws correct HTTP exception when a repo couldn't 
     * find a stored value. (HTTP 404:Not Found)
     * @param <T>
     * @param object
     * @return
     * @throws NotFoundException
     */
    public static <T extends Object> T notNull(T object) throws NotFoundException
    {
        if (object == null) throw new NotFoundException();
        return object;
    }
    
    /**
     * Wrapper method that throws correct HTTP exception when a repo returns
     * an empty list. (HTTP 404:Not Found)
     * @param objects
     * @return
     * @throws NotFoundException
     */
    public static Iterable notEmpty(Iterable objects) throws NotFoundException {
        if (Iterables.size(objects) == 0)
            throw new NotFoundException();
        return objects;
    }

}
