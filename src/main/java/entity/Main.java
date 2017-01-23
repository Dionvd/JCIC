package entity;


import java.util.ArrayList;

public class Main 
{
    public static Main self;

    ArrayList<Game> games;
    ArrayList<Player> players;
    WaitingQueue waitingQueue;
    Settings settings;
       
    public Main() {
    	games = new ArrayList<>();
    	players = new ArrayList<>();
    	waitingQueue = new WaitingQueue();
    	settings = new Settings();
    }
    
    public void MockData()
    {
    	Player player = new Player();
    	player.setUsername("John");
    	player.setPassword("password");
    	player.setPlayerId(12345);
    	player.setSessionId(11111111);
    	player.setWinCount(3);
    	players.add(player);

    	Player player2 = new Player();
    	player2.setUsername("Jake");
    	player2.setPassword("password");
    	player2.setPlayerId(23456);
    	player2.setSessionId(22222222);
    	player2.setWinCount(1);
    	players.add(player2);
    	
    	Game game = new Game();
    	game.setMap(new Map());
    	game.getMap().setWidth(10);
    	game.getMap().setHeight(10);
    	
    	
    	ArrayList<Node> nodes = new ArrayList<>();
    	
    	Node startNode = new Node();
    	startNode.ownerId = 12345;
    	startNode.power = 50;
    	nodes.add(startNode);
    	
    	for (int i = 0; i < 98; i++) {
    		nodes.add(new Node());
		}
    	
    	Node startNode2 = new Node();
    	startNode2.ownerId = 23456;
    	startNode2.power = 50;
    	nodes.add(startNode2);
    	
    	
    	game.getMap().setNodes(nodes);
    	game.getPlayerIds().add(12345);
    	games.add(game);
    	
    }
    

    public ArrayList<Game> getGames() {
            return games;
    }

    public void setGames(ArrayList<Game> games) {
            this.games = games;
    }

    public ArrayList<Player> getPlayers() {
            return players;
    }

    public void setPlayers(ArrayList<Player> players) {
            this.players = players;
    }

    public WaitingQueue getWaitingQueue() {
            return waitingQueue;
    }

    public void setWaitingQueue(WaitingQueue waitingQueue) {
            this.waitingQueue = waitingQueue;
    }

    public Settings getSettings() {
            return settings;
    }

    public void setSettings(Settings settings) {
            this.settings = settings;
    }

    
    
}
