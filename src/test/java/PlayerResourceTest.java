
import org.junit.Assert;
import org.junit.Test;
import rest.PlayerResource;
import entity.*;
import exceptions.NotANumberException;
import exceptions.NotFoundException;
import java.util.Collection;
import static org.junit.Assert.fail;

/**
 * RESTful Web Service Unit Test of Player Resource.
 * @author dion
 */

public class PlayerResourceTest extends Assert {
    
    private static final int TEST_ID = 1000;
    private static final String TEST_NAME = "John";
    private static final String BAD_STRING = "abc";
    
    private PlayerResource pr = new PlayerResource();
    
    @Test
    public void getAllPlayers() {
        Collection<Player> players = pr.getAllPlayers();
        assertTrue(!players.isEmpty());
        assertEquals(players, Main.self.getPlayers());
        assertTrue(players.contains(Main.self.getPlayerById(TEST_ID)));
        
        int size = pr.getAllPlayers().size();
        Main.self.getPlayers().add(new Player(new Credentials("","","")));
        assertEquals(size+1, Main.self.getPlayers().size());
    }
    
    @Test
    public void getPlayer() {
        Player player = pr.getPlayer(TEST_ID+"");
        
        assertTrue(player.getUsername().equals(TEST_NAME));
        
        
        try { pr.getPlayer("0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { pr.getPlayer(BAD_STRING); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
    }
    
}
