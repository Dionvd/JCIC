//http://stackoverflow.com/questions/335311/static-initializer-in-java
package entity;

import exceptions.NotFoundException;
import java.util.ArrayList;

/**
 * Highest hierarchy entity class that stores the other entities as objects. 
 * @author dion
 */
public class Main {

    /**
     * Main.self is used to reference the game data from the web services.
     * Main.self is automatically generated with the static constructor. 
     * main.self should not be referenced anywhere else besides the web service resources.
     */
    public static Main self;

    ArrayList<Game> games;
    ArrayList<Player> players;
    WaitingQueue waitingQueue;
    Settings settings;

    /**
     *  Main constructor
     */
    public Main() {
        games = new ArrayList<>();
        players = new ArrayList<>();
        waitingQueue = new WaitingQueue();
        settings = new Settings();
    }

    /**
     *  Static constructor that generates an instance of itself.
     *  Mocks data if settings.mockData is true.
     */
    static 
    {
        Main.self = new Main();

        if (Main.self.settings.mockData) {
            Player player = new Player("John", "password");
            player.setSessionId(11111111);
            player.setWinCount(3);
            Main.self.players.add(player);

            Player player2 = new Player("Jake", "password");
            player2.setSessionId(22222222);
            player2.setWinCount(1);
            Main.self.players.add(player2);

            Player player3 = new Player("Paul", "password");
            player3.setSessionId(33333333);
            player3.setWinCount(1);
            Main.self.players.add(player3);

            Player player4 = new Player("Terrance", "password");
            player4.setSessionId(44444444);
            player4.setWinCount(7);
            Main.self.players.add(player4);

            Player player5 = new Player("Phil", "password");
            player5.setSessionId(55555555);
            player5.setWinCount(0);
            Main.self.players.add(player5);

            Game game = new Game();
            game.setMap(new GameMap(10, 10));

            Main.self.waitingQueue.setMaxCount(50);
            Main.self.waitingQueue.getPlayerIds().add(1002);
            Main.self.waitingQueue.getPlayerIds().add(1003);
            Main.self.waitingQueue.getPlayerIds().add(1004);

          
            Node startNode = game.getMap().getNode(0, 0);
            startNode.setOwnerId(1000);
            startNode.setPower(50);

            Node startNode2 = game.getMap().getNode(9, 9);
            startNode2.setOwnerId(1001);
            startNode2.setPower(50);

            game.getPlayerIds().add(1000);            
            game.getPlayerIds().add(1001);

            Main.self.games.add(game);
        }
    }

    
    /**
     * 
     * @param id
     * @return Game with matching id
     * @throws NotFoundException occurs when no id exists (error 404).
     */
    public Game getGameById(int id) {
        for (Game g : games) {
            if (g.getId() == id) {
                return g;
            }
        }
        throw new NotFoundException();
    }

    /**
     * @param id
     * @return player matching id
     * @throws NotFoundException occurs when no id exists (error 404).
     */
    public Player getPlayerById(int id) {
        for (Player p : players) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new NotFoundException();
    }
    
    public ArrayList<Game> getGames() {
        return games;
    }

    public Game getGame(int i) {
        return games.get(i);
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
