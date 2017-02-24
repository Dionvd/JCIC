package app.rest;

import app.dao.StarterpackRepository;
import app.entity.JsonWrapper;
import app.entity.Starterpack;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for getting the global settings.
 * Settings are not to be confused with MatchRules. MatchRules differ with each round of a game whereas Settings remain the same.
 * That said, MatchRules might be partially derived from the Settings.
 * @author dion
 */
@RestController
public class StarterpackResource {
    
    @Inject
    StarterpackRepository starterpackRep;
    
    /**
     * Gets the current settings, as chosen by the admin panel.
     * @return settings
     */
    @RequestMapping(value = "/starterpacks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Starterpack> starterpacks() {
        return starterpackRep.findAll();
    }
    

}
