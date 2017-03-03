package app.rest;

import app.entity.Starterpack;
import app.exception.NotFoundException;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import app.service.HelpService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RESTful web service resource for getting help. Responsible for offering
 * external reference documents.
 *
 * @author dion
 */
@RestController
@RequestMapping(value = "/help", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class HelpResource {

    @Inject
    private HelpService helpService;

    /**
     * Get all Starterpacks.
     *
     * @return all Starterpacks
     */
    @RequestMapping(value = "/starterpacks")
    public Iterable<Starterpack> starterpacks() {
        return helpService.getAllStarterpacks();
    }

    /**
     * Get Starterpack with matching Language.
     *
     * @param language
     * @return Starterpack with matching language.
     * @throws NotFoundException 
     */
    @RequestMapping(value = "/starterpacks/{language}")
    public Starterpack getStarterpackByLanguage(@PathVariable(value = "language") String language) throws NotFoundException {
        return helpService.getStarterpackByLanguage(language);
    }

}
