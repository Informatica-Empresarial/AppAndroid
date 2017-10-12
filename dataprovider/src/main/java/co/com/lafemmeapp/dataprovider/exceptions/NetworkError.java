package co.com.lafemmeapp.dataprovider.exceptions;

import java.io.IOException;

/**
 * Created by oscargallon on 4/8/17.
 */

public class NetworkError extends Throwable {
    public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again.";
    public static final String NETWORK_ERROR_MESSAGE = "No Internet Connection!";

    private final Throwable error;

    public NetworkError(Throwable e) {
        super(e);
        this.error = e;
    }

    @Override
    public String getMessage() {
        if (getError() instanceof IOException) {
            return NETWORK_ERROR_MESSAGE;
        }
        return DEFAULT_ERROR_MESSAGE;

    }


    public Throwable getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkError that = (NetworkError) o;

        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}
