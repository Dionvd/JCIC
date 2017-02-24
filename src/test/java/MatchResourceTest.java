//
//import app.rest.MatchResource;
//import app.exceptions.NotFoundException;
//import app.exceptions.NotFoundOutOfBoundsException;
//import app.exceptions.NotANumberException;
//import app.entity.Match;
//import app.entity.MatchRules;
//import app.entity.Action;
//import app.entity.Main;
//import app.entity.JsonWrapper;
//import org.junit.*;
//
///**
// * RESTful Web Service Unit Test of Match Resource.
// * @author dion
// */
//
//public class MatchResourceTest extends Assert {
//   
//    private MatchResource mr = new MatchResource();
//    
//    private static final int TEST_GAME_ID = 0;
//    private static final int TEST_PLAYER_ID = 0;
//    private static final int TEST_MAP_SIZE = 10;
//    private static final int TEST_TURN = 4;
//    private static final String BAD_STRING = "abc";
//    
//    @Test
//    public void getMatches() {
//      
////        assertTrue(mr.getMatches() == Main.self.getMatches());
////
////        assertEquals(mr.getMatches().size(), 1);
////        Main.self.getMatches().add(new Match(Main.self.getSettings()));
////        assertEquals(mr.getMatches().size(), 2);
////        Main.self.getMatches().add(new Match(Main.self.getSettings()));
////        assertEquals(mr.getMatches().size(), 3);
////        
////        assertTrue(mr.getMatches().contains(Main.self.getMatchById(TEST_GAME_ID)));
////        assertTrue(mr.getMatches() == Main.self.getMatches());
////        
//    }
//    
//    @Test
//    public void getMatch() {
//        
//        assertNotNull(mr.getMatch(TEST_GAME_ID+""));
//        assertTrue(mr.getMatch(TEST_GAME_ID+"").getPlayerCount() > 0);
//        assertTrue(!mr.getMatch(TEST_GAME_ID+"").getMap().getNodes().isEmpty());
//          
//        try { mr.getMatch("0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatch("abc"); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//    }
//
//    @Test
//    public void getMatchMap() {
//        
//        assertNotNull(mr.getMatchMap(TEST_GAME_ID+""));
//        assertTrue(mr.getMatchMap(TEST_GAME_ID+"").getWidth()> 0);        
//        assertTrue(mr.getMatchMap(TEST_GAME_ID+"").getHeight() > 0);
//        assertTrue(!mr.getMatchMap(TEST_GAME_ID+"").getNodes().isEmpty());
//        
//        try { mr.getMatchMap("0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchMap("abc"); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//    }
//
//    @Test
//    public void getMatchMapRow() {
//        
//        assertNotNull(mr.getMatchMapRow(TEST_GAME_ID+"", "0"));  
//        assertTrue(mr.getMatchMapRow(TEST_GAME_ID+"", "0").size() > 0);
////        assertNotNull(mr.getMatchMapRow(TEST_GAME_ID+"", Main.self.getMatchById(1000).getMap().getHeight()-1+""));
//        assertEquals(mr.getMatchMapRow(TEST_GAME_ID+"", "0").size(), mr.getMatchMapRow("1000", "1").size());
//        
//        try { mr.getMatchMapRow("0", "0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchMapRow("0", TEST_MAP_SIZE+""); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchMapRow(TEST_GAME_ID+"", TEST_MAP_SIZE+""); fail("expected NotFoundOutOfBoundsException"); }
//        catch(NotFoundOutOfBoundsException e) { }
//         
//        try { mr.getMatchMapRow("abc", "0"); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//        
//        try { mr.getMatchMapRow(TEST_GAME_ID+"", "abc"); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//    }
//
//    @Test
//    public void getMatchMapNode() {
//        assertNotNull(mr.getMatchMapNode(TEST_GAME_ID+"", "0", "0"));        
//        assertNotNull(mr.getMatchMapNode(TEST_GAME_ID+"", (TEST_MAP_SIZE-1)+"", (TEST_MAP_SIZE-1)+""));
//        assertTrue(mr.getMatchMapNode(TEST_GAME_ID+"", "0", "0").getOwnerId() != 0);         
//        assertTrue(mr.getMatchMapNode(TEST_GAME_ID+"", (TEST_MAP_SIZE-1)+"", (TEST_MAP_SIZE-1)+"").getOwnerId() != 0); 
//
//     try { mr.getMatchMapNode("0", "0", "0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchMapNode("0", TEST_MAP_SIZE+"", "0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchMapNode(BAD_STRING, "0", "0"); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//        
//        try { mr.getMatchMapNode(TEST_GAME_ID+"", BAD_STRING, "0"); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//        
//        try { mr.getMatchMapNode(TEST_GAME_ID+"", "0", BAD_STRING); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//        
//        
//        try { mr.getMatchMapNode(TEST_GAME_ID+"", "0", TEST_MAP_SIZE+""); fail("expected NotFoundOutOfBoundsException"); }
//        catch(NotFoundOutOfBoundsException e) { }
//        
//        try { mr.getMatchMapNode(TEST_GAME_ID+"", TEST_MAP_SIZE+"", "0"); fail("expected NotFoundOutOfBoundsException"); }
//        catch(NotFoundOutOfBoundsException e) { }
//    }
//
//    @Test
//    public void getMatchPlayers() {
//        int size = mr.getMatchPlayers(TEST_GAME_ID+"").size();
//        assertTrue(size > 0);
//        
//        int newId = 1003;
////        Main.self.getMatchById(TEST_GAME_ID).getPlayerIds().add(newId);
//        assertTrue(size+1 == mr.getMatchPlayers(TEST_GAME_ID+"").size());
//        
//        try { mr.getMatchPlayers("0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchPlayers(BAD_STRING); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//        
//    }
//    
//    @Test
//    public void getMatchTurn() {
//        JsonWrapper wrapper = mr.getMatchTurn(TEST_GAME_ID+"");
//        int turnCount = (int)wrapper.getValue();
//        assertTrue(turnCount == TEST_TURN);
//        
//        try { mr.getMatchTurn("0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchTurn(BAD_STRING); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//    }
//
//    @Test
//    public void getMatchRules() {
//
//        MatchRules rules = mr.getMatchRules(TEST_GAME_ID+"");
//        assertTrue(!rules.getActionCosts().isEmpty());
//        assertTrue(rules.getActionCosts().get(Action.SPREAD) > 0);
//        
//        try { mr.getMatchRules("0"); fail("expected NotFoundException"); }
//        catch(NotFoundException e) { }
//        
//        try { mr.getMatchRules(BAD_STRING); fail("expected NotANumberException"); }
//        catch(NotANumberException e) { }
//    }
//}
