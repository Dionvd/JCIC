package app.rest;

import app.SessionHandler;
import app.entity.JsonWrapper;
import app.entity.WaitingQueue;
import app.exceptions.FailedLoginException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for getting and subscribing to the waiting queue.
 * @author dion
 */
@RestController
public class QueueResource {

    
    public static WaitingQueue waitingQueue = new WaitingQueue();
    
    /**
     * Get the current waiting queue.
     * @return WaitingQueue 
     */
    @RequestMapping(value = "/queue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public WaitingQueue queue() {
        return waitingQueue;
    }

    /**
     * Gets the queue position of a specific player.
     * @param playerId
     * @return index of playerId in the WaitingQueue.
     */
    @RequestMapping(value = "/queue/{playerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper queuePositionOfPlayer(@PathVariable(value = "playerId") String playerId) {
        
        int i = ResourceMethods.parseInt(playerId);
        
        return new JsonWrapper(waitingQueue.getPositionOfPlayer(i)+"");

    }
    
    /**
     * Checks  player.
     * @param playerId
     * @param sessionToken
     * @return index of playerId in the WaitingQueue.
     * @throws app.exceptions.FailedLoginException
     */
    @RequestMapping(value = "/queue/{playerId}/{sessionToken}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper subscribe(@PathVariable(value = "playerId") String playerId, @PathVariable(value = "sessionToken") String sessionToken) throws FailedLoginException {
        
        long i = ResourceMethods.parseLong(playerId);

        //Verify sessionToken
        boolean success = SessionHandler.checkSessionToken(i, sessionToken);
        
        if (!success) { throw new FailedLoginException(); }
        int queuePos;
        
        try {
            //get player position
            queuePos = waitingQueue.getPositionOfPlayer(i);
        }
        catch(Exception e) {
            //Player is not yet in queue, add him.
            waitingQueue.getPlayerIds().add(i);
            queuePos = waitingQueue.getPlayerIds().size();
        }
        
        return new JsonWrapper(queuePos+"");
        
        

    }
}
