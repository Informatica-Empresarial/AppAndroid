package co.com.lafemmeapp.lafemmeapp.presentation.dashboard;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBasePresenter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface IDashboardActivityPresenter extends IBasePresenter,
        IFragmentCallbacks {

    void onNavigationItemSelected(int id);

    void onFragmentReplaced(Fragment fragment);

    void onBackPressed();

    void getNavigationDrawerMenu(boolean initView);

    void getDeviceToken(@NonNull Context context);

    void onNewIntent(@NonNull Intent intent);

    int getStackSize();

}
