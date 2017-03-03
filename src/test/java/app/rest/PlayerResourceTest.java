package app.rest;

import app.entity.Player;
import app.object.JsonWrapper;
import app.object.LoginCredentials;
import app.object.RegisterCredentials;
import app.service.PlayerService;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author dion
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerResourceTest {

    @InjectMocks
    private PlayerResource playerResource;

    @Mock
    private PlayerService playerService;

    @Mock
    private HttpSession httpSession;

    private static final String TEST_ID_STRING = "123";
    private static final long TEST_ID_LONG = 123L;

    private ArrayList<Player> players;
    private Player player;
    private Player player2;
    private LoginCredentials loginCredentials;
    private RegisterCredentials registerCredentials;
    
    @Before
    public void init() throws Exception {

        players = new ArrayList<>();
        player = new Player(new RegisterCredentials("", "", ""));
        player2 = new Player(new RegisterCredentials("", "", ""));
        players.add(player);
        players.add(player2);
        loginCredentials = new LoginCredentials(TEST_ID_STRING, TEST_ID_STRING);
        registerCredentials = new RegisterCredentials(TEST_ID_STRING, TEST_ID_STRING, TEST_ID_STRING);
        
    }

    @Test
    public void testGetAllPlayers() {
        when(playerService.findAll()).thenReturn(players);
        Iterable<Player> value = playerResource.getAllPlayers();
        verify(playerService).findAll();
        assertEquals(players, value);
    }

    @Test
    public void testGetPlayer() throws Exception {
        when(playerService.findOne(TEST_ID_LONG)).thenReturn(player);
        Player value = playerResource.getPlayer(TEST_ID_STRING);
        verify(playerService).findOne(TEST_ID_LONG);
        assertEquals(player, value);
    }

    @Test
    public void testLogin() throws Exception {

        when(httpSession.getId()).thenReturn(TEST_ID_STRING);
        JsonWrapper value = playerResource.login(loginCredentials, httpSession);
        verify(httpSession).getId();
        assertEquals(TEST_ID_STRING, value.getValue());
    }

    @Test
    public void testRegister() throws Exception {
        JsonWrapper value = playerResource.register(registerCredentials);
        verify(playerService).registerWithCredentials(registerCredentials);
        assertTrue(value.getValue() != null);
    }

}
