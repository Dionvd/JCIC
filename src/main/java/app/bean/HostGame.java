package app.bean;

import app.bot.*;
import app.dao.PlayerRepository;
import app.entity.*;
import app.exception.*;
import app.enums.Action;
import app.dto.Move;
import app.enums.MoveDirection;
import app.dto.MoveList;
import app.ui.Log;
import java.awt.Point;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import app.dao.RoundRepository;
import app.dao.SettingsRepository;
import app.rest.SettingsResource;
import app.service.QueueService;
import app.ui.TableActiveMatches;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Responsible for handling game rules logic. Stores MoveLists and activeRounds
 * and processes them over time.
 *
 * Although Round progress is stored through Repository, it is no longer hosted
 * in case the application stops working (the Round is then discontinued). Moves
 * themselves are not stored in a Repository, and are instead stored statically
 * in this class. This means they will be entirely lost if the application
 * stops.
 *
 * @author dion
 */
@Configuration
public class HostGame {

    public static Map<Long, Round> activeRounds = new HashMap<>();

    private static Map<Long, MoveList> playerMoves = new HashMap<>();

    @Inject
    private PlayerRepository playerRep;
    private static PlayerRepository playerRepository;

    @Inject
    private RoundRepository roundRep;
    private static RoundRepository roundRepository;

    @Inject
    private SettingsRepository settingsRep;
    private static SettingsRepository settingsRepository;

    private static boolean stopRounds = false;
    private static long stopRoundId = -1;

    /**
     * Bean for relaying injected Repository variables. Beans are automatically
     * run when the application is booted. Static injecting a repository does
     * not work. This Bean however, can inject but has no REST session and will
     * therefor be limited in features, such as that lazy loading is not handled
     *
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
            roundRepository = roundRep;
            playerRepository = playerRep;
            settingsRepository = settingsRep;

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

                //Not enough rounds being played?
                if (activeRounds.isEmpty()) {
                    Settings settings = settingsRepository.findOne(1L);
                    Round round = new Round(settings);
                    roundRepository.save(round);
                    RoundMap map = new RoundMap(new Point(10, 10), round.getId());
                    round.setMap(map);

                    Player player = QueueService.getWaitingQueue().RemoveFirst();

                    while (player != null && round.getPlayerCount() < 4) {
                        round.getPlayerIds().add(player.getId());
                        player = QueueService.getWaitingQueue().RemoveFirst();
                    }

                    if (round.getPlayerCount() < 2) {
                        round.getPlayerIds().add(1L);
                    }
                    if (round.getPlayerCount() < 2) {
                        round.getPlayerIds().add(2L);
                    }

                    round.getMap().generate(round.getPlayerIds());
                    roundRepository.save(round);

                    storeRound(round);
                    Log.write("Game " + round.getId() + " initiated due to lack of active matches.");
                }

                sleep(500);

                //For every round, perform a turn
                for (Round activeRound : new ArrayList<Round>(activeRounds.values())) {

                    //did the round end?
                    if (activeRound.isEnded()) {
                        activeRounds.remove(activeRound.getId());
                    }

                    //Internal bots calculate and send in their moves
                    calculateBotMoves(activeRound);

                    //Perform all sent moves
                    performMoves(activeRound);

                    //Every owned node gains power
                    performPowerIncrease(activeRound);

                    //NEW TURN
                    activeRound.setTurn(activeRound.getTurn() + 1);

                    //Log.write("Game " + activeRound.getId() + " Turn " + activeRound.getTurn());
                    //Store round
                    roundRepository.save(activeRound);

                    //DID THE MATCH END?
                    if (activeRound.getTurn() > 300) {
                        long id = activeRound.getMap().getPlayerWithMostNodes();
                        if (id > 0) {
                            Player winner = playerRepository.findOne(id);
                            winner.setWinCount(winner.getWinCount() + 1);
                        }
                        activeRound.setEnded(true);
                    }

                }

                //SEND TURN TO UNITY
                SocketToUnity.addTurnMoves(playerMoves, activeRounds);

                //RESET MOVE LIST
                for (Round activeRound : activeRounds.values()) {
                    for (Long l : activeRound.getPlayerIds()) {
                        playerMoves.put(l, null);
                    }
                }

                //RESET DATA?
                if (stopRoundId != -1) {
                    activeRounds.remove(stopRoundId);
                    stopRoundId = -1;
                }
                if (stopRounds) {
                    activeRounds.clear();
                    playerMoves.clear();
                    AdminPanelHandler.READY_TO_CLEAR_GAME_DATA = true;
                    stopRounds = false;
                }

                //update adminpanel
                TableActiveMatches.update(activeRounds.values());

            } catch (InterruptedException ex) {
                Logger.getLogger(HostGame.class.getName()).log(Level.SEVERE, null, ex);
                Log.write(ex.getMessage());
            }
        }

    }

    /**
     * Finds internal bots within rounds and tells them to send in their moves.
     *
     * @param activeRound
     */
    static void calculateBotMoves(Round activeRound) {
        activeRound.getPlayerIds().stream()
                .filter((playerId) -> (playerId <= 6))
                .forEachOrdered((bot) -> {
                    int i = bot.intValue();
                    MoveList moves = null;

                    switch (i) {
                        case 1:
                            moves = GuardianBot.calculateMoves(activeRound.getMap());
                            break;
                        case 2:
                            moves = AssaultBot.calculateMoves(activeRound.getMap());
                            break;
                        case 3:
                            moves = PowerBot.calculateMoves(activeRound.getMap());
                            break;
                        case 4:
                            moves = TacticalBot.calculateMoves(activeRound.getMap());
                            break;
                        case 5:
                            moves = FrontierBot.calculateMoves(activeRound.getMap());
                            break;
                        case 6:
                            moves = PlanningBot.calculateMoves(activeRound.getMap());
                            break;
                    }
                    playerMoves.put(bot, moves);
                });
    }

