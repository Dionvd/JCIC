package app;

import app.entity.RegisterCredentials;
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
import app.service.*;

/**
 *
 * @author dion
 */
@Configuration
public class MockDataBean {

    @Inject
    PlayerService playerService;

    @Inject
    MatchService matchService;

    @Inject
    SettingsService settingsService;

    @Inject
    HelpService helpService;

    @Bean
    public CommandLineRunner mockData() {
        return (args) -> {

            WaitingQueue waitingQueue = QueueResource.waitingQueue;
            Settings settings = new Settings();

            Player player = new Player(new RegisterCredentials("John@test.uk", "John", "password"));
            player.setWinCount(3);
            playerService.save(player);

            Player player2 = new Player(new RegisterCredentials("Jake@test.uk", "Jake", "password"));
            player2.setWinCount(1);
            playerService.save(player2);

            Player player3 = new Player(new RegisterCredentials("Paul@test.uk", "Paul", "password"));
            player3.setWinCount(1);
            playerService.save(player3);

            Player player4 = new Player(new RegisterCredentials("Terrance@test.uk", "Terrance", "password"));
            player4.setWinCount(7);
            playerService.save(player4);

            Player player5 = new Player(new RegisterCredentials("Phil@test.uk", "Phil", "password"));
            player5.setWinCount(0);
            playerService.save(player5);

            Match match = new Match(settings);

            match.setMap(new MatchMap(10, 10, match.getId()));
            match.setTurn(4);

            waitingQueue.setMaxCount(50);
            waitingQueue.getPlayerIds().add(2L);
            waitingQueue.getPlayerIds().add(3L);
            waitingQueue.getPlayerIds().add(4L);

            Node startNode = match.getMap().getNode(0, 0);
            startNode.setOwnerId(0L);
            startNode.setPower(50);

            Node startNode2 = match.getMap().getNode(9, 9);
            startNode2.setOwnerId(1);
            startNode2.setPower(50);

            match.getPlayerIds().add(0L);
            match.getPlayerIds().add(1L);

            matchService.save(match);
            settingsService.save(settings);
            helpService.save(new Starterpack("Java starter package", "Java", "http://www.example.com/javapackage.zip"));

            // fetch all customers
            System.out.println("TEST:Users found with findAll():");
            System.out.println("-------------------------------");
            for (Player p : playerService.findAll()) {
                System.out.println(p.toString());
            }
            System.out.println("");

            // fetch an individual customer by ID
            Player p = playerService.findOne(1L);
            System.out.println("TEST:User found with findOne(1L):");
            System.out.println("--------------------------------");
            System.out.println(p.toString());
            System.out.println("");

            // fetch customers by last name
            System.out.println("TEST:User found with findByName('John'):");
            System.out.println("--------------------------------------------");
            playerService.findByName("John").forEach((pl) -> {
                System.out.println(pl.toString());
            });
            System.out.println("");
        };
    }
}
