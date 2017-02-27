package app.service;

import app.dao.HelpRepository;
import app.entity.Starterpack;
import app.exception.NotFoundException;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author dion
 */
@Service
public class HelpService {

    @Inject
    HelpRepository helpRep;

    public void save(Starterpack starterpack) {
        helpRep.save(starterpack);
    }

    public Iterable<Starterpack> findAll() {
        return helpRep.findAll();
    }

    public Starterpack findByLanguage(String language) {
        Starterpack starterpack = helpRep.findByLanguage(language);
        if (starterpack == null) {
            throw new NotFoundException();
        }
        return starterpack;
    }
}
