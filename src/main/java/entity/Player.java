//http://stackoverflow.com/questions/28322376/exclude-some-fields-of-spring-data-rest-resource
package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author dion
 */
public class Player {

    private long id;
    private final AtomicLong idCounter = new AtomicLong(999);
    
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private int sessionId;

    private int winCount;

    
    
    /**
     * Default constructor, sets the id automatically.
     */
    public Player()
    {
        id = idCounter.incrementAndGet();
    }
    
    /**
     * normal constructor, sets username and password and sets the id automatically.
     * @param username
     * @param password
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        id = idCounter.incrementAndGet();
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param playerId
     */
    public void setId(long playerId) {
        this.id = playerId;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     *
     * @param sessionId
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    /**
     *
     * @return
     */
    public int getWinCount() {
        return winCount;
    }

    /**
     *
     * @param winCount
     */
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

}
