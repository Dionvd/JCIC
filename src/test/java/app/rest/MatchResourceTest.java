package app.rest;

import app.entity.*;
import app.exception.NotANumberException;
import app.exception.NotFoundException;
import app.enums.Action;
import app.dto.JsonWrapper;
import app.dto.Move;
import app.enums.MoveDirection;
import app.dto.MoveList;
import app.dto.RegisterCredentials;
import app.service.MatchService;
import app.service.NodeService;
import java.awt.Point;
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
    private NodeService nodeService;

    @Mock
    private MatchService matchService;

    private static final String TEST_ID_STRING = "123";
    private static final long TEST_ID_LONG = 123L;
    private static final String TEST_ZERO_STRING = "0";
    private static final String TEST_NODE_ID_STRING = "1230000";
    private static final long TEST_NODE_ID_LONG = 1230000L;

    private Iterable<Match> matches;

    private Match match;

    private Node node;

    private ArrayList<Player> players;
    private Player player;
    private Player player2;
    private ArrayList<Move> moves;

    @Before
    public void init() throws Exception {

        match = new Match(new Settings());

        match.setMap(new MatchMap(new Point(10, 10), match.getId()));
        match.setTurn(4);
        match.setId(TEST_ID_LONG);

        node = match.getMap().getNode(0, 0);
        node.setOwnerId(0L);
        node.setPower(50);

        Node startNode2 = match.getMap().getNode(9, 9);
        startNode2.setOwnerId(1);
        startNode2.setPower(50);

        players = new ArrayList<>();
        player = new Player(new RegisterCredentials("", "",""));
        player2 = new Player(new RegisterCredentials("", "",""));
        players.add(player);
        players.add(player2);
        match.setPlayers(players);

        moves = new ArrayList<>();
        moves.add(new Move(new Point(0,0),Action.SLEEP,MoveDirection.CENTRAL));
        moves.add(new Move(new Point(0,0),Action.SLEEP,MoveDirection.CENTRAL));
        moves.add(new Move(new Point(0,0),Action.SLEEP,MoveDirection.CENTRAL));
        
        ArrayList<Match> matchesList = new ArrayList<>();
        matchesList.add(match);
        matches = matchesList; 
    }

    @Test
    public void testGetMatches() {

        when(matchService.findAll()).thenReturn(matches);
        Iterable<Match> value = matchResource.getMatches();
        verify(matchService).findAll();
        assertEquals(matches, value);
    }

    @Test
    public void testGetMatch() throws NotFoundException, NotANumberException {

        when(matchService.findOne(TEST_ID_LONG)).thenReturn(match);
        Match value = matchResource.getMatch(TEST_ID_STRING);
        verify(matchService).findOne(TEST_ID_LONG);
        assertEquals(TEST_ID_LONG, value.getId());
    }

    @Test
    public void testGetMatchMap() throws NotFoundException, NotANumberException {

        when(matchService.findOne(TEST_ID_LONG)).thenReturn(match);
        MatchMap value = matchResource.getMatchMap(TEST_ID_STRING);
        verify(matchService).findOne(TEST_ID_LONG);
        assertEquals(match.getMap(), value);
    }

    @Test
    public void testGetMatchMapNode() throws NotFoundException, NotANumberException {

        when(nodeService.getNode(TEST_ID_LONG, 0, 0)).thenReturn(node);
        Node value = matchResource.getMatchMapNode(TEST_ID_STRING, TEST_ZERO_STRING, TEST_ZERO_STRING);
        verify(nodeService).getNode(TEST_ID_LONG, 0, 0);
        assertEquals(node, value);
    }

    @Test
    public void testGetMatchPlayers() throws NotANumberException, NotFoundException {

        when(matchService.findOne(TEST_ID_LONG)).thenReturn(match);
        Iterable<Player> value = matchResource.getMatchPlayers(TEST_ID_STRING);
        verify(matchService).findOne(TEST_ID_LONG);
        assertEquals(players, value);
    }

    @Test
    public void testGetNode() throws Exception {

        when(nodeService.getNode(TEST_NODE_ID_LONG)).thenReturn(node);
        Node value = matchResource.getNode(TEST_NODE_ID_STRING);
        verify(nodeService).getNode(TEST_NODE_ID_LONG);
        assertEquals(node, value);
    }

    @Test
    public void testGetMatchPlayer() throws Exception {

        when(matchService.findMatchPlayer(TEST_ID_LONG,TEST_ID_LONG)).thenReturn(player);
        Player value = matchResource.getMatchPlayer(TEST_ID_STRING, TEST_ID_STRING);
        verify(matchService).findMatchPlayer(TEST_ID_LONG,TEST_ID_LONG);
        assertEquals(player, value);

    }

    @Test
    public void testPostMoves() throws Exception {
        
        JsonWrapper value = matchResource.postMoves(TEST_ID_STRING, TEST_ID_STRING, moves);
       //TODO verify(matchService).postMoves(TEST_ID_LONG, TEST_ID_LONG, new MoveList(moves));
        assertNotNull(value);
    }

    @Test
    public void testGetMatchTurn() throws Exception {

        when(matchService.findOne(TEST_ID_LONG)).thenReturn(match);
        JsonWrapper value = matchResource.getMatchTurn(TEST_ID_STRING);
        verify(matchService).findOne(TEST_ID_LONG);
        assertEquals(match.getTurn(), value.getValue());
    }

    @Test
    public void testGetMatchRules() throws Exception {

        when(matchService.findOne(TEST_ID_LONG)).thenReturn(match);
        MatchRules value = matchResource.getMatchRules(TEST_ID_STRING);
        verify(matchService).findOne(TEST_ID_LONG);
        assertEquals(match.getGameRules(), value);
    }

}
