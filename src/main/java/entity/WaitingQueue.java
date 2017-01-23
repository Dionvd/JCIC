package entity;

import java.util.ArrayList;

public class WaitingQueue 
{
	
    int maxCount = 100;
    ArrayList<Integer> playerIds;
       
    public WaitingQueue() {
    	playerIds = new ArrayList<Integer>();
    }

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public ArrayList<Integer> getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(ArrayList<Integer> playerIds) {
		this.playerIds = playerIds;
	}
 
    

    
}
