package co.com.lafemmeapp.dataprovider.network.entities;

/**
 * Created by oscargallon on 4/18/17.
 */

public class LoginRequest {

    private final String email;

    private final String password;


    public LoginRequest(String email, String password) {
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
