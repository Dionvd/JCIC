package app;

import app.dao.MatchRepository;
import app.dao.PlayerRepository;
import app.dao.SettingsRepository;
import app.dao.StarterpackRepository;
import app.entity.Match;
import app.entity.MatchMap;
import app.entity.Node;
import app.entity.Player;
import app.entity.Settings;
import app.entity.Starterpack;
import app.object.RegisterCredentials;
import app.object.WaitingQueue;
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
    PlayerRepository playerRep;

    @Inject
    MatchRepository matchRep;

    @Inject
    SettingsRepository settingsRep;

    @Inject
    StarterpackRepository starterpackRep;
    
    @Inject
    QueueService queueService;
    

    @Bean
    public CommandLineRunner mockData() {
        return (args) -> {

            if (!Settings.MOCKDATA) return;
            
            System.out.println("--INITIATING MOCK DATA--");
            WaitingQueue waitingQueue = QueueService.getWaitingQueue();
            Settings settings = new Settings();

            Player player = new Player(new RegisterCredentials("John@test.uk", "John", "password"));
            player.setWinCount(3);

            Player player2 = new Player(new RegisterCredentials("Jake@test.uk", "Jake", "password"));
            player2.setWinCount(1);

            Player player3 = new Player(new RegisterCredentials("Paul@test.uk", "Paul", "password"));
            player3.setWinCount(1);

            Player player4 = new Player(new RegisterCredentials("Terrance@test.uk", "Terrance", "password"));
            player4.setWinCount(7);

            Player player5 = new Player(new RegisterCredentials("Phil@test.uk", "Phil", "password"));
            player5.setWinCount(0);

            Match match = new Match(settings);

            match.setMap(new MatchMap(10, 10, match.getId()));
            match.setTurn(4);

            match.getPlayers().add(player);
            match.getPlayers().add(player2);
                    
            waitingQueue.setMaxCount(50);
            waitingQueue.getPlayers().add(player3);
            waitingQueue.getPlayers().add(player4);
            waitingQueue.getPlayers().add(player5);

            Node startNode = match.getMap().getNode(0, 0);
            startNode.setOwnerId(0L);
            startNode.setPower(50);

            Node startNode2 = match.getMap().getNode(9, 9);
            startNode2.setOwnerId(1);
            startNode2.setPower(50);

            System.out.println("--SAVING MOCK DATA--");
            settingsRep.save(settings);
            starterpackRep.save(new Starterpack("Java starter package", "Java", "http://www.example.com/javapackage.zip"));
            playerRep.save(player);
            playerRep.save(player2);
            playerRep.save(player3);
            playerRep.save(player4);
            playerRep.save(player5);
            matchRep.save(match);
            
            System.out.println("--MOCK DATA SUCCESFUL--");

        };
    }
}
