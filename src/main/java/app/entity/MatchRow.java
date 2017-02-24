
package app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author dion
 */

@Entity
public class MatchRow implements Serializable
{

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection
    public List<Node> nodes = new ArrayList<Node>();

    public MatchRow() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
