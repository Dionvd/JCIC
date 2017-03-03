package app.dao;

import app.entity.Settings;

import org.springframework.data.repository.CrudRepository;

/**
 * Data access interface for the Settings entity. Spring Data JPA automatically 
 * creates a class with this interface, that can be injected.
 * 
 * @author dion
 */
public interface SettingsRepository extends CrudRepository<Settings, Long> {

}
