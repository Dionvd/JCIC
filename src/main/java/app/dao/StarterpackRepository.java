//http://stackoverflow.com/questions/22573428/case-insensitive-query-with-spring-crudrepository

package app.dao;

import app.entity.Starterpack;
import org.springframework.data.repository.CrudRepository;

/**
 * Data access interface for the Starterpack entity. Spring Data JPA 
 * automatically creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface StarterpackRepository extends CrudRepository<Starterpack, Long> {

    public Starterpack findByLanguageIgnoreCase(String language);
    

}
