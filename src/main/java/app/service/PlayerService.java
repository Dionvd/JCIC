//https://www.leveluplunch.com/java/tutorials/014-post-json-to-spring-rest-webservice/
package app.service;

import app.dao.PlayerRepository;
import app.entity.LoginCredentials;
import app.entity.RegisterCredentials;
import app.entity.Player;
import app.exception.BlockedException;
import app.exception.FailedRegisterException;
import app.exception.FailedLoginException;
import app.exception.NotFoundException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
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

    public PlayerService() {
    }

    /**
     * Tries to login the player with the given Credentials. If it fails the
     * failed login count goes up by one, potentially blocking the player.
     *
     * @param c
     * @return player matching id
     * @throws FailedLoginException
     * @throws NotFoundException occurs when no id exists (error 404).
     */
    public Player getPlayerByLogin(LoginCredentials c) throws FailedLoginException {

        List<Player> players = playerRep.findByEmail(c.getEmail());

        for (Player p : players) {
            //Email matches, check if this user is blocked or not.
            if (p.isBlocked() && !p.checkUnblocked()) {
                //user is blocked, check if he should be blocked.
                throw new BlockedException();
            }
            //user is not blocked, check if password matches.
            if (p.getPassword().equals(c.getPassword())) {
                //credentials match, login succesful
                return p;
            } else {
                //credentials do not match, login failed. If too many failed login attempts the user becomes blocked.
                p.incrementFailedLoginCount();

                //block account
                if (p.getFailedLoginCount() == BLOCKED_ON_FAILED_LOGIN_ATTEMPTS) {
                    p.block(BLOCKED_TIMEOUT_MINUTES);
                    throw new BlockedException();
                }
            }
        }
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

        List<Player> playersWithSameName = playerRep.findByName(c.getName());
        List<Player> playersWithSameEmail = playerRep.findByEmail(c.getEmail());

        if (playersWithSameName.size() > 0 || playersWithSameEmail.size() > 0) {
            throw new FailedRegisterException();
        }

        playerRep.save(new Player(c));
    }

    public boolean checkSessionToken(Long playerId, String sessionToken) {

        Player p = playerRep.findOne(playerId);
        return p.getSessionId().equals(sessionToken);
    }

    public void save(Player player) {
        playerRep.save(player);
    }

    public Iterable<Player> findAll() {
        return playerRep.findAll();
    }

    public Player findOne(long l) throws NotFoundException {

        Player player = playerRep.findOne(l);
        if (player == null) {
            throw new NotFoundException();
        }
        return player;
    }

    public Iterable<Player> findByName(String name) {
        return playerRep.findByName(name);
    }

}
