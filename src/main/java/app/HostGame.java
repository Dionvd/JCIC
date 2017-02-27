package app;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for handling game rules logic.
 *
 * @author dion
 */
public class HostGame {

    private HostGame() {
    }

    /**
     * Runs the game logic
     */
    public static void run() {
        while (true) {
            try {
                System.out.println("Game is running...");
                sleep(300);

            } catch (InterruptedException ex) {
                Logger.getLogger(HostGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
