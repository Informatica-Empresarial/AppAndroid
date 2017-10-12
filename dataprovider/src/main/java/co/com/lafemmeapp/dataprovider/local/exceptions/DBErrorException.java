package co.com.lafemmeapp.dataprovider.local.exceptions;

/**
 * Created by oscargallon on 4/18/17.
 */

public class DBErrorException extends Exception {

    public DBErrorException() {
    }

    public DBErrorException(String message) {
        super(message);
    }

    public DBErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBErrorException(Throwable cause) {
        super(cause);
    }



    @Override
    public String getMessage() {
        return "Internal Error inside the DB";
    }
}
