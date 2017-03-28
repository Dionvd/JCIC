package app.dto;

/**
 * LoginCredentials contains the data required to login an account. Handles
 * sensitive information. It is used in post requests from the client, but it
 * should never be returned to the client.
 *
 * @author dion
 */
public class LoginCredentials {

    private String email;
    private String password;

    /**
     * Empty constructor. Do not use.
     */
    @Deprecated
    public LoginCredentials()
    {
    }
    
    /**
     * Default constructor.
     * @param email
     * @param password
     */
    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
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
