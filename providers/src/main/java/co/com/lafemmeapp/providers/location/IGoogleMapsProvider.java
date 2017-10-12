package co.com.lafemmeapp.providers.location;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import io.reactivex.Observable;

/**
 * Created by oscargallon on 5/1/17.
 */

public interface IGoogleMapsProvider {

    Observable<GoogleMap> getGoogleMapProvider(@NonNull  final MapFragment mapFragment);
}
