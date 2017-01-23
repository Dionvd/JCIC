package entity;

public class Player 
{
	
    int playerId;
    String username;
    String password;
    int sessionId;
    int winCount;
    
       
    public Player() {
    }
 
    
    public int getPlayerId() {
		return playerId;
	}


	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getSessionId() {
		return sessionId;
	}


	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}


	public int getWinCount() {
		return winCount;
	}


	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

    
}
