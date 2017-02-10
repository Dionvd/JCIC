//http://stackoverflow.com/questions/335311/static-initializer-in-java
package entity;

import exceptions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Highest hierarchy entity class that stores the other entities as objects.
 *
 * @author dion
 */
public class Main {

    /**
     * Main.self is used to reference the game data from the web services.
     * Main.self is automatically generated with the static constructor.
     * main.self should not be referenced anywhere else besides the web service
     * resources.
     */
    public static Main self;

    private final static int BLOCKED_ON_FAILED_LOGIN_ATTEMPTS = 5;
    private final static int BLOCKED_TIMEOUT_MINUTES = 5;
    
    private List<Game> games;
    private List<Player> players;
    private WaitingQueue waitingQueue;
    private Settings settings;

    /**
     * Main constructor
     */
    public Main() {
        games = new ArrayList<>();
        players = new ArrayList<>();
        waitingQueue = new WaitingQueue();
        settings = new Settings();
    }

    /**
     * Static constructor that generates an instance of itself. Mocks data if
     * settings.mockData is true.
     */
    static {
        Main.self = new Main();

        if (Settings.MOCKDATA) {

            Player player = new Player(new Credentials("John@test.uk", "John", "password"));
            player.setWinCount(3);
            Main.self.players.add(player);

            Player player2 = new Player(new Credentials("Jake@test.uk", "Jake", "password"));
            player2.setWinCount(1);
            Main.self.players.add(player2);

            Player player3 = new Player(new Credentials("Paul@test.uk", "Paul", "password"));
            player3.setWinCount(1);
            Main.self.players.add(player3);

            Player player4 = new Player(new Credentials("Terrance@test.uk", "Terrance", "password"));
            player4.setWinCount(7);
            Main.self.players.add(player4);

            Player player5 = new Player(new Credentials("Phil@test.uk", "Phil", "password"));
            player5.setWinCount(0);
            Main.self.players.add(player5);

            Game game = new Game(Main.self.settings);

            game.setMap(new GameMap(10, 10));
            game.setTurn(4);

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

    /**
     * Tries to login the player with the given Credentials. 
     * If it fails the failed login count goes up by one, potentially blocking the player.
     *
     * @param c
     * @return player matching id
     * @throws exceptions.FailedLoginException
     * @throws NotFoundException occurs when no id exists (error 404).
     */
    public Player getPlayerByLogin(Credentials c) throws FailedLoginException {

        for (Player p : players) {
            if (p.getUsername().equals(c.getUsername())) {
                //username matches, check if this user is blocked or not.
                if (p.isBlocked()) {
                    //user is blocked, check if he should be blocked.
                    if (!p.checkUnblocked()) {
                        throw new BlockedException();
                    }
                }
                //user is not blocked, check if credentials match.
                if (p.getPassword().equals(c.getPassword()) && p.getEmail().equals(c.getEmail())) {
                    //credentials match, login succesful
                    return p;
                } else {
                    //credentials do not match, login failed. on too many failed attempts the user becomes blocked.
                    p.incrementFailedLoginCount();

                    //block account
                    if (p.getFailedLoginCount() == BLOCKED_ON_FAILED_LOGIN_ATTEMPTS) {
                        p.block(BLOCKED_TIMEOUT_MINUTES);
                        throw new BlockedException();
                    }
                }
            }
        }
        throw new FailedLoginException();
    }

    /**
     * Creates a new Player account.
     * Checks if the username and email credentials are already in use, if not, the player account is created.
     * @param c (credentials)
     * @throws exceptions.FailedRegisterException
     */
    public void registerWithCredentials(Credentials c) throws FailedRegisterException {
        for (Player p : players) {
            if (p.getUsername().equals(c.getUsername()) || p.getEmail().equals(c.getEmail())) {
                throw new FailedRegisterException();
            }
            
        }
        players.add(new Player(c));
    }
    
    
    public boolean checkSessionToken(int playerId, String sessionToken) {
        
        Player p = getPlayerById(playerId);
        return p.getSessionId().equals(sessionToken);
    }

    
    public List<Game> getGames() {
        return games;
    }

    public Game getGame(int i) {
        return games.get(i);
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
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
