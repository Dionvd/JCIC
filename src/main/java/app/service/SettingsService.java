package app.service;

import app.FindException;
import app.dao.SettingsRepository;
import app.entity.Settings;
import app.exception.NotFoundException;
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

    public Settings get() throws NotFoundException {
        
        return FindException.notFoundOnNull(settingsRep.findOne(0L));
    }
}
