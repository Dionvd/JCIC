package app.dao;

import app.entity.Match;
import org.springframework.data.repository.CrudRepository;

/**
 * Data access interface for the Match entity. Spring Data JPA automatically 
 * creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface MatchRepository extends CrudRepository<Match, Long> {

}
