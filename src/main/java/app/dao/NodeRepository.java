package app.dao;

import app.entity.Node;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author dion
 */
public interface NodeRepository extends CrudRepository<Node, Long> {
    
}
