package co.com.lafemmeapp.providers.location.exceptions;

/**
 * Created by oscargallon on 4/25/17.
 */

public class NoSuchAsProviderException extends Exception{

    @Override
    public String getMessage() {
        return "Wrong Provider should be GPS or Network Provider";
    }
}
