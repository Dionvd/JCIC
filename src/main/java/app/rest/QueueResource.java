package app.rest;

import app.FindException;
import app.exception.FailedLoginException;
import app.exception.NotANumberException;
import app.exception.NotFoundException;
import app.object.JsonWrapper;
import app.object.WaitingQueue;
import app.service.PlayerService;
import app.service.QueueService;
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
@RequestMapping(value = "/queue", produces = MediaType.APPLICATION_JSON_VALUE)
public class QueueResource {

    @Inject
    private QueueService queueService;

    @Inject
    private PlayerService playerService;

    /**
     * Get the current waiting queue.
     *
     * @return WaitingQueue
     */
    @RequestMapping(value = "")
    public WaitingQueue queue() {
        return queueService.get();
    }

    /**
     * Gets the queue position of a specific player.
     *
     * @param playerId
     * @return index of playerId in the WaitingQueue.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET)
    public JsonWrapper queuePositionOfPlayer(@PathVariable(value = "playerId") String playerIdString) throws NotANumberException, NotFoundException {

        Long playerId = FindException.parseLong(playerIdString);

        return new JsonWrapper(queueService.getPositionOfPlayer(playerId) + "");

    }

    /**
     * Player joins the waiting queue, if he is not already in the queue Returns
     * queue position of player.
     *
     * @param playerId
     * @param sessionToken
     * @return index of playerId in the WaitingQueue.
     * @throws FailedLoginException
     * @throws NotANumberException
     */
    @RequestMapping(value = "/{playerId}/{sessionToken}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper joinQueue(@PathVariable(value = "playerId") String playerIdString, @PathVariable(value = "sessionToken") String sessionToken) throws FailedLoginException, NotANumberException {

        long playerId = FindException.parseLong(playerIdString);

        //Verify sessionToken
        playerService.checkSessionToken(playerId, sessionToken);

        //Join queue
        int queuePos = queueService.joinQueue(playerId);

        return new JsonWrapper(queuePos + "");

    }
}
