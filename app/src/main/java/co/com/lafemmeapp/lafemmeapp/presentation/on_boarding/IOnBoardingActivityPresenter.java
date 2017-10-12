package co.com.lafemmeapp.lafemmeapp.presentation.on_boarding;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBasePresenter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 4/23/17.
 */

public interface IOnBoardingActivityPresenter extends IBasePresenter,

        IFragmentCallbacks {

    void goToRegisterActivity();

    void goToServiceActivity();
}
