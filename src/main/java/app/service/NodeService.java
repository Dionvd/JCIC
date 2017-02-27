package app.service;

import app.dao.NodeRepository;
import app.entity.Node;
import javax.inject.Inject;
import org.springframework.stereotype.Service;


/**
 *
 * @author dion
 */
@Service
public class NodeService {
    
    @Inject
    NodeRepository nodeRep;
    
    
    
    public Node getNode(int x, int y, Long matchId) {
        return nodeRep.findOne(calcNodeKey(x,y,matchId));
    }

    public Node getNode(Long nodeId) {
        return nodeRep.findOne(nodeId);
    }
    
    public static Long calcNodeKey(int x, int y, Long matchId)
    {
        return matchId * 10000 + x*100+y;
    }
}
