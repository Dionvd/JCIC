/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

/**
 * Credentials contain the data required to register an account or log in. 
 * Handles sensitive information.
 * It is used in post requests but it should never be sent to the client.
 * @author dion
 */
public class Credentials {
    
    
    private String email;
    private String username;
    private String password;

    public Credentials() {
    }
    
    public Credentials(String email, String username, String password) { 
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
