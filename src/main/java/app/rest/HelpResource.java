package app.rest;

import app.entity.Starterpack;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import app.service.HelpService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RESTful web service resource for getting the global settings. Settings are
 * not to be confused with MatchRules. MatchRules differ with each round of a
 * game whereas Settings remain the same. That said, MatchRules might be
 * partially derived from the Settings.
 *
 * @author dion
 */
@RestController
public class HelpResource {

    @Inject
    private HelpService helpService;

    /**
     * Gets the current settings, as chosen by the admin panel.
     *
     * @return settings
     */
    @RequestMapping(value = "/starterpacks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Starterpack> starterpacks() {
        return helpService.findAll();
    }

    /**
     * Gets the current settings, as chosen by the admin panel.
     *
     * @return settings
     */
    @RequestMapping(value = "/starterpacks/{language}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Starterpack getStarterpackOfLanguage(@PathVariable(value = "language") String language) {
        return helpService.findByLanguage(language);
    }

}
