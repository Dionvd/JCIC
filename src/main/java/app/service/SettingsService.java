package app.service;

import app.dao.SettingsRepository;
import app.entity.Settings;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author dion
 */
@Service
public class SettingsService {

    @Inject
    SettingsRepository settingsRep;

    public void save(Settings settings) {
        settingsRep.save(settings);
    }
}
