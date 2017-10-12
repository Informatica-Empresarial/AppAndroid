package co.com.lafemmeapp.providers.location;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by oscargallon on 5/1/17.
 * This provider will be in charge to load the map from google
 */
public class GoogleMapsProvider implements IGoogleMapsProvider {

    @Override
    public Observable<GoogleMap> getGoogleMapProvider(@NonNull final MapFragment mapFragment) {
        return Observable.create(new ObservableOnSubscribe<GoogleMap>() {
            @Override
            public void subscribe(final ObservableEmitter<GoogleMap> emitter) throws Exception {
                new Handler(Looper.myLooper() == null ?
                        Looper.getMainLooper() : Looper.myLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {
                                        emitter.onNext(googleMap);
                                        emitter.onComplete();
                                    }
                                });
                            }
                        });

            }
        });
    }
}
