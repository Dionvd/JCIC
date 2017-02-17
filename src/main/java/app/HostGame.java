package app;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for Hosting and progressing the game.
 * @author dion
 */
public class HostGame {
    
   
    
    public static void run()
    {
        while (true)
        {
            try {
                System.out.println("Game is running...");
                sleep(3000);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HostGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
