package app.bean;

import app.dao.MatchRepository;
import app.entity.Match;
import app.entity.MatchMap;
import app.entity.Node;
import app.entity.Player;
import app.exception.MatchHasEndedException;
import app.exception.NotFoundException;
import app.exception.ParameterOutOfBoundsException;
import app.exception.NotInMatchException;
import app.enums.Action;
import app.dto.Move;
import app.enums.MoveDirection;
import app.dto.MoveList;
import java.awt.Point;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Responsible for handling game rules logic. Stores MoveLists and activeMatches
 * and processes them over time.
 *
 * Although Match progress is stored through Repository, it is no longer hosted
 * in case the application stops working (the match is then discontinued). Moves
 * themselves are not stored in a Repository, and are instead stored statically
 * in this class. This means they will be entirely lost if the application
 * stops.
 *
 * @author dion
 */
@Configuration
public class HostGame {

    private static Map<Long, Match> activeMatches = new HashMap<>();

    private static Map<Long, MoveList> playerMoves = new HashMap<>();

    @Inject
    private MatchRepository matchRep;

    private static MatchRepository matchRepository;

    /**
     * Bean for relaying injected Repository variables. Beans are automatically
     * run when the application is booted. Static injecting a repository does
     * not work. This Bean however, can inject but has no REST session and will
     * therefor be limited in features, such as that lazy loading is not handled
     * and instead throws org.hibernate.LazyInitializationException.
     *
     * Warning : Bean might not fire if method name is not unique across all
     * Beans. Beans are not multi-threaded from each other and are therefor not
     * a solution to running these static methods.
     *
     * @return can be ignored.
     */
    @Bean
    public CommandLineRunner hostGameStaticInjects() {

        return (args) -> {
            matchRepository = matchRep;
        };

    }

    /**
     * Static run command that hosts the game. Will remain stuck in an infinite
     * loop and should therefor be called last by Application.run(). Executes
     * all game logic and processes the stored Moves.
     *
     */
    public static void run() {
        while (true) {
            try {
                sleep(500);

                //For every match, perform a turn
                for (Match activeMatch : activeMatches.values()) {

                    //Every owned node gains power
                    MatchMap map = activeMatch.getMap();
                    List<Node> nodes = map.getNodes();
                    Map<Action, Integer> actionCosts = activeMatch.getGameRules().getActionCosts();
                    nodes.forEach(node -> {
                        if (node.getOwnerId() != 0) {
                            node.adjustPower(1);
                        }
                    });

                    //Perform all moves
                    for (Player player : activeMatch.getPlayers()) {
                        MoveList moves = playerMoves.get(player.getId());
                        if (moves == null) {
                            continue;
                        }

                        for (Move move : moves.getMoves()) {
                            Action action = Action.values()[move.getAction()];
                            MoveDirection direction = MoveDirection.values()[move.getDirection()];
                            Point location = new Point(move.getX(), move.getY());
                            Point location2 = direction.getLocation(location);
                            
                            Node node = nodes.get(location.x + location.y * map.getWidth());
                            Node node2 = nodes.get(location2.x + location2.y * map.getWidth());

                            //remove cost
                            node.adjustPower(-actionCosts.get(action));

                            //perform action
                            switch (action) {
                                case SLEEP:
                                    break;
                                case SPREAD:
                                    if (node2 != node && node2.getOwnerId() != player.getId())
                                        node2.setOwnerId(player.getId());
                                    else
                                        //revert cost
                                        node.adjustPower(actionCosts.get(action));
                                    
                                    break;
                                case SPREADALL:
                                    Point location3 = direction.getLocation(location2);
                                    Point location4 = direction.getLocation(location3);
                                    Point location5 = direction.getLocation(location4);
                                    
                                    Node node3 = nodes.get(location3.x + location3.y * map.getWidth());                                    
                                    Node node4 = nodes.get(location4.x + location4.y * map.getWidth());
                                    Node node5 = nodes.get(location5.x + location5.y * map.getWidth());

                                    node2.setOwnerId(player.getId());
                                    node3.setOwnerId(player.getId());
                                    node4.setOwnerId(player.getId());
                                    node5.setOwnerId(player.getId());
                                    break;
                                case SPREADLINE:
                                    break;
                                case EMPOWER:
                                    break;
                                case DISCHARGE:
                                    break;
                                case POWERLINE:
                                    break;
                                case OVERCLOCK:
                                    break;
                                case GUARD:
                                    break;
                                case STORAGE:
                                    break;
                                case DRAIN:
                                    break;
                                case EXPLODE:
                                    break;
                            }
                        }

                    }

                    //NEW TURN
                    activeMatch.setTurn(activeMatch.getTurn() + 1);
                    System.out.print("|");
                    //System.out.println("Game " + activeMatch.getId() + " Turn " + activeMatch.getTurn());

                    //Store match
                    matchRepository.save(activeMatch);
                    
                    //SEND TURN TO UNITY
                    SocketToUnity.setTurnMoves(playerMoves);

                    //RESET MOVE LIST
                    for (Player player : activeMatch.getPlayers()) {
                        playerMoves.put(player.getId(), null);
                    }
                   
                }
                

            } catch (InterruptedException ex) {
                Logger.getLogger(HostGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Stores a new Match. Called by WaitingQueue web service when someone joins
     * the waiting queue and there is room to host another game. Once a game is
     * stored, it will start playing immediately.
     *
     * @param match
     */
    public static void storeMatch(Match match) {
        activeMatches.put(match.getId(), match);
    }

    /**
     * Stores a MoveList for a specific Match and Player. Called by Match web
     * service when someone posts the moves they want to perform. Only one
     * MoveList is stored per Player, that is, newly posted MoveLists override
     * any MoveLists for the same turn.
     *
     * @param matchId
     * @param playerId
     * @param moves
     * @throws NotInMatchException
     * @throws ParameterOutOfBoundsException
     * @throws NotFoundException
     */
    public static void storeMoves(long matchId, long playerId, MoveList moves) throws NotInMatchException, ParameterOutOfBoundsException, NotFoundException, MatchHasEndedException {
        Match match = activeMatches.get(matchId);

        if (match == null) {
            throw new NotFoundException();
        }
        
        if (match.isEnded()) {
            throw new MatchHasEndedException();
        }

        Player player = match.getPlayer(playerId);

        if (player == null) {
            throw new NotInMatchException();
        }

        //Player found in match, remove invalid moves
        MatchMap map = match.getMap();
        int width = map.getWidth();
        int height = map.getHeight();
        Map<Action, Integer> actionCosts = match.getGameRules().getActionCosts();

        for (int i = 0; i < moves.getMoves().size(); i++) {
            Move move = moves.getMove(i);
            
            //Remove moves where owner does not match.
            Node node = map.getNode(move.getX(), move.getY());
            if (node.getOwnerId() != player.getId()) {
                moves.remove(i);
                i--;
            } else //Remove moves where there is not enough Power.
            if (actionCosts.get(Action.values()[move.getAction()]) > node.getPower() + 1) {
                moves.remove(i);
                i--;
            }

            //throw exception when direction leads off the map
            MoveDirection direction = MoveDirection.values()[move.getDirection()];
            Point location2 = direction.getLocation(new Point(move.getX(), move.getY()));
            map.getNode(location2.x, location2.y);
            
        }
        //Save moves
        if (moves.getMoves().size() > 0) {
            playerMoves.put(playerId, moves);
            System.out.println(moves.getMoves().size() + " moves received from player " + player.getName());
        }
    }

}