    /**
     * Performs all actions that are stored for the given round
     *
     * @param activeRound
     */
    static void performMoves(Round activeRound) {
        RoundMap map = activeRound.getMap();
        List<Node> nodes = map.getNodes();
        Map<Action, Integer> actionCosts = activeRound.getGameRules().getActionCosts();
        int mapWidth = map.getWidth();

        //Perform all moves
        for (Long playerId : activeRound.getPlayerIds()) {
            MoveList moves = playerMoves.get(playerId);
            if (moves == null) {
                continue;
            }

            for (Move move : new ArrayList<>(moves.getMoves())) {

                Action action = Action.values()[move.getAction()];
                Node node = nodes.get(move.getX() + move.getY() * mapWidth);

                //remove cost
                node.adjustPower(-actionCosts.get(action));

                boolean status = false;

                //perform action
                switch (action) {
                    case SLEEP:
                        break;
                    case SPREAD:
                        status = actionSpread(playerId, nodes, node, move, actionCosts.get(action), mapWidth);
                        break;
                    case SPREADALL:
                        status = actionSpreadAll(playerId, nodes, node, move, actionCosts.get(action), mapWidth);
                        break;
                    case SPREADLINE:
                        status = actionSpreadLine(playerId, nodes, node, move, actionCosts.get(action), mapWidth);
                        break;
                    case EMPOWER:
                        status = actionEmpower(playerId, nodes, node, move, actionCosts.get(action), mapWidth);
                        break;
                    case DISCHARGE:
                        status = actionDischarge(playerId, nodes, node, move, actionCosts.get(action), mapWidth);
                        break;
                    case POWERLINE:
                        status = actionPowerline(playerId, node, move, actionCosts.get(action));
                        break;
                    case OVERCLOCK:
                        status = actionOverclock(playerId, node, move, actionCosts.get(action));
                        break;
                    case GUARD:
                        status = actionGuard(playerId, node, move, actionCosts.get(action));
                        break;
                    case STORAGE:
                        status = actionStorage(playerId, node, move, actionCosts.get(action));
                        break;
                    case DRAIN:
                        status = actionDrain(playerId, nodes, node, move, actionCosts.get(action), mapWidth);
                        break;
                    case EXPLODE:
                        status = actionExplode(playerId, nodes, node, move, actionCosts.get(action), mapWidth);
                        break;
                }

                if (status == false) //action did not go through
                {
                    node.adjustPower(actionCosts.get(action));
                    moves.getMoves().remove(move);
                }
            }

        }
    }

    /**
     * Increases the power of all owned nodes.
     *
     * @param activeRound
     */
    static void performPowerIncrease(Round activeRound) {
        activeRound.getMap().getNodes().forEach(node -> {
            if (node.getOwnerId() != 0) {
                if (node.getType() == 2) {
                    node.adjustPower(3);
                } else {
                    node.adjustPower(1);
                }
            }
        });
    }

    /**
     * Stores a new Round. Called by WaitingQueue web service when someone joins
     * the waiting queue and there is room to host another game. Once a game is
     * stored, it will start playing immediately.
     *
     * @param round
     */
    public static void storeRound(Round round) {
        activeRounds.put(round.getId(), round);

        SocketToUnity.addRound(round);
    }

