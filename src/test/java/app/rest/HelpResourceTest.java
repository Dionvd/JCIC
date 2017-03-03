package app.rest;

import app.entity.Starterpack;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import app.service.HelpService;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author dion
 */
@RunWith(MockitoJUnitRunner.class)
public class HelpResourceTest {

    @InjectMocks
    private HelpResource helpResource;

    @Mock
    private HelpService helpService;

    private static final String TEST_LANGUAGE = "Java";

    Iterable<Starterpack> starterpacks;
    Starterpack starterpack;

    @Before
    public void init() throws Exception {

        List<Starterpack> starterpackList = new ArrayList<>();
        starterpack = new Starterpack("", TEST_LANGUAGE, "");
        starterpackList.add(starterpack);
        starterpacks = starterpackList;
    }

    @Test
    public void testStarterpacks() {
        when(helpService.getAllStarterpacks()).thenReturn(starterpacks);
        Iterable<Starterpack> value = helpResource.starterpacks();
        verify(helpService).getAllStarterpacks();
        assertEquals(starterpacks, value);
    }

    @Test
    public void testGetStarterpackByLanguage() throws Exception {
        when(helpService.getStarterpackByLanguage(TEST_LANGUAGE)).thenReturn(starterpack);
        Starterpack value = helpResource.getStarterpackByLanguage(TEST_LANGUAGE);
        verify(helpService).getStarterpackByLanguage(TEST_LANGUAGE);
        assertEquals(starterpack, value);
    }

}
