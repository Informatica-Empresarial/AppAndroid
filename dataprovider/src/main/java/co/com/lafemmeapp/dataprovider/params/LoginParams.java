package co.com.lafemmeapp.dataprovider.params;

/**
 * Created by oscargallon on 4/18/17.
 */

public class LoginParams {

    private final String email;

    private final String password;

    public LoginParams(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
