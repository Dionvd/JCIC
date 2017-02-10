//http://stackoverflow.com/questions/28322376/exclude-some-fields-of-spring-data-rest-resource
package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.tomcat.jni.Time;

/**
 * Player class. Contains all account and player data of one person.
 * Referenced by its id in the Game, WaitingQueue and Node classes.
 * Stored as a list in Main.
 * @author dion
 */
public class Player {

    private long id;
    private static final AtomicLong ID_COUNTER = new AtomicLong(999);
    
    private String username;

    //The email is used to login, and will not be shown to protect bots from automatically blocking login attempts.
    @JsonIgnore
    private String email;
    
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String sessionId = "";

    private int winCount;

    private boolean blocked = false;
    
    @JsonIgnore
    private long blockedTime;
    
    @JsonIgnore
    private int failedLoginCount;

    
    /**
     * Default constructor, sets the id automatically.
     */
    public Player()
    {
        id = ID_COUNTER.incrementAndGet();
    }
    
    /**
     * normal constructor, sets username and password and sets the id automatically.
     * @param credentials with email, password and name
     */
    public Player(Credentials credentials) {
        this.username = credentials.getUsername();
        this.password = credentials.getPassword();
        this.email = credentials.getEmail();
        id = ID_COUNTER.incrementAndGet();
    }

    
    public boolean checkUnblocked()
    {
        if (blockedTime < System.currentTimeMillis())
        {    
            blocked = false;
            failedLoginCount = 0;
            
        }
        return !blocked;
    }
    
    public void block(int minutes)
    {
        //blocks this player for a few minutes.
        blocked = true;
        blockedTime = System.currentTimeMillis()+1000*60*minutes;
    }
    
    public void incrementFailedLoginCount()
    {
        failedLoginCount++;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long playerId) {
        this.id = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    
}
