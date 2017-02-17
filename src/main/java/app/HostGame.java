package app;

import entity.Settings;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for Hosting and progressing the game.
 * @author dion
 */
public class HostGame {
    
   private HostGame(Settings settings)
   {
   }
    
    public static void run()
    {
        while (true)
        {
            try {
                System.out.println("Game is running...");
                sleep(300);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HostGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
