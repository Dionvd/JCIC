package app.dao;

import app.entity.Player;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    public List<Player> findByName(String name);

    public List<Player> findByEmail(String email);

}
