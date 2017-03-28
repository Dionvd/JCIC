//http://stackoverflow.com/questions/14824491/can-i-communicate-between-java-and-c-sharp-using-just-sockets
package app.bean;

import app.dao.MatchRepository;
import app.dao.NodeRepository;
import app.entity.Match;
import app.entity.MatchMap;
import app.entity.Node;
import app.enums.Action;
import app.dto.Move;
import app.enums.MoveDirection;
import app.dto.MoveList;
import app.dto.WaitingQueue;
import app.entity.Player;
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

    private static final MoveList movesToSend = new MoveList();
    private static final List<String> waitingQueueToSend = new ArrayList<>();
    
    @Inject
    private MatchRepository matchRep;
    private static MatchRepository matchRepository;
    
    @Inject
    private NodeRepository nodeRep;
    private static NodeRepository nodeRepository;
    
    @Bean
    public CommandLineRunner socketStaticInjects() {
        
        return (args) -> { 
            matchRepository = matchRep;
            nodeRepository = nodeRep;
        };
        
    }
    
    public static void run() {

        try {
            System.out.println("--STARTING SOCKET SERVER--");

            serverSocket = new ServerSocket(HOST_PORT, 1);


            new Thread() {
                @Override
                public void run() {

                    try {
                        socket = serverSocket.accept();
                    } catch (IOException ex) {
                        Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Client connected...");
                    
                    
                    boolean success = send(getMap());
                    if (success == false) return;
                    
                    while (true) {
                        try {
                            MoveList takeMoves = takeMoves();
                            if (takeMoves != null && !takeMoves.getMoves().isEmpty())
                            { success = send(takeMoves); if (success == false) return; }
                            
                            List<String> takeQueue = takeQueue();
                            if (takeQueue != null && !takeQueue.isEmpty())
                            { success = send(takeQueue); if (success == false) return; }
                            
                            sleep(500);
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }.start();
            System.out.println("--STARTED!--");
 

        } catch (IOException ex) {
            System.out.println("Failed to start socket!");
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);

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
            System.out.println("Sending to Unity : " + toSend);
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
            System.out.println("Connection with Unity broke! Restarting socket!");
            os.close();
            socket.close();
            serverSocket.close();
            run();
        } catch (IOException ex) {
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public static MatchMap getMap() {
        try {
        Match match = matchRepository.findOne(1L);
        MatchMap map = match.getMap();
        
        Iterable<Node> findAll = nodeRepository.findAll();
        ArrayList<Node> nodes = new ArrayList<>();
        findAll.forEach(nodes::add);
        map.setNodes(nodes);
        match.setPlayers(new ArrayList<>());
        return map;
        }
        catch (Exception e) { return null; }
    }
    
    
    public static MoveList takeMoves() {
        synchronized (movesToSend)
        {
        MoveList moves = new MoveList(movesToSend.getMoves());
        movesToSend.getMoves().clear();
        return moves;
        }
    }
    
    public static List<String> takeQueue() {
        
        synchronized (waitingQueueToSend)
        {
            List<String> queue = new ArrayList<>(waitingQueueToSend);
            waitingQueueToSend.clear();
            return queue;
        }
    }
    
    
    public static void setTurnMoves(Map<Long, MoveList> playerMoves) {
        
        synchronized (movesToSend)
        {
            MoveList allMoves = MoveList.merge(playerMoves.values());
            //new turn signal move
            movesToSend.getMoves().add(new Move(new Point(-1,-1), Action.SLEEP, MoveDirection.CENTRAL));
            //add all moves to this turn
            movesToSend.getMoves().addAll(allMoves.getMoves());
        }
    }
    
    public static void setQueueUpdate(WaitingQueue waitingQueue)
    {
        synchronized (waitingQueueToSend)
        {
            List<String> names = new ArrayList<>();
            List<Player> players = waitingQueue.getPlayers();

            players.forEach((player) -> {
                waitingQueueToSend.add(player.getName());
            });
        }
    }
    
}
