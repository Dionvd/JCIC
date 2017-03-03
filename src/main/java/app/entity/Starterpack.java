package app.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Contains the data required to find a starterpack on the internet.
 * Starterpacks are projects that can be downloaded and used by a player to 
 * avoid having to write their own JSON interpreter and HTTP request sender.
 * @author dion
 */
@Entity
public class Starterpack implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    private String language;

    private String link;

    /**
     * Entity constructor. Do not use.
     */
    @Deprecated
    public Starterpack() {
    }

    /**
     * Default constructor.
     * @param name
     * @param language
     * @param link
     */
    public Starterpack(String name, String language, String link) {
        this.name = name;
        this.language = language;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
