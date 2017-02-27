package app.rest;


import app.dao.MatchRepository;
import app.entity.*;
import app.service.MatchService;
import java.util.ArrayList;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * RESTful Web Service Unit Test of Match Resource.
 *
 * @author dion
 */
@RunWith(MockitoJUnitRunner.class)
public class MatchResourceTest extends Assert {

    @InjectMocks
    private MatchResource matchResource;

    @Mock
    private MatchService matchService;

    @Mock
    private MatchRepository matchRepository;

    private static final String TEST_ID_STRING = "1234567890";
    private static final long TEST_ID_LONG = 1234567890L;

    private Iterable<Match> matches;
    
    private Match match;

    @Before
    public void init() throws Exception {
        
        //CREATE A MATCH
        match = new Match(new Settings());

        match.setMap(new MatchMap(10, 10, match.getId()));
        match.setTurn(4);
        match.setId(TEST_ID_LONG);

        Node startNode = match.getMap().getNode(0, 0);
        startNode.setOwnerId(0L);
        startNode.setPower(50);

        Node startNode2 = match.getMap().getNode(9, 9);
        startNode2.setOwnerId(1);
        startNode2.setPower(50);

        match.getPlayerIds().add(0L);
        match.getPlayerIds().add(1L);
        
        ArrayList<Match> matchesList = new ArrayList<Match>();
        matchesList.add(match);
        matches = matchesList;

    }
    
    @Test
    public void testGetMatches() {

        when(matchService.findAll()).thenReturn(matches);
        
        Iterable<Match> foundMatches = matchResource.getMatches();
        verify(matchService).findAll();
        assertEquals(matches, foundMatches);
    }
    
    @Test
    public void testGetMatch() {
        
        when(matchService.findOne(TEST_ID_LONG)).thenReturn(match);
        
        Match foundMatch = matchResource.getMatch(TEST_ID_STRING);
        verify(matchService).findOne(TEST_ID_LONG);
        assertEquals(TEST_ID_LONG, foundMatch.getId());
    }

    @Test
    public void testGetMatchMap() {
        
        when(matchService.findOne(TEST_ID_LONG)).thenReturn(match);
        
        MatchMap foundMatchMap = matchResource.getMatchMap(TEST_ID_STRING);
        verify(matchService).findOne(TEST_ID_LONG);
        assertEquals(match.getMap(), foundMatchMap);

    }

    @Test
    public void testGetMatchMapNode() {
        
        matchResource.getMatchMapNode(TEST_ID_STRING, TEST_ID_STRING, TEST_ID_STRING);
    }

    @Test
    public void testGetMatchPlayers() {
        
        matchResource.getMatchPlayers(TEST_ID_STRING);
    }

    @Test
    public void testGetMatchPlayer() {
        matchResource.getMatchPlayer(TEST_ID_STRING, TEST_ID_STRING);
    }

    @Test
    public void testGetMatchTurn() {
        matchResource.getMatchTurn(TEST_ID_STRING);
    }
   
    @Test
    public void testGetMatchRules() {
        matchResource.getMatchRules(TEST_ID_STRING);
    }

    
    

    //  ArrayList list = Lists.newArrayList(ms.findAll());
    //assertTrue(list.size() > 0);
//
//        assertEquals(mr.getMatches().size(), 1);
//        Main.self.getMatches().add(new Match(Main.self.getSettings()));
//        assertEquals(mr.getMatches().size(), 2);
//        Main.self.getMatches().add(new Match(Main.self.getSettings()));
//        assertEquals(mr.getMatches().size(), 3);
//        
//        assertTrue(mr.getMatches().contains(Main.self.getMatchById(TEST_GAME_ID)));
//        assertTrue(mr.getMatches() == Main.self.getMatches());
//        
//}

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

    
    
}
