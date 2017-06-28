package app.bean;

import app.dao.PlayerRepository;
import app.dao.SettingsRepository;
import app.dao.StarterpackRepository;
import app.entity.Round;
import app.entity.RoundMap;
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
import app.dao.RoundRepository;
import java.util.List;

/**
 * Bean class for initializing data and storing it in the Database. Can be
 * turned off in the Settings class.
 *
 * @author dion
 */
@Configuration
public class DataInitBean {

    @Inject
    PlayerRepository playerRep;

    @Inject
    RoundRepository roundRep;

    @Inject
    SettingsRepository settingsRep;

    @Inject
    StarterpackRepository starterpackRep;

    @Inject
    QueueService queueService;

    /**
     * Bean that initializes and mocks the entity data and stores it in the
     * repository. When enabled, the application will have some data to work
     * with when testing out its features. The mocked round is also immediately
     * hosted and can be visualized with the Unity project.
     *
     * Beans are automatically run when the application is booted.
     *
     * @return
     */
    @Bean
    public CommandLineRunner Initialize() {
        return (args) -> {

            if (!Settings.DATA_INITIALIZATION) {
                return;
            }

             Settings settings = new Settings();
            settingsRep.save(settings);
            
            Player guardianBot = new Player(new RegisterCredentials("", "GuardianBot", ""));
            Player assaultBot = new Player(new RegisterCredentials("", "AssaultBot", ""));
            Player powerBot = new Player(new RegisterCredentials("", "PowerBot", ""));
            Player tacticalBot = new Player(new RegisterCredentials("", "TacticalBot", ""));
            Player frontierBot = new Player(new RegisterCredentials("", "FrontierBot", ""));
            Player planningBot = new Player(new RegisterCredentials("", "PlanningBot", ""));

            //ids must remain 1-6 to function properly.
            playerRep.save(guardianBot);
            playerRep.save(assaultBot);
            playerRep.save(powerBot);
            playerRep.save(tacticalBot);
            playerRep.save(frontierBot);
            playerRep.save(planningBot);

            if (!Settings.DATA_MOCK) {
                return;
            }

            Log.write("--INITIATING MOCK DATA--");
            WaitingQueue waitingQueue = QueueService.getWaitingQueue();
           
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

            Round round = new Round(settings);
            //roundRep.save(round);
            
            RoundMap map = new RoundMap(new Point(10, 10), round.getId());
            round.setMap(map);
            
            map.getNode(0, 0).setType(-1);
            map.getNode(1, 0).setType(-1);
            map.getNode(0, 1).setType(-1);
            
            map.getNode(4,4).setType(-1);
            map.getNode(5,4).setType(-1);
            map.getNode(6,4).setType(-1);
            map.getNode(4,5).setType(-1);
            map.getNode(4,5).setType(-1);
            map.getNode(4,5).setType(-1);
            map.getNode(4,6).setType(-1);            
            map.getNode(5,6).setType(-1);
            map.getNode(6,6).setType(-1);

            
            round.getPlayerIds().add(assaultBot.getId());
            round.getPlayerIds().add(guardianBot.getId());

            waitingQueue.setMaxCount(50);
            waitingQueue.getPlayers().add(player3);
            waitingQueue.getPlayers().add(player4);
            SocketToUnity.setQueueUpdate(waitingQueue);

            Node startNode = round.getMap().getNode(0, 0);
            startNode.setOwnerId(2);
            startNode.setPower(30);

            
            Node startNode2 = round.getMap().getNode(9, 9);
            startNode2.setOwnerId(2);
            startNode2.setPower(30);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Node blockedNode = round.getMap().getNode(4 + i, 4 + j);
                    blockedNode.setType(-1);
                }
            }

            Log.write("--SAVING MOCK DATA--");
            starterpackRep.save(new Starterpack("Java starter package", "Java", "https://github.com/Dionvd/JCIC-JavaStarter", "Dion van Dam"));
            starterpackRep.save(new Starterpack("C# starter package", "C#", "http://www.example.com/c#package.zip", "Casper Linschooten"));
            
            playerRep.save(player);
            playerRep.save(player2);
            playerRep.save(player3);
            playerRep.save(player4);
            playerRep.save(player5);
            roundRep.save(round);

            HostGame.storeRound(round);
            
            Log.write("--MOCK DATA SUCCESFUL--");

        };
    }
}
