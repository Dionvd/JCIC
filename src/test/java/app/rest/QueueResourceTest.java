package app.rest;

import app.entity.Player;
import app.dto.JsonWrapper;
import app.dto.RegisterCredentials;
import app.dto.WaitingQueue;
import app.service.PlayerService;
import app.service.QueueService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author dion
 */
@RunWith(MockitoJUnitRunner.class)
public class QueueResourceTest {

    @InjectMocks
    private QueueResource queueResource;

    @Mock
    private QueueService queueService;

    @Mock
    private PlayerService playerService;
    
    private static final String TEST_ID_STRING = "123";
    private static final long TEST_ID_LONG = 123L;
    private static final int TEST_INT = 123;

    WaitingQueue waitingQueue;

    private Player player;
    private Player player2;

    @Before
    public void init() throws Exception {
        waitingQueue = new WaitingQueue();

        player = new Player(new RegisterCredentials("", "", ""));
        player2 = new Player(new RegisterCredentials("", "", ""));
        waitingQueue.getPlayers().add(player);
        waitingQueue.getPlayers().add(player2);

    }

    @Test
    public void testQueue() {
        when(queueService.get()).thenReturn(waitingQueue);
        WaitingQueue value = queueResource.queue();
        verify(queueService).get();
        assertEquals(waitingQueue, value);
    }

    @Test
    public void testQueuePositionOfPlayer() throws Exception {
        when(queueService.getPositionOfPlayer(TEST_ID_LONG)).thenReturn(TEST_INT);
        JsonWrapper value = queueResource.queuePositionOfPlayer(TEST_ID_STRING);
        verify(queueService).getPositionOfPlayer(TEST_ID_LONG);
        assertEquals(TEST_INT+"", value.getValue());
    }

    @Test
    public void testJoinQueue() throws Exception {
        when(queueService.joinQueue(TEST_ID_LONG)).thenReturn(TEST_INT);
        when(playerService.checkToken(TEST_ID_LONG, TEST_ID_STRING)).thenReturn(true);

        JsonWrapper value = queueResource.joinQueue(TEST_ID_STRING, TEST_ID_STRING);
        verify(queueService).joinQueue(TEST_ID_LONG);        
        verify(playerService).checkToken(TEST_ID_LONG, TEST_ID_STRING);

        assertEquals(TEST_INT+"", value.getValue());
    }

}
