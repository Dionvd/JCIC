package app.service;

import app.util.Validate;
import app.dao.SettingsRepository;
import app.entity.Settings;
import app.exception.NotFoundException;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * QueueService handles Queue Requests. Because the waiting queue does not need
 * to be stored in the database, it is stored as a variable without a dao repo.
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
        
        return Validate.notNull(settingsRep.findOne(1L));
    }
}
