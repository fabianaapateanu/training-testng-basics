package training.basic.dataprovider;

/**
 * @author fapateanu
 * User class - to be used for data provider object for performing the login needed by all tests
 */
public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
