package co.com.lafemmeapp.lafemmeapp.presentation.registry;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBasePresenter;

/**
 * Created by stephany.berrio on 4/12/17.
 */

public interface IRegistryPresenter extends IBasePresenter,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, FacebookCallback<LoginResult> {


    CallbackManager getCallbackManager();

    /**
     * This method will start the registry process
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param address
     * @param city
     * @param password
     */
    void createUser(String firstName, String lastName, String email, String phoneNumber,
                    String address, String city, String password);

    void loginWithFacebook(Activity activity);

    void logOutFromFacebook(AccessToken accessToken);
}
