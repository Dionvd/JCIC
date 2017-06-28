//https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
package app.util;

import java.security.SecureRandom;

/**
 *
 * @author dion
 */
public class TokenGenerator {
    
    private static final char[] SYMBOLS;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ch++) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            tmp.append(ch);
        }
        SYMBOLS = tmp.toString().toCharArray();
    }   

    private static final SecureRandom RANDOM = new SecureRandom();

    private static char[] buf;

    public static String next() {
        
        buf = new char[24];
        
        for (int i = 0; i < buf.length; i++) {
            buf[i] = SYMBOLS[RANDOM.nextInt(SYMBOLS.length)];
        }
        return new String(buf);
    }
}
