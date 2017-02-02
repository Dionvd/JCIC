package rest;

import entity.Main;
import entity.JsonWrapper;
import entity.WaitingQueue;
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

    /**
     * Get the current waiting queue
     * @return WaitingQueue 
     */
    @RequestMapping(value = "/queue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public WaitingQueue queue() {
        return Main.self.getWaitingQueue();
    }

    /**
     * Gets the queue position of a specific player
     * @param playerId
     * @return index of playerId in the WaitingQueue.
     */
    @RequestMapping(value = "/queue/{playerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper queuePositionOfPlayer(@PathVariable(value = "playerId") int playerId) {
        return new JsonWrapper(Main.self.getWaitingQueue().getPositionOfPlayer(playerId)+"");
    }
}
