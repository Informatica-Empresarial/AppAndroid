package co.com.lafemmeapp.lafemmeapp.presentation.location;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter;

/**
 * Created by oscargallon on 5/1/17.
 */

public interface ILocationFragmentPresenter extends IBaseFragmentPresenter {

    void loadMap(@NonNull Context context);

    void getAddressFromLatLng(Context context, LatLng latLng);

    void getLatLngFromAddress(Context context, String address);

    void getLocation(@NonNull Context context);

    void showGPSDialog(final Activity activity);

    void checkLocationAndContinueWithServiceSchedule(LatLng latLng,
                                                     String address,
                                                     String indications,
                                                     Context context,
                                                     View view);

    Bitmap getMarkerIcon(Resources resources);

    void onStart();


}
