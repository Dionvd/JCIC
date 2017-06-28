package app.service;

import app.util.Validate;
import app.dao.NodeRepository;
import app.entity.Node;
import app.exception.NotFoundException;
import java.awt.Point;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Responsible for handling Node logic when a RESTful web service is called and
 * accessing related JPA repositories appropriately.
 *
 * @author dion
 */
@Service
public class NodeService {

    @Inject
    NodeRepository nodeRep;

    public Node getNode(Long roundId, int x, int y) throws NotFoundException {
        return Validate.notNull(nodeRep.findOne(Node.calcKey(roundId, new Point(x,y))));
    }

    public Node getNode(Long nodeId) throws NotFoundException {
        return Validate.notNull(nodeRep.findOne(nodeId));
    }

}
