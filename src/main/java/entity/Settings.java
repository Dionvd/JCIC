package entity;

/**
 *
 * @author dion
 */
public class Settings {

    /**
     *
     */
    public final Boolean mockData = true;
    private int playSpeed = 300; //Milliseconds per turn

    /**
     *
     */
    public Settings() {
    }

    /**
     *
     * @return
     */
    public int getPlaySpeed() {
        return playSpeed;
    }

    /**
     *
     * @param playSpeed
     */
    public void setPlaySpeed(int playSpeed) {
        this.playSpeed = playSpeed;
    }

}
