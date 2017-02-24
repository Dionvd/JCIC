package app.dao;

import app.entity.Player;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findByUsername(String username);
}