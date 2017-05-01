package app.dao;

import app.entity.Match;
import app.entity.Player;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Data access interface for the Match entity. Spring Data JPA automatically 
 * creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface MatchRepository extends CrudRepository<Match, Long> {

    public List<Match> findByPlayers(Player player);
    
    @Query("SELECT m.players FROM Match m where m.id = :id") 
    Optional<List<Player>> findPlayersByMatchId(@Param("id") Long id);
}
