/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.dao.MatchRepository;
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

/**
 *
 * @author Dion
 */
@Configuration
public class AdminPanelHandler {

    public static boolean RESET_ALL_GAME_DATA = false;
    public static boolean READY_TO_CLEAR_GAME_DATA = false;
    public static boolean SAVE_NEW_SETTINGS = false;
    public static boolean STOP_ALL_MATCHES = false;
    public static Settings SETTINGS_TO_BE_SET;

    @Inject
    private MatchRepository matchRep;
    private static MatchRepository matchRepository;

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
            matchRepository = matchRep;
        };

    }

    public static void run() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    
                    if (STOP_ALL_MATCHES)
                    {
                        HostGame.stopMatches();
                        STOP_ALL_MATCHES = false;
                        Log.write("--ALL CURRENT MATCHES STOPPED!");
                    }
                    
                    
                    if (RESET_ALL_GAME_DATA) {
                     
                        HostGame.stopMatches();

                        while (!READY_TO_CLEAR_GAME_DATA) { }
                        matchRepository.deleteAll();
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
