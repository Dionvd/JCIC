/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.entity.Credentials;
import app.entity.Player;
import app.exceptions.BlockedException;
import app.exceptions.FailedRegisterException;
import app.exceptions.FailedLoginException;
import app.exceptions.NotFoundException;

/**
 *
 * @author dion
 */
public class SessionHandler {
    
    
    private static final int BLOCKED_ON_FAILED_LOGIN_ATTEMPTS = 5;
    private static final int BLOCKED_TIMEOUT_MINUTES = 5;
    
    private SessionHandler() { }
    
    
    
    /**
     * Tries to login the player with the given Credentials. 
     * If it fails the failed login count goes up by one, potentially blocking the player.
     *
     * @param c
     * @return player matching id
     * @throws exceptions.FailedLoginException
     * @throws NotFoundException occurs when no id exists (error 404).
     */
    public static Player getPlayerByLogin(Credentials c) throws FailedLoginException {

//        for (Player p : players) {
//            if (p.getUsername().equals(c.getUsername())) {
//                //username matches, check if this user is blocked or not.
//                if (p.isBlocked() && !p.checkUnblocked()) {
//                    //user is blocked, check if he should be blocked.
//                    throw new BlockedException();
//                }
//                //user is not blocked, check if credentials match.
//                if (p.getPassword().equals(c.getPassword()) && p.getEmail().equals(c.getEmail())) {
//                    //credentials match, login succesful
//                    return p;
//                } else {
//                    //credentials do not match, login failed. on too many failed attempts the user becomes blocked.
//                    p.incrementFailedLoginCount();
//
//                    //block account
//                    if (p.getFailedLoginCount() == BLOCKED_ON_FAILED_LOGIN_ATTEMPTS) {
//                        p.block(BLOCKED_TIMEOUT_MINUTES);
//                        throw new BlockedException();
//                    }
//                }
//            }
//        }
        throw new FailedLoginException();
    }

    /**
     * Creates a new Player account.
     * Checks if the username and email credentials are already in use, if not, the player account is created.
     * @param c (credentials)
     * @throws exceptions.FailedRegisterException
     */
    public static void registerWithCredentials(Credentials c) throws FailedRegisterException {
//        for (Player p : players) {
//            if (p.getUsername().equals(c.getUsername()) || p.getEmail().equals(c.getEmail())) {
//                throw new FailedRegisterException();
//            }
//            
//        }
//        players.add(new Player(c));
    }
    
    
    public static boolean checkSessionToken(Long playerId, String sessionToken) {
        
//        Player p = getPlayerById(playerId);
//        return p.getSessionId().equals(sessionToken);
        return false;
    }
    
}
