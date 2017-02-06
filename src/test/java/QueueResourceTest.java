
import entity.*;
import exceptions.NotANumberException;
import exceptions.NotFoundException;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import rest.QueueResource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dion
 */
public class QueueResourceTest extends Assert {
    
    final int TEST_ID = 1002;
    final int TEST_ID_NOT_IN_QUEUE = 1000;
    final String BAD_ID = "123";
    final int TEST_POS = 1;
    
    
    
    QueueResource qr = new QueueResource();
    
    
    @Test
    public void queue() {
        WaitingQueue q = qr.queue();
        assertTrue(!q.getPlayerIds().isEmpty());
        assertEquals(q, Main.self.getWaitingQueue());
        int size = q.getPlayerIds().size();
        Main.self.getWaitingQueue().getPlayerIds().add(0);
        assertEquals(size+1,qr.queue().getPlayerIds().size());
    }
    
    @Test
    public void queuePositionOfPlayer() {
        assertEquals(TEST_POS+"", qr.queuePositionOfPlayer(TEST_ID+"").getValue().toString());
        
        try { qr.queuePositionOfPlayer("0"); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        //check if this exists.
        Main.self.getPlayerById(TEST_ID_NOT_IN_QUEUE);
        
        try { qr.queuePositionOfPlayer(TEST_ID_NOT_IN_QUEUE+""); fail("expected NotFoundException"); }
        catch(NotFoundException e) { }
        
        try { qr.queuePositionOfPlayer("abc"); fail("expected NotANumberException"); }
        catch(NotANumberException e) { }
    }
    
    
}
