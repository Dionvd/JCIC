package app.rest;

import app.entity.Settings;
import app.service.SettingsService;
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
public class SettingsResourceTest {
    
    
    @InjectMocks
    private SettingsResource settingsResource;
    
    @Mock
    private SettingsService settingsService;
    
    
    Settings settings;
    
    @Before
    public void init() throws Exception {
        settings = new Settings();
    }

    @Test
    public void testSettings() throws Exception {
        when(   settingsService.get()).thenReturn( settings   );
        Settings value =       settingsResource.settings();
        verify(    settingsService).get();
        assertEquals( settings   , value    );    
    }
    
}
