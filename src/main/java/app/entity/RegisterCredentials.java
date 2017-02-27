package app.entity;

/**
 * RegisterCredentials contain the data required to register an account. Handles
 * sensitive information. It is used in post requests from the client, but it
 * should never be sent to the client.
 *
 * @author dion
 */
public class RegisterCredentials {

    private String email;
    private String name;
    private String password;

    public RegisterCredentials() {
    }

    public RegisterCredentials(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
