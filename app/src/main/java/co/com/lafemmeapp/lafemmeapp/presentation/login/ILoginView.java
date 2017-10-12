package co.com.lafemmeapp.lafemmeapp.presentation.login;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseView;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 4/20/17.
 * This is the {@link ILoginView} contract
 */
public interface ILoginView extends IBaseView, IFragmentCallbacks {

    void showEmailError(String error);

    void showPasswordError(String error);

    void enableOrDisableLoginButton(boolean enable);

    boolean hasEmailFieldError();

    boolean hasPasswordFieldError();

    void setEmail(String email);

    void setPassword(String password);

    void finishActivity();

    void onFacebookLogOutSuccess();



}
