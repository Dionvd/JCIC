package app.rest;

import app.service.PlayerService;
import app.entity.JsonWrapper;
import app.entity.WaitingQueue;
import app.exception.FailedLoginException;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for getting and subscribing to the waiting
 * queue.
 *
 * @author dion
 */
@RestController
public class QueueResource {

    public static WaitingQueue waitingQueue = new WaitingQueue();

    @Inject
    private PlayerService playerService;

    /**
     * Get the current waiting queue.
     *
     * @return WaitingQueue
     */
    @RequestMapping(value = "/queue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public WaitingQueue queue() {
        return waitingQueue;
    }

    /**
     * Gets the queue position of a specific player.
     *
     * @param playerId
     * @return index of playerId in the WaitingQueue.
     */
    @RequestMapping(value = "/queue/{playerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper queuePositionOfPlayer(@PathVariable(value = "playerId") String playerId) {

        int i = ResourceMethods.parseInt(playerId);

        return new JsonWrapper(waitingQueue.getPositionOfPlayer(i) + "");

    }

    /**
     * Player joins the waiting queue, if he is not already in the queue Returns
     * queue position of player.
     *
     * @param playerId
     * @param sessionToken
     * @return index of playerId in the WaitingQueue.
     * @throws app.exception.FailedLoginException
     */
    @RequestMapping(value = "/queue/{playerId}/{sessionToken}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper joinQueue(@PathVariable(value = "playerId") String playerId, @PathVariable(value = "sessionToken") String sessionToken) throws FailedLoginException {

        long i = ResourceMethods.parseLong(playerId);

        //Verify sessionToken
        boolean success = playerService.checkSessionToken(i, sessionToken);

        if (!success) {
            throw new FailedLoginException();
        }

        //Get queue posiiton
        int queuePos;

        try {
            queuePos = waitingQueue.getPositionOfPlayer(i);
        } catch (Exception e) {
            //Player is not yet in queue, add him.
            waitingQueue.getPlayerIds().add(i);
            queuePos = waitingQueue.getPlayerIds().size();
        }

        return new JsonWrapper(queuePos + "");

    }
}
