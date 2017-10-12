package co.com.lafemmeapp.lafemmeapp.presentation.login;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBasePresenter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 4/20/17.
 * This is the {@link LoginActivity} presenter
 */

public interface ILoginPresenter extends IBasePresenter, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        FacebookCallback<LoginResult>,IFragmentCallbacks {

    /**
     * This method will start the login process and will handle the process wants the
     * login process ends
     *
     * @param user     user's  email
     * @param password user's password
     */
    void login(String user, String password);

    /**
     * As we will have the save credentials with Google Smart Lock feature, we need
     * a method to init the {@link GoogleApiClient}
     */
    void initGoogleApiClient();

    void loginWithFacebook(Activity activity);

    void logOutFromFacebook(AccessToken accessToken);



}
