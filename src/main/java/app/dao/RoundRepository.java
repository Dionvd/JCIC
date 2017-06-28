package app.dao;

import app.entity.Round;
import app.entity.Player;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Data access interface for the Round entity. Spring Data JPA automatically 
 * creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface RoundRepository extends CrudRepository<Round, Long> {

    public List<Round> findByPlayerIds(Long playerId);
    
   // @Query("SELECT r.playerIds FROM Round r where r.id = :id") 
   // Optional<List<Long>> findPlayerIdsByRoundId(@Param("id") Long id);
}
