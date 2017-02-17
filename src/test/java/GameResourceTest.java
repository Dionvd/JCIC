
import entity.*;
import exceptions.*;
import org.junit.*;
import rest.*;

/**
 * RESTful Web Service Unit Test of Game Resource.
 * @author dion
 */

public class GameResourceTest extends Assert {
   
    private GameResource gr = new GameResource();
    
    private static final int TEST_GAME_ID = 1000;
    private static final int TEST_PLAYER_ID = 1000;
    private static final int TEST_MAP_SIZE = 10;
    private static final int TEST_TURN = 4;
    private static final String BAD_STRING = "abc";
    
    @Test
    public void getGames() {
      
        assertTrue(gr.getGames() == Main.self.getGames());

        assertEquals(gr.getGames().size(), 1);
        Main.self.getGames().add(new Match(Main.self.getSettings()));
        assertEquals(gr.getGames().size(), 2);
        Main.self.getGames().add(new Match(Main.self.getSettings()));
        assertEquals(gr.getGames().size(), 3);
        
        assertTrue(gr.getGames().contains(Main.self.getGameById(TEST_GAME_ID)));
        assertTrue(gr.getGames() == Main.self.getGames());
        
    }
    
    @Test
    public void getGame() {
        
        assertNotNull(gr.getGame(TEST_GAME_ID+""));
        assertTrue(gr.getGame(TEST_GAME_ID+"").getPlayerCount() > 0);
        assertTrue(!gr.getGame(TEST_GAME_ID+"").getMap().getNodes().isEmpty());
          
        try { gr.getGame("0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGame("abc"); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
    }

    @Test
    public void getGameMap() {
        
        assertNotNull(gr.getGameMap(TEST_GAME_ID+""));
        assertTrue(gr.getGameMap(TEST_GAME_ID+"").getWidth()> 0);        
        assertTrue(gr.getGameMap(TEST_GAME_ID+"").getHeight() > 0);
        assertTrue(!gr.getGameMap(TEST_GAME_ID+"").getNodes().isEmpty());
        
        try { gr.getGameMap("0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGameMap("abc"); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
    }

    @Test
    public void getGameMapRow() {
        
        assertNotNull(gr.getGameMapRow(TEST_GAME_ID+"", "0"));  
        assertTrue(gr.getGameMapRow(TEST_GAME_ID+"", "0").size() > 0);
        assertNotNull(gr.getGameMapRow(TEST_GAME_ID+"", Main.self.getGameById(1000).getMap().getHeight()-1+""));
        assertEquals(gr.getGameMapRow(TEST_GAME_ID+"", "0").size(), gr.getGameMapRow("1000", "1").size());
        
        try { gr.getGameMapRow("0", "0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGameMapRow("0", TEST_MAP_SIZE+""); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGameMapRow(TEST_GAME_ID+"", TEST_MAP_SIZE+""); fail("expected NotFoundOutOfBoundsException"); }
        catch(NotFoundOutOfBoundsException e) { }
         
        try { gr.getGameMapRow("abc", "0"); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
        
        try { gr.getGameMapRow(TEST_GAME_ID+"", "abc"); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
    }

    @Test
    public void getGameMapNode() {
        assertNotNull(gr.getGameMapNode(TEST_GAME_ID+"", "0", "0"));        
        assertNotNull(gr.getGameMapNode(TEST_GAME_ID+"", (TEST_MAP_SIZE-1)+"", (TEST_MAP_SIZE-1)+""));
        assertTrue(gr.getGameMapNode(TEST_GAME_ID+"", "0", "0").getOwnerId() != 0);         
        assertTrue(gr.getGameMapNode(TEST_GAME_ID+"", (TEST_MAP_SIZE-1)+"", (TEST_MAP_SIZE-1)+"").getOwnerId() != 0); 

     try { gr.getGameMapNode("0", "0", "0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGameMapNode("0", TEST_MAP_SIZE+"", "0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGameMapNode(BAD_STRING, "0", "0"); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
        
        try { gr.getGameMapNode(TEST_GAME_ID+"", BAD_STRING, "0"); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
        
        try { gr.getGameMapNode(TEST_GAME_ID+"", "0", BAD_STRING); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
        
        
        try { gr.getGameMapNode(TEST_GAME_ID+"", "0", TEST_MAP_SIZE+""); fail("expected NotFoundOutOfBoundsException"); }
        catch(NotFoundOutOfBoundsException e) { }
        
        try { gr.getGameMapNode(TEST_GAME_ID+"", TEST_MAP_SIZE+"", "0"); fail("expected NotFoundOutOfBoundsException"); }
        catch(NotFoundOutOfBoundsException e) { }
    }

    @Test
    public void getGamePlayers() {
        int size = gr.getGamePlayers(TEST_GAME_ID+"").size();
        assertTrue(size > 0);
        
        int newId = 1003;
        Main.self.getGameById(TEST_GAME_ID).getPlayerIds().add(newId);
        assertTrue(size+1 == gr.getGamePlayers(TEST_GAME_ID+"").size());
        
        try { gr.getGamePlayers("0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGamePlayers(BAD_STRING); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
        
    }
    
    @Test
    public void getGameTurn() {
        JsonWrapper wrapper = gr.getGameTurn(TEST_GAME_ID+"");
        int turnCount = (int)wrapper.getValue();
        assertTrue(turnCount == TEST_TURN);
        
        try { gr.getGameTurn("0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGameTurn(BAD_STRING); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
    }

    @Test
    public void getGameRules() {

        MatchRules rules = gr.getGameRules(TEST_GAME_ID+"");
        assertTrue(!rules.getActionCosts().isEmpty());
        assertTrue(rules.getActionCosts().get(Action.SPREAD) > 0);
        
        try { gr.getGameRules("0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { gr.getGameRules(BAD_STRING); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
    }
}
