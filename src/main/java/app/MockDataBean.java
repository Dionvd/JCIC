package app;

import app.dao.MatchRepository;
import app.dao.PlayerRepository;
import app.dao.SettingsRepository;
import app.dao.StarterpackRepository;
import app.entity.Credentials;
import app.entity.Match;
import app.entity.MatchMap;
import app.entity.Node;
import app.entity.Player;
import app.entity.Settings;
import app.entity.Starterpack;
import app.entity.WaitingQueue;
import app.rest.QueueResource;
import javax.inject.Inject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author dion
 */

@Configuration
public class MockDataBean {
    
    
    @Inject
    PlayerRepository playerRep;
    
    @Inject
    MatchRepository matchRep;
    
    @Inject
    SettingsRepository settingsRep;
    
    @Inject
    StarterpackRepository starterpackRep;
    
    @Bean
    public CommandLineRunner mockData() {
        return (args) -> {

            WaitingQueue waitingQueue = QueueResource.waitingQueue;
            Settings settings = new Settings();

            Player player = new Player(new Credentials("John@test.uk", "John", "password"));
            player.setWinCount(3);
            playerRep.save(player);

            Player player2 = new Player(new Credentials("Jake@test.uk", "Jake", "password"));
            player2.setWinCount(1);
            playerRep.save(player2);

            Player player3 = new Player(new Credentials("Paul@test.uk", "Paul", "password"));
            player3.setWinCount(1);
            playerRep.save(player3);

            Player player4 = new Player(new Credentials("Terrance@test.uk", "Terrance", "password"));
            player4.setWinCount(7);
            playerRep.save(player4);

            Player player5 = new Player(new Credentials("Phil@test.uk", "Phil", "password"));
            player5.setWinCount(0);
            playerRep.save(player5);

            Match game = new Match(settings);

            game.setMap(new MatchMap(10, 10));
            game.setTurn(4);

            waitingQueue.setMaxCount(50);
            waitingQueue.getPlayerIds().add(2L);
            waitingQueue.getPlayerIds().add(3L);
            waitingQueue.getPlayerIds().add(4L);

            Node startNode = game.getMap().getNode(0, 0);
            startNode.setOwnerId(0L);
            startNode.setPower(50);

            Node startNode2 = game.getMap().getNode(9, 9);
            startNode2.setOwnerId(1);
            startNode2.setPower(50);

            game.getPlayerIds().add(0L);
            game.getPlayerIds().add(1L);

            matchRep.save(game);
            settingsRep.save(settings);
            starterpackRep.save(new Starterpack("java starter package","http://www.example.com/javapackage.zip"));

            // fetch all customers
            System.out.println("Customers found with findAll():");
            System.out.println("-------------------------------");
            for (Player p : playerRep.findAll()) {
                    System.out.println(p.toString());
            }
            System.out.println("");

            // fetch an individual customer by ID
            Player p = playerRep.findOne(1L);
            System.out.println("Customer found with findOne(1L):");
            System.out.println("--------------------------------");
            System.out.println(p.toString());
            System.out.println("");

            // fetch customers by last name
            System.out.println("Customer found with findByUsername('John'):");
            System.out.println("--------------------------------------------");
            playerRep.findByUsername("John").forEach((pl) -> {
                System.out.println(pl.toString());
            });
            System.out.println("");
        };
    }
}
