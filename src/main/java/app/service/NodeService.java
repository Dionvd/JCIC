package app.service;

import app.FindException;
import app.dao.NodeRepository;
import app.entity.Node;
import app.exception.NotFoundException;
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

    public Node getNode(Long matchId, int x, int y) throws NotFoundException {
        return FindException.notFoundOnNull(nodeRep.findOne(Node.calcKey(matchId, x, y)));
    }

    public Node getNode(Long nodeId) throws NotFoundException {
        return FindException.notFoundOnNull(nodeRep.findOne(nodeId));
    }

}
