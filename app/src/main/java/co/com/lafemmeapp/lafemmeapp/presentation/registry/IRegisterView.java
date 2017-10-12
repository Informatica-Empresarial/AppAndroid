package co.com.lafemmeapp.lafemmeapp.presentation.registry;

import java.util.List;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseView;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by stephany.berrio on 4/12/17.
 */

public interface IRegisterView extends IBaseView, IFragmentCallbacks {

    void showGoogleSignInButton();

    void hideGoogleSignInButton();

    void initGoogleConnection();

    void openGoogleSingInActivity();

    void setName(String name);

    void setLastName(String lastName);

    void setEmail(String email);

    void showNameError(String name);

    void showLastNameError(String lastName);

    void changeBTNRegistryAvailability(boolean enabled);

    void onFacebookLogOutSuccess();

    void setCities(List<String> cities);

    void showPasswordError(String s);

    void showPasswordConfirmationError(String s);

    void showCityError(String s);

    void showPhoneNumberError(String s);

    void showAddressError(String s);

    void showEmailError(String s);

    void finishActivity();

    void showProgressDialog();

    void hideProgressDialog();
}
