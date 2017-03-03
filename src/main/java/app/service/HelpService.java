package app.service;

import app.FindException;
import app.dao.StarterpackRepository;
import app.entity.Starterpack;
import app.exception.NotFoundException;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Responsible for handling Starterpack logic when a RESTful web service is 
 * called and accessing related JPA repositories appropriately.
 * 
 * @author dion
 */
@Service
public class HelpService {

    @Inject
    StarterpackRepository starterpackRep;

    /**
     * Gets a Starterpack that matches the language.
     * @param language
     * @return Starterpack with this language
     * @throws NotFoundException
     */
    public Starterpack getStarterpackByLanguage(String language) throws NotFoundException {
        return (Starterpack) FindException.notFoundOnNull(starterpackRep.findByLanguageIgnoreCase(language));
    }
    
    
    /**
     * Gets all Starterpacks.
     * @return
     */
    public Iterable<Starterpack> getAllStarterpacks() {
        return starterpackRep.findAll();
    }
    

}
