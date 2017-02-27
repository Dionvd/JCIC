package app.dao;

import app.entity.Starterpack;

import org.springframework.data.repository.CrudRepository;

public interface HelpRepository extends CrudRepository<Starterpack, Long> {

    public Starterpack findByLanguage(String language);

}
