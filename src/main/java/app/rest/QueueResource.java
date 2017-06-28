package app.rest;

import app.util.Validate;
import app.exception.FailedLoginException;
import app.exception.NotANumberException;
import app.exception.NotFoundException;
import app.dto.JsonWrapper;
import app.dto.WaitingQueue;
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
    @RequestMapping(value = "", method = RequestMethod.GET)
    public WaitingQueue queue() {
        return queueService.get();
    }

    /**
     * Gets the queue position of a specific player.
     *
     * @param playerIdString
     * @return index of playerId in the WaitingQueue.
     * @throws NotANumberException
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET)
    public JsonWrapper queuePositionOfPlayer(@PathVariable(value = "playerId") String playerIdString) throws NotANumberException, NotFoundException {

        Long playerId = Validate.parseLong(playerIdString);

        return new JsonWrapper(queueService.getPositionOfPlayer(playerId) + "");

    }

    /**
     * Player joins the waiting queue, if he is not already in the queue Returns
     * queue position of player.
     *
     * @param playerIdString
     * @param Token
     * @return index of playerId in the WaitingQueue.
     * @throws FailedLoginException
     * @throws NotANumberException
     */
    @RequestMapping(value = "/{playerId}/{sessionToken}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public JsonWrapper joinQueue(@PathVariable(value = "playerId") String playerIdString, @PathVariable(value = "token") String token) throws FailedLoginException, NotANumberException {

        long playerId = Validate.parseLong(playerIdString);

        //Verify token
        playerService.checkToken(playerId, token);

        //Join queue
        int queuePos = queueService.joinQueue(playerId);

        return new JsonWrapper(queuePos + "");

    }
}
