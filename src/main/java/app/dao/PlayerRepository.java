package app.dao;

import app.entity.Match;
import app.entity.Player;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Data access interface for the Player entity. Spring Data JPA automatically 
 * creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {

    public List<Player> findByNameIgnoreCase(String name);

    public List<Player> findByEmailIgnoreCase(String email);
    
}
