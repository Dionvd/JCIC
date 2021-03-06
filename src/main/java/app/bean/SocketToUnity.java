//http://stackoverflow.com/questions/14824491/can-i-communicate-between-java-and-c-sharp-using-just-sockets
package app.bean;

import app.dao.NodeRepository;
import app.dao.PlayerRepository;
import app.entity.Round;
import app.entity.RoundMap;
import app.entity.Node;
import app.enums.Action;
import app.dto.Move;
import app.enums.MoveDirection;
import app.dto.PlayerList;
import app.dto.MoveList;
import app.dto.WaitingQueue;
import app.entity.Player;
import app.service.QueueService;
import app.ui.Log;
import java.awt.Point;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import app.dao.RoundRepository;
import app.dto.MoveList2;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Hosts a socket server for sending information about the game to Unity3D.
 *
 * @author dion
 */
@Configuration
public class SocketToUnity {

    private static final int HOST_PORT = 5242;

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static OutputStream os;

    private static final List<Round> roundsToSend = new ArrayList<Round>();
    private static final List<MoveList2> movesToSend = new ArrayList<MoveList2>();
    private static final List<Player> waitingQueueToSend = new ArrayList<>();
    
    @Inject
    private RoundRepository roundRep;
    private static RoundRepository roundRepository;
    
    @Inject
    private NodeRepository nodeRep;
    private static NodeRepository nodeRepository;
    
    @Inject
    private PlayerRepository playerRep;
    private static PlayerRepository playerRepository;
    
    @Bean
    public CommandLineRunner socketStaticInjects() {
        
        return (args) -> { 
            roundRepository = roundRep;
            nodeRepository = nodeRep;
            playerRepository = playerRep;
        };
        
    }
    
    public static void run() {

        try {
            Log.write("--STARTING SOCKET SERVER--");

            serverSocket = new ServerSocket(HOST_PORT, 1);


            new Thread() {
                @Override
                public void run() {

                    try {
                        socket = serverSocket.accept();
                    } catch (IOException ex) {
                        Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Log.write("Client connected...");
                    
                    resetRoundsToSend();
                    
                    boolean success = send(getQueue());
                    if (success == false) return;
                    
                    while (true) {
                        try {
                            
                            
                            Round takeRound = takeRound();
                            while (takeRound != null)
                            {
                                success = send(takeRound);
                                if (success == false) return;
                                takeRound = takeRound();
                            }
                            
                            MoveList2 takeMoves = takeMoves();
                            while (takeMoves != null)
                            {
                                if (!takeMoves.getMoves().isEmpty())
                                { success = send(takeMoves); if (success == false) return; }
                                takeMoves = takeMoves();
                            }
                            
                            PlayerList takeQueue = takeQueue();
                            if (takeQueue != null && !takeQueue.getPlayers().isEmpty())
                            { success = send(takeQueue); if (success == false) return; }
                            
                            sleep(500);
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }.start();
            Log.write("--SOCKET STARTED!--");
 

        } catch (IOException ex) {
            Log.write("Failed to start socket!");
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    
    
    private static void resetRoundsToSend() {

        synchronized (roundsToSend)
        {
            roundsToSend.clear();
            roundsToSend.addAll(HostGame.activeRounds.values());
        }
    }

    public static boolean send(Object o) {

        try {
            os = socket.getOutputStream();

            // prepare sending
            JSONObject json = new JSONObject(o);

            String toSend = json.toString();
            byte[] toSendBytes = toSend.getBytes();
            int toSendLen = toSendBytes.length;
            byte[] toSendLenBytes = new byte[4];
            toSendLenBytes[0] = (byte) (toSendLen & 0xff);
            toSendLenBytes[1] = (byte) ((toSendLen >> 8) & 0xff);
            toSendLenBytes[2] = (byte) ((toSendLen >> 16) & 0xff);
            toSendLenBytes[3] = (byte) ((toSendLen >> 24) & 0xff);

            //send to client
            if (o instanceof MoveList2 == false)
                Log.write("Sending to Unity : " + toSend);
            
            os.write(toSendLenBytes);
            os.write(toSendBytes);
            
            return true;

        } catch (SocketException ex) {

            //UNITY3D CLOSED WHILE RUNNING
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
            restartSocket();

        } catch (IOException ex) {
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void restartSocket() {
        try {
            Log.write("Connection with Unity broke! Restarting socket!");
            os.close();
            socket.close();
            serverSocket.close();
            run();
        } catch (IOException ex) {
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the requested round out of the database. Because there is no spring
     * data session the lazy-load does not work and required values have to be
     * manually loaded and attached.
     * 
     * @return
     */
    public static Round takeRound() {
        
        synchronized (roundsToSend)
        {
            if (roundsToSend.isEmpty() == false)
            {
                Round round = roundsToSend.get(0);
                roundsToSend.remove(0);
                return round;
            }
            else
            {
                return null;
            }
        }
        
    }
    
    
    public static MoveList2 takeMoves() {
        synchronized (movesToSend)
        {
            if (movesToSend.isEmpty() == false)
            {
                MoveList2 moves = movesToSend.get(0);
                movesToSend.remove(0);
                return moves;
            }
            else
            {
                return null;
            }
        }
    }
    
    public static PlayerList getQueue() { 
        
        takeQueue();
        return new PlayerList(QueueService.getWaitingQueue().getPlayers());
    }
    
    public static PlayerList takeQueue() {
        
        synchronized (waitingQueueToSend)
        {
            PlayerList queue = new PlayerList(waitingQueueToSend);
            waitingQueueToSend.clear();
            return queue;
        }
    }
    
    
    public static void addTurnMoves(Map<Long, MoveList> playerMoves, Map<Long, Round> activeRounds) {
        
        synchronized (movesToSend)
        {
            Iterator<Round> iterator = activeRounds.values().iterator();
            
            while (iterator.hasNext())
            {
                Round round = iterator.next();
                
                Iterator<Long> playerIds = round.getPlayerIds().iterator();
                MoveList2 moves = new MoveList2(round.getId());
                
                if (round.isEnded())
                    moves.getMoves().add(new Move(new Point(-9,-9), Action.EXPLODE, MoveDirection.CENTRAL));
                else
                    moves.getMoves().add(new Move(new Point(-1,-1), Action.SLEEP, MoveDirection.CENTRAL));
                
                while (playerIds.hasNext())
                {
                    long playerId = playerIds.next();
                    MoveList get = playerMoves.get(playerId);
                    if (get != null)
                    moves.getMoves().addAll(get.getMoves());
                }
                
                //all moves from one round are added together, ready to be sent to unity.
                movesToSend.add(moves);
            }
            
        }
    }
    
    public static void setQueueUpdate(WaitingQueue waitingQueue)
    {
        synchronized (waitingQueueToSend)
        {
            List<Player> players = waitingQueue.getPlayers();

            players.forEach((player) -> {
                waitingQueueToSend.add(player);
            });
        }
    }
    
    public static void addRound(Round round)
    {
        
        synchronized (roundsToSend)
        {
            if (roundsToSend.contains(round) == false)
            roundsToSend.add(round);
        }
    }
}
