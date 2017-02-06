
import entity.*;
import exceptions.NotANumberException;
import exceptions.NotFoundException;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import rest.SettingsResource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dion
 */
public class SettingsResourceTest extends Assert {
    
    final int TEST_ID = 1000;
   
    SettingsResource sr = new SettingsResource();
    
    
    @Test
    public void settings() {
        Settings settings = sr.settings();
        assertNotNull(settings);
        assertEquals(settings, Main.self.getSettings());
        assertTrue(settings.getDefaultActionCosts().get(Action.SPREAD) > 0);
    }
    
    
}
