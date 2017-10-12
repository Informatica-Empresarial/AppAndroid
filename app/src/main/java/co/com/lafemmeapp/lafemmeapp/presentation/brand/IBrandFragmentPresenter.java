package co.com.lafemmeapp.lafemmeapp.presentation.brand;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 5/2/17.
 */

public interface IBrandFragmentPresenter extends IBaseFragmentPresenter,
        IFragmentCallbacks {

    void getLocation(@NonNull Context context);

    void showGPSDialog(final Activity activity);



}
