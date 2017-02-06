
import org.junit.*;
import rest.*;
import entity.*;
import exceptions.NotANumberException;
import org.junit.rules.ExpectedException;

/**
 *
 * @author dion
 */

public class GameResourceTest extends Assert {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test(expected = NotANumberException.class)
    public void testException()
    {
        GameResource gr = new GameResource();
        Game g = gr.getGame("0a");
    }
}
