package co.com.lafemmeapp.dataprovider.local.exceptions;

/**
 * Created by oscargallon on 4/18/17.
 */

public class DBNoCreatedException extends Exception {

    public DBNoCreatedException() {
    }

    public DBNoCreatedException(String message) {
        super(message);
    }

    public DBNoCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBNoCreatedException(Throwable cause) {
        super(cause);
    }


    @Override
    public String getMessage() {
        return  "The DB did not exist, do you forget to call initDataSource in your android " +
                "application class";
    }
}
