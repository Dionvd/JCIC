/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.dao.NodeRepository;
import app.dao.PlayerRepository;
import app.dao.SettingsRepository;
import app.entity.Settings;
import app.ui.Log;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import app.dao.RoundRepository;
import app.dto.RegisterCredentials;
import app.entity.Player;
import app.entity.Round;
import app.entity.RoundMap;
import app.ui.TableActiveMatches;
import java.awt.Point;

/**
 *
 * @author Dion
 */
@Configuration
public class AdminPanelHandler {

    public static boolean RESET_ALL_GAME_DATA = false;
    public static boolean READY_TO_CLEAR_GAME_DATA = false;
    public static boolean SAVE_NEW_SETTINGS = false;
    public static boolean STOP_ALL_ROUNDS = false;
    public static boolean STOP_SELECTED_ROUND = false;
    public static Settings SETTINGS_TO_BE_SET;
    public static boolean ADD_MOCKED_ROUND = false;

    @Inject
    private RoundRepository roundRep;
    private static RoundRepository roundRepository;

    @Inject
    private PlayerRepository playerRep;
    private static PlayerRepository playerRepository;

    @Inject
    private NodeRepository nodeRep;
    private static NodeRepository nodeRepository;

    @Inject
    private SettingsRepository settingsRep;
    private static SettingsRepository settingsRepository;

    @Bean
    public CommandLineRunner adminPanelHandlerStaticInjects() {

        return (args) -> {
            settingsRepository = settingsRep;
            nodeRepository = nodeRep;
            playerRepository = playerRep;
            roundRepository = roundRep;
        };

    }

    public static void run() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    
                    if (STOP_ALL_ROUNDS)
                    {
                        HostGame.stopRounds();
                        STOP_ALL_ROUNDS = false;
                        Log.write("--ALL CURRENT ROUNDS STOPPED!");
                    }
                    
                    
                    if (RESET_ALL_GAME_DATA) {
                     
                        HostGame.stopRounds();

                        while (!READY_TO_CLEAR_GAME_DATA) { }
                        roundRepository.deleteAll();
                        playerRepository.deleteAll();
                        nodeRepository.deleteAll();
                        settingsRepository.deleteAll();
                        RESET_ALL_GAME_DATA = false;
                        READY_TO_CLEAR_GAME_DATA = false;
                        Log.write("--ALL GAME DATA CLEARED!");
                    }
                    else {
                        READY_TO_CLEAR_GAME_DATA = false;
                    }
                    
                    if (SAVE_NEW_SETTINGS)
                    {
                        SETTINGS_TO_BE_SET.setId(1L);
                        settingsRepository.save(SETTINGS_TO_BE_SET);
                        SAVE_NEW_SETTINGS = false;
                        Log.write("--NEW SETTINGS SAVED!");
                    }

                    if (ADD_MOCKED_ROUND)
                    {
                        Settings settings = settingsRepository.findOne(1L);
                        Round round = new Round(settings);
                        roundRepository.save(round);
                        RoundMap map = new RoundMap(new Point(10, 10), round.getId());
                        round.setMap(map);

                        round.getPlayerIds().add(1L);                 
                        round.getPlayerIds().add(2L);
                        round.getMap().generate(round.getPlayerIds());
                        roundRepository.save(round);  
                        HostGame.storeRound(round);
                        ADD_MOCKED_ROUND = false;
                        Log.write("Game " + round.getId() + " initiated manually.");
                    }
                    
                    if (STOP_SELECTED_ROUND)
                    {
                        int selected = TableActiveMatches.self.getSelectedRow();
                        HostGame.stopRound(selected);
                        STOP_SELECTED_ROUND = false;
                    }
                    
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AdminPanelHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

}
