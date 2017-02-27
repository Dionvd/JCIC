package app.entity;

/**
 * LoginCredentials contains the data required to register an account. Handles
 * sensitive information. It is used in post requests from the client, but it
 * should never be sent to the client.
 *
 * @author dion
 */
public class LoginCredentials {

    private String email;
    private String password;

    public LoginCredentials() {
    }

    public LoginCredentials(String email, String name, String password) {
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
