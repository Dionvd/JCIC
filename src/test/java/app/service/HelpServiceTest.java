package app.service;

import app.dao.StarterpackRepository;
import app.entity.Starterpack;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 *
 * @author dion
 */
@RunWith(MockitoJUnitRunner.class)
public class HelpServiceTest {
    
    @InjectMocks
    private HelpService helpService;

    @Mock
    private StarterpackRepository starterpackRepository;
    
    Starterpack starterpack;
    
    
    @Before
    public void init() {
        starterpack = new Starterpack("Java package", "Java", "example.com", "");
    }
    
    @Test
    public void testGetStarterpackByLanguage() throws Exception {
        
        when(starterpackRepository.findByLanguageIgnoreCase("Java")).thenReturn(starterpack);
        Starterpack value = helpService.getStarterpackByLanguage("Java");
        verify(starterpackRepository).findByLanguageIgnoreCase("Java");
        assertEquals(starterpack, value);
    }

    @Test
    public void testGetAllStarterpacks() {
        
    }
    
}
