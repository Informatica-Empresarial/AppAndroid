package co.com.lafemmeapp.lafemmeapp.presentation.location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView;

/**
 * Created by oscargallon on 5/1/17.
 */

public interface ILocationFragmentView extends IBaseFragmentView,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveListener{

    MapFragment getMapFragment();

    void showAddress(String address);

    void addMarker(LatLng latLng);

    void setMap(GoogleMap googleMap);

    void onLocationPermissionsGranted();

    void onGPSEnabled();

    void cleanAddress();

    void changeBTNNextAvailability(boolean enabled);

    void changeETIndicationsVisibility(int visibility);

}
