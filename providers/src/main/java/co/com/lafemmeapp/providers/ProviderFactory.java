package co.com.lafemmeapp.providers;

import co.com.lafemmeapp.providers.push_notifications.IPushNotificacionRegisterProvider;
import co.com.lafemmeapp.providers.push_notifications.PushNotificacionRegisterProvider;

/**
 * Created by oscargallon on 5/29/17.
 */

public class ProviderFactory {
    private static final ProviderFactory ourInstance = new ProviderFactory();

    private final IPushNotificacionRegisterProvider mPushNotificacionRegisterProvider;

    public static ProviderFactory getInstance() {
        return ourInstance;
    }

    private ProviderFactory() {
        mPushNotificacionRegisterProvider = new PushNotificacionRegisterProvider();
    }

    public IPushNotificacionRegisterProvider getPushNotificacionRegisterProvider() {
        return mPushNotificacionRegisterProvider;
    }
}
