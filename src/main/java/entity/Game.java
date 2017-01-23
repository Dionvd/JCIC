package entity;

import java.util.ArrayList;

public class Game 
{
 
    ArrayList<Integer> playerIds;
    Map map;   
    GameRules gameRules;
    int turn;
    
    public Game() {
    	playerIds = new ArrayList<>();
    	map = new Map();
    	gameRules = new GameRules();
        turn = 0;
    }

   
    public ArrayList<Integer> getPlayerIds() {
            return playerIds;
    }

    public void setPlayerIds(ArrayList<Integer> playerIds) {
            this.playerIds = playerIds;
    }

    public int getPlayerCount() {
            return playerIds.size();
    }


    public Map getMap() {
            return map;
    }


    public void setMap(Map map) {
            this.map = map;
    }

    public GameRules getGameRules() {
            return gameRules;
    }    

    public void setGameRules(GameRules gameRules) {
            this.gameRules = gameRules;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    
}