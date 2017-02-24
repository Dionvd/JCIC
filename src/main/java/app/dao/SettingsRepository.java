package app.dao;

import app.entity.Settings;

import org.springframework.data.repository.CrudRepository;

public interface SettingsRepository extends CrudRepository<Settings, Long> {

   
}