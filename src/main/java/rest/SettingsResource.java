package rest;

import entity.Main;
import entity.Settings;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful web service resource for getting the global settings.
 * Settings are not to be confused with GameRules. GameRules differ with each round of a game whereas Settings remain the same.
 * That said, GameRules might be partially derived from the Settings.
 * @author dion
 */
@RestController
public class SettingsResource {

    /**
     * Gets the current settings, as chosen by the admin panel.
     * @return settings
     */
    @RequestMapping(value = "/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Settings settings() {
        return Main.self.getSettings();
    }

}
