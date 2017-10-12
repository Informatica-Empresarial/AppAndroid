package co.com.lafemmeapp.providers.utils;

/**
 * Created by oscargallon on 5/9/17.
 */

public class UtilsProvidersFactory {

    private IDateProvider mDateProvider;


    private final static UtilsProvidersFactory instance = new UtilsProvidersFactory();

    private UtilsProvidersFactory() {
        mDateProvider = new DateProvider();

    }

    public static UtilsProvidersFactory getInstance() {
        return instance;
    }

    public IDateProvider getmDateProvider() {
        return mDateProvider;
    }
}
