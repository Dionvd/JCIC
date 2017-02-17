//http://stackoverflow.com/questions/14824491/can-i-communicate-between-java-and-c-sharp-using-just-sockets

package app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static app.SocketToUnity.sendUpdate;
import entity.Main;
import java.net.SocketException;
import org.json.JSONObject;

/**
 * Hosts a socket server for sending information about the game to Unity3D.
 * @author dion
 */
public class SocketToUnity {
    
    private static final int HOST_PORT = 5242;
    
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static OutputStream os;
    
    public static void run()
    {
        try {
            System.out.println("Starting socket server...");

            serverSocket = new ServerSocket(HOST_PORT, 1);
            socket = serverSocket.accept();

            System.out.println("Client connected...");

            
            new Thread() { 
                @Override
                public void run() {
                    
                    sendStart();
                    while(true)
                    {
                        try {
                            sendUpdate();
                            sleep(300);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            
            }.start();

        } catch (IOException ex) {
            System.out.println("Failed to start socket!");
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
            
        }

    }
    
    
    public static void sendStart()
    {
        //TODO 
        //SEND PLAYER INFO && STUFF
    }
    
    public static void sendUpdate()
    {
        //SEND MAP
        
        try {
            
            os = socket.getOutputStream();
            
            // prepare sending
            JSONObject json = new JSONObject(Main.self.getGame(0).getMap());
            
            String toSend = json.toString();
            byte[] toSendBytes = toSend.getBytes();
            int toSendLen = toSendBytes.length;
            byte[] toSendLenBytes = new byte[4];
            toSendLenBytes[0] = (byte)(toSendLen & 0xff);
            toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
            toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
            toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
            
            
            //send to client
            System.out.println("Sending to Unity : " + toSend);
            os.write(toSendLenBytes);
            os.write(toSendBytes);
            
            
        } catch (SocketException ex) {

            //UNITY3D CLOSED WHILE RUNNING
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection with Unity broke! Restarting socket!");
            restartSocket();
            
        } catch (IOException ex) {
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
    }
    
    public static void restartSocket()
    {
        try {
            os.close();
            socket.close();
            serverSocket.close();
            run();
        } catch (IOException ex) {
            Logger.getLogger(SocketToUnity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
