//https://www.leveluplunch.com/java/tutorials/014-post-json-to-spring-rest-webservice/
package app.service;

import app.FindException;
import app.dao.PlayerRepository;
import app.entity.Player;
import app.exception.BlockedException;
import app.exception.FailedRegisterException;
import app.exception.FailedLoginException;
import app.exception.NotFoundException;
import app.object.LoginCredentials;
import app.object.RegisterCredentials;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * Responsible for handling Player logic when a RESTful web service is called
 * and accessing related JPA repositories appropriately.
 *
 * @author dion
 */
@Service
public class PlayerService {

    private static final int BLOCKED_ON_FAILED_LOGIN_ATTEMPTS = 5;
    private static final int BLOCKED_TIMEOUT_MINUTES = 5;
    private static final int SESSION_LENGTH_IN_SECONDS = 120;

    @Inject
    PlayerRepository playerRep;

    /**
     * Tries to login the player with the given Credentials, storing the session
     * ID. If it fails the failed login count goes up by one, potentially
     * blocking the email.
     *
     * @param credentials
     * @param session
     * @throws FailedLoginException when login fails.
     * @throws BlockedException when the email is blocked.
     */
    public void Login(LoginCredentials credentials, HttpSession session) throws FailedLoginException, BlockedException {

        List<Player> players = playerRep.findByEmailIgnoreCase(credentials.getEmail());

        for (Player player : players) {
            //Email matches, check if this user is blocked or not.
            if (player.isBlocked() && !player.checkUnblocked()) {
                //user is blocked
                session.invalidate();
                throw new BlockedException();
            }
            //user is not blocked, check if password matches.
            if (player.getPassword().equals(credentials.getPassword())) {
                //credentials match, assign session
                player.setSessionId(session.getId());
                playerRep.save(player);
                //login succesful
                return;
            } else {
                //credentials do not match, login failed. If too many failed login attempts the user becomes blocked.
                player.incrementFailedLoginCount();

                //block account
                if (player.getFailedLoginCount() == BLOCKED_ON_FAILED_LOGIN_ATTEMPTS) {
                    player.block(BLOCKED_TIMEOUT_MINUTES);
                    session.invalidate();
                    throw new BlockedException();
                }
            }
        }
        session.invalidate();
        throw new FailedLoginException();
    }

    /**
     * Creates a new Player account. Checks if the name and email credentials
     * are already in use, if not, the player account is created.
     *
     * @param c (credentials)
     * @throws FailedRegisterException
     */
    public void registerWithCredentials(RegisterCredentials c) throws FailedRegisterException {

        List<Player> playersWithSameName = playerRep.findByNameIgnoreCase(c.getName());
        List<Player> playersWithSameEmail = playerRep.findByEmailIgnoreCase(c.getEmail());

        if (playersWithSameName.size() > 0 || playersWithSameEmail.size() > 0) {
            throw new FailedRegisterException();
        }

        playerRep.save(new Player(c));
    }

    /**
     * Checks if the sessionToken matches with the stored sessionToken.
     *
     * @param playerId
     * @param sessionToken
     * @return
     * @throws FailedLoginException
     */
    public boolean checkSessionToken(Long playerId, String sessionToken) throws FailedLoginException {

        Player p = playerRep.findOne(playerId);
        if (p == null) {
            throw new FailedLoginException();
        }
        return p.getSessionId().equals(sessionToken);
    }

    /**
     * Gets all players.
     *
     * @return
     */
    public Iterable<Player> findAll() {
        return playerRep.findAll();
    }

    /**
     * Gets one player with matching ID.
     *
     * @param l
     * @return
     * @throws NotFoundException
     */
    public Player findOne(long l) throws NotFoundException {

        return FindException.notFoundOnNull(playerRep.findOne(l));
    }

    /**
     * Gets one player with matching Name.
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    public Iterable<Player> findByName(String name) throws NotFoundException {
        return FindException.notFoundOnEmpty(playerRep.findByNameIgnoreCase(name));
    }

}
