package entity;

public class Settings 
{
	
    int playSpeed = 300; //Milliseconds per turn
       
    public Settings() {
    }

    public int getPlaySpeed() {
		return playSpeed;
	}
    
    public void setPlaySpeed(int playSpeed) {
		this.playSpeed = playSpeed;
	}
}
