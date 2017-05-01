package app.bean;

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
import app.dto.RegisterCredentials;
import app.dto.WaitingQueue;
import javax.inject.Inject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import app.service.*;
import app.ui.Log;
import java.awt.Point;

/**
 * Bean class for mocking Entity data and storing it in the Repository.
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

    /**
     * Bean that mocks the entity data and stores it in the repository. When
     * enabled, the application will have some data to work with when testing
     * out its features. mockData() can be turned on or off through
     * Settings.MOCKDATA. The mocked round is also immediately hosted and can be
     * visualized with the Unity visuals.
     *
     * Beans are automatically run when the application is booted.
     *
     * @return
     */
    @Bean
    public CommandLineRunner mockData() {
        return (args) -> {

            if (!Settings.MOCKDATA) {
                return;
            }

            Log.write("--INITIATING MOCK DATA--");
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

            match.setMap(new MatchMap(new Point(10, 10), match.getId()));
            match.getPlayers().add(player);
            match.getPlayers().add(player2);

            waitingQueue.setMaxCount(50);
            waitingQueue.getPlayers().add(player3);
            waitingQueue.getPlayers().add(player4);            
            SocketToUnity.setQueueUpdate(waitingQueue);

            Node startNode = match.getMap().getNode(0, 0);
            startNode.setOwnerId(1);
            startNode.setPower(30);

            Node startNode2 = match.getMap().getNode(9, 9);
            startNode2.setOwnerId(2);
            startNode2.setPower(30);
            
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Node blockedNode = match.getMap().getNode(4+i, 4+j);
                    blockedNode.setType(-1);
                }
            }
            

            Log.write("--SAVING MOCK DATA--");
            settingsRep.save(settings);
            starterpackRep.save(new Starterpack("Java starter package", "Java", "http://www.example.com/javapackage.zip"));
            playerRep.save(player);
            playerRep.save(player2);
            playerRep.save(player3);
            playerRep.save(player4);
            playerRep.save(player5);
            matchRep.save(match);

            HostGame.storeMatch(match);

            Log.write("--MOCK DATA SUCCESFUL--");

        };
    }
}