    /**
     * Stores a MoveList for a specific Round and Player. Called by Round web
     * service when someone posts the moves they want to perform. Only one
     * MoveList is stored per Player, that is, newly posted MoveLists override
     * any MoveLists for the same turn.
     *
     * @param roundId
     * @param playerId
     * @param moves
     * @throws NotInRoundException
     * @throws ParameterOutOfBoundsException
     * @throws NotFoundException
     */
    public static void storeMoves(long roundId, long playerId, MoveList moves) throws NotInRoundException, ParameterOutOfBoundsException, NotFoundException, RoundHasEndedException {
        Round round = activeRounds.get(roundId);

        if (round == null) {
            throw new NotFoundException();
        }

        if (round.isEnded()) {
            throw new RoundHasEndedException();
        }

        Player player = round.getPlayer(playerId);

        if (player == null) {
            throw new NotInRoundException();
        }

        //Player found in round, remove invalid moves
        RoundMap map = round.getMap();
        int width = map.getWidth();
        int height = map.getHeight();
        Map<Action, Integer> actionCosts = round.getGameRules().getActionCosts();

        for (int i = 0; i < moves.getMoves().size(); i++) {
            Move move = moves.getMove(i);

            //throw exception when direction leads off the map
            MoveDirection direction = MoveDirection.values()[move.getDirection()];
            Point location2 = direction.getLocation(new Point(move.getX(), move.getY()));
            Node node2 = map.getNode(location2.x, location2.y);

            //Remove moves where owner does not match.
            Node node = map.getNode(move.getX(), move.getY());

            if (node.getOwnerId() != player.getId()) {
                moves.remove(i);
                i--;
            } else if (actionCosts.get(Action.values()[move.getAction()]) > node.getPower() + 1) {
                //Remove moves where there is not enough Power.
                moves.remove(i);
                i--;
            } else if (node2.getType() == -1) {
                //Remove moves that target blocked tiles
                moves.remove(i);
                i--;
            }

        }
        //Save moves
        if (moves.getMoves().size() > 0) {
            playerMoves.put(playerId, moves);
            Log.write(moves.getMoves().size() + " moves received from player " + player.getName());
        }
    }

    public static void stopRounds() {
        stopRounds = true;
    }

    public static void stopRound(int roundIndex) {
        if (roundIndex == -1) {
            return;
        }
        Round round = new ArrayList<>(activeRounds.values()).get(roundIndex);
        round.setEnded(true);
        stopRoundId = round.getId();
    }

    public static boolean actionSpread(Long playerId, List<Node> nodes, Node node, Move move, int actionCost, int mapWidth) {
        MoveDirection direction = MoveDirection.values()[move.getDirection()];

        Point location = new Point(move.getX(), move.getY());
        Point location2 = direction.getLocation(location);
        Node node2 = nodes.get(location2.x + location2.y * mapWidth);

        return node2.attackedBy(playerId);
    }

    public static boolean actionSpreadAll(Long playerId, List<Node> nodes, Node node, Move move, int actionCost, int mapWidth) {
        Point location = new Point(move.getX(), move.getY());
        Node node2;

        boolean didSomething = false;
        int mapHeight = nodes.size() / mapWidth;

        for (MoveDirection moveDirection : MoveDirection.values()) {
            Point location2 = moveDirection.getLocation(location);

            if (location2.x < 0 || location2.y < 0 || location2.x >= mapWidth || location2.y >= mapHeight) {
                continue;
            }

            node2 = nodes.get(location2.x + location2.y * mapWidth);
            if (node2 != node) {
                if (node2.attackedBy(playerId)) {
                    didSomething = true;
                }

            }
        }

        return didSomething;
    }

    public static boolean actionSpreadLine(Long playerId, List<Node> nodes, Node node, Move move, int actionCost, int mapWidth) {

        MoveDirection direction = MoveDirection.values()[move.getDirection()];
        Point location = new Point(move.getX(), move.getY());
        int mapHeight = nodes.size() / mapWidth;
        List<Point> locations = new ArrayList<>();
        Point location2 = location;

        boolean didSomething = false;

        for (int i = 0; i < 5; i++) {
            location2 = direction.getLocation(location2);
            if (location2.x < 0 || location2.y < 0 || location2.x >= mapWidth || location2.y >= mapHeight) {
                continue;
            }
            Node node2 = nodes.get(location2.x + location2.y * mapWidth);
            if (node2.attackedBy(playerId)) {
                didSomething = true;
            }
        }

        return didSomething;
    }

