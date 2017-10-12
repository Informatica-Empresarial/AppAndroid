package co.com.lafemmeapp.lafemmeapp.presentation.brand;

import android.view.View;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView;

/**
 * Created by oscargallon on 5/2/17.
 */

public interface IBrandFragmentView extends IBaseFragmentView {

    void onLocationPermissionsGranted();

    void onGPSEnabled();

    void requestPermissions();

    void setViewsVisibility(Boolean hide);

    View getBTNSchedule();
}
