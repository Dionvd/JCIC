package app.dao;

import app.entity.Node;
import org.springframework.data.repository.CrudRepository;

/**
 * Data access interface for the Node entity. Spring Data JPA automatically 
 * creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface NodeRepository extends CrudRepository<Node, Long> {
    
}
