package app.dao;

import app.entity.Match;
import app.entity.Player;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Data access interface for the Match entity. Spring Data JPA automatically 
 * creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface MatchRepository extends CrudRepository<Match, Long> {

    public List<Match> findByPlayers(Player player);
}
