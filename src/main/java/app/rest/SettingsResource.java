package app.rest;

import app.entity.Settings;
import app.exception.NotFoundException;
import app.service.SettingsService;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for getting the global settings. Settings are
 * not to be confused with RoundRules. RoundRules differ with each round of a
 * game whereas Settings remain the same. That said, RoundRules might be
 * partially derived from the Settings.
 *
 * @author dion
 */
@RestController
@RequestMapping(value = "/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class SettingsResource {

    @Inject
    SettingsService settingsService;

    /**
     * Gets the current settings, as chosen by the admin panel.
     *
     * @return settings
     * @throws NotFoundException
     */
    @RequestMapping(value = "")
    public Settings settings() throws NotFoundException {
        return settingsService.get();
    }

}
