package co.com.lafemmeapp.dataprovider.exceptions;

/**
 * Created by oscargallon on 5/3/17.
 */

public class NoSessionException extends Exception {

    public NoSessionException() {
    }

    @Override
    public String getMessage() {
        return "There is not session saved";
    }
}