    public static boolean actionEmpower(Long playerId, List<Node> nodes, Node node, Move move, int actionCost, int mapWidth) {
        MoveDirection direction = MoveDirection.values()[move.getDirection()];
        Point location = new Point(move.getX(), move.getY());
        Node node2;
        Node lowestNode = null;
        int dir = -1;

        for (int i = 1; i < MoveDirection.values().length; i++) {
            Point spreadPoint = MoveDirection.values()[i].getLocation(location);

            if (spreadPoint.x < 0 || spreadPoint.y < 0 || spreadPoint.x >= mapWidth || spreadPoint.y >= nodes.size() / mapWidth) {
                continue;
            }

            node2 = nodes.get(spreadPoint.x + spreadPoint.y * mapWidth);
            if (node2.getOwnerId() == playerId && (lowestNode == null || node2.getPower() < lowestNode.getPower())) {
                lowestNode = node2;
                dir = i;
            }
        }
        //no other friendly nodes found
        if (lowestNode == null) {
            return false;
        }

        lowestNode.adjustPower(5);
        move.setDirection(dir);
        return true;
    }

    public static boolean actionDischarge(Long playerId, List<Node> nodes, Node node, Move move, int actionCost, int mapWidth) {
        //TODO SELL SPECIAL TYPE
        MoveDirection direction = MoveDirection.values()[move.getDirection()];
        Point location = new Point(move.getX(), move.getY());
        Node node2;

        int remainingPower = node.getPower();
        List<Node> friendlyAdjNodes = new ArrayList<>();

        for (MoveDirection moveDirection : MoveDirection.values()) {
            Point adjacentPoint = moveDirection.getLocation(location);
            node2 = nodes.get(adjacentPoint.x + adjacentPoint.y * mapWidth);
            if (node2.getOwnerId() == playerId) {
                friendlyAdjNodes.add(node2);
            }
        }

        remainingPower = remainingPower / friendlyAdjNodes.size();
        node.setPower(0);
        for (Node friendlyAdjNode : friendlyAdjNodes) {
            friendlyAdjNode.adjustPower(remainingPower);
        }

        return true;
    }

    public static boolean actionPowerline(Long playerId, Node node, Move move, int actionCost) {
        node.setType(1);
        return true;
    }

    public static boolean actionOverclock(Long playerId, Node node, Move move, int actionCost) {
        node.setType(2);
        return true;
    }

    public static boolean actionGuard(Long playerId, Node node, Move move, int actionCost) {
        node.setType(3);
        return true;
    }

    public static boolean actionStorage(Long playerId, Node node, Move move, int actionCost) {
        node.setType(4);
        return true;
    }

    public static boolean actionDrain(Long playerId, List<Node> nodes, Node node, Move move, int actionCost, int mapWidth) {
        Node highestNode;
        Point location = new Point(move.getX(), move.getY());
        int mapHeight = nodes.size() / mapWidth;

        List<Node> collectedList = Arrays.asList(MoveDirection.values()).stream().map(moveDirection -> {
            Point adjacentPoint = moveDirection.getLocation(location);
            if (adjacentPoint.x > -1 && adjacentPoint.y > -1 && adjacentPoint.x < mapWidth && adjacentPoint.y < mapHeight) {
                return nodes.get(adjacentPoint.x + adjacentPoint.y * mapWidth);
            } else {
                return null;
            }
        }).collect(Collectors.toList());

        Optional<Node> max = collectedList.stream()
                .filter(adjacentNode -> adjacentNode != null)
                .filter(adjacentNode -> adjacentNode.getOwnerId() != 0)
                .filter(adjacentNode -> adjacentNode.getOwnerId() != playerId)
                .filter(adjacentNode -> adjacentNode.getPower() >= 5)
                .max((n1, n2) -> n1.getPower() - n2.getPower());

        if (max.isPresent()) {
            highestNode = max.get();
            highestNode.adjustPower(-5);

            move.setDirection(collectedList.indexOf(highestNode));

            return true;
        }

        return false;
    }

    public static boolean actionExplode(Long playerId, List<Node> nodes, Node node, Move move, int actionCost, int mapWidth) {
        Point location = new Point(move.getX(), move.getY());

        for (int ix = -2; ix <= 2; ix++) {
            for (int iy = -2; iy <= 2; iy++) {

                int newX = location.x + ix;
                int newY = location.y + iy;

                if (ix * iy == -4 || ix * iy == 4) {
                    //skip corners
                    continue;
                }

                if (location.y % 2 == 0) {
                    // . - O + .
                    //  - - O + .
                    // - - i + +
                    //  - - O + .
                    // . - O + .
                    if ((iy == -1 || iy == 1) && ix == 2) {
                        //not part of circle
                        continue;
                    }
                } else {
                    // . - O + .
                    //. - O + +
                    // - - i + +
                    //. - O + +
                    // . - O + .
                    if ((iy == -1 || iy == 1) && ix == -2) {
                        //not part of circle
                        continue;
                    }
                }

                Node explodingNode = nodes.get(newX + newY * mapWidth);
                explodingNode.setPower(0);
                explodingNode.setOwnerId(0);
                if (explodingNode.getType() != -1) {
                    explodingNode.setType(0);
                }
            }
        }

        return true;

    }

}
