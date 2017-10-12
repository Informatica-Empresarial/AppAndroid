package co.com.lafemmeapp.utilmodule.presentation.alert_dialog;

import android.view.View;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 5/13/17.
 */

public interface IAlertFragmentDialogPresenter extends IBaseFragmentPresenter,
        IFragmentCallbacks {


    void actionTriggered(View view, boolean wasPositive);

}
