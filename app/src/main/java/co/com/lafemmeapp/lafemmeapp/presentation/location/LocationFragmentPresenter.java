package co.com.lafemmeapp.lafemmeapp.presentation.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jakewharton.rxbinding2.widget.RxTextView;

import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest;
import co.com.lafemmeapp.core.domain.params.GeocoderGetAddressFromLatLngParams;
import co.com.lafemmeapp.core.domain.params.GeocoderGetLatLngFromLatLngParams;
import co.com.lafemmeapp.core.domain.params.GoogleAPIClientParams;
import co.com.lafemmeapp.core.domain.params.LocationRequestParams;
import co.com.lafemmeapp.core.domain.params.VerifyAddressLatLngParams;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.customer.GetGoogleAPIClientUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.GetAddressFromLatLngUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.GetLatLngFromAddressUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.GetMapUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.GetUserLocationUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.ShowEnableGPSDialogUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.VerifyAddressAndLatLngUseCase;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.providers.location.exceptions.LatLngNotFoundForGivenAddress;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.UtilModuleConstants;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by oscargallon on 5/1/17.
 */

public class LocationFragmentPresenter implements ILocationFragmentPresenter {

    private ILocationFragmentView mLocationFragmentView;

    private ViewServicesRequest mViewServiceRequest;

    private CompositeDisposable mCompositeDisposable;

    private IFragmentCallbacks mFragmentCallbacks;

    private boolean mIsGettingLocation = false;

    private boolean mHasEnabledGPS = false;


    public LocationFragmentPresenter(ILocationFragmentView mLocationFragmentView) {
        this.mLocationFragmentView = mLocationFragmentView;
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void onCreate(Bundle arguments) {
        if (arguments != null) {
            mViewServiceRequest = arguments
                    .getParcelable(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY);
        }
    }

    @Override
    public void onCreateView(View view) {
        if (mLocationFragmentView != null) {
            mLocationFragmentView.initViewComponents(view);
            mLocationFragmentView.initComponents();
        }
    }

    @Override
    public void onAttach(IFragmentCallbacks fragmentCallback) {
        mFragmentCallbacks = fragmentCallback;
    }

    @Override
    public void loadMap(@NonNull final Context context) {
        if (mLocationFragmentView != null) {
            getSupportMapFragmentFromView()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<MapFragment>() {
                        @Override
                        public void onSuccess(MapFragment value) {
                            IUseCaseIterator iterator =
                                    new GetMapUseCase(Schedulers.io(),
                                            AndroidSchedulers.mainThread());
                            iterator.execute(new DisposableObserver<GoogleMap>() {
                                @Override
                                public void onNext(GoogleMap value) {
                                    if (mLocationFragmentView != null) {
                                        mLocationFragmentView.setMap(value);
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (mLocationFragmentView != null) {
                                        mLocationFragmentView.showMessage("Error",
                                                e.getMessage(),
                                                UtilModuleConstants.SHOW_ERROR_MESSAGE_ACTION,
                                                false);
                                    }
                                }

                                @Override
                                public void onComplete() {

                                }
                            }, value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });


        }
    }

    private Single<MapFragment> getSupportMapFragmentFromView() {
        return Single.create(new SingleOnSubscribe<MapFragment>() {
            @Override
            public void subscribe(SingleEmitter<MapFragment> emitter) throws Exception {
                if (mLocationFragmentView != null) {
                    emitter.onSuccess(mLocationFragmentView.getMapFragment());
                } else {
                    throw new NullPointerException("ILocationFragmentView null");
                }

            }
        });
    }

    @Override
    public void getAddressFromLatLng(Context context, LatLng latLng) {
        IUseCaseIterator iterator =
                new GetAddressFromLatLngUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread());
        iterator.execute(new DisposableObserver<String>() {
            @Override
            public void onNext(String address) {
                if (address != null) {
                    if (mLocationFragmentView != null) {
                        mLocationFragmentView.showAddress(address);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        }, new GeocoderGetAddressFromLatLngParams(context,
                latLng));
    }

    @Override
    public Bitmap getMarkerIcon(Resources resources) {
        Bitmap bitmap = null;
        Drawable drawable =
                resources.getDrawable(R.drawable.ic_pin);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getLocation(@NonNull Context context) {
        if (!mIsGettingLocation && mHasEnabledGPS) {
            mIsGettingLocation = true;
            IUseCaseIterator iterator
                    = new GetUserLocationUseCase(Schedulers.computation(),
                    AndroidSchedulers.mainThread());

            iterator.execute(new DisposableObserver<Location>() {
                @Override
                public void onNext(Location value) {
                    if (mLocationFragmentView != null) {
                        mLocationFragmentView.addMarker(new LatLng(value.getLatitude(),
                                value.getLongitude()));
                    }
                    mIsGettingLocation = false;

                }

                @Override
                public void onError(Throwable e) {
                    mIsGettingLocation = false;
                }

                @Override
                public void onComplete() {

                }
            }, new LocationRequestParams(context, LocationManager.NETWORK_PROVIDER));
        } else if (!mHasEnabledGPS && !mIsGettingLocation) {
            if (mLocationFragmentView != null) {
                mLocationFragmentView.addMarker(new LatLng(AppModuleConstants.DEFAULT_LAT,
                        AppModuleConstants.DEFAULT_LNG));
            }

            mIsGettingLocation = false;
        }

    }

    @Override
    public void getLatLngFromAddress(Context context, String address) {

        if (mViewServiceRequest.getmCity() != null && !address.contains(mViewServiceRequest.getmCity())) {
            address += " " + mViewServiceRequest.getmCity();
        }

        IUseCaseIterator iterator =
                new GetLatLngFromAddressUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread());
        iterator.execute(new DisposableObserver<LatLng>() {
            @Override
            public void onNext(LatLng value) {
                if (mLocationFragmentView != null) {
                    mLocationFragmentView
                            .addMarker(value);
                }

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (e instanceof LatLngNotFoundForGivenAddress) {
                    if (mLocationFragmentView != null) {
                        mLocationFragmentView.showMessage("Error",
                                e.getMessage(), UtilModuleConstants.SHOW_ERROR_MESSAGE_ACTION,
                                false);
                        mLocationFragmentView.cleanAddress();
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        }, new GeocoderGetLatLngFromLatLngParams(context,
                address));
    }

    private void showGPSDialog(final Activity activity, GoogleApiClient googleApiClient) {

        IUseCaseIterator iterator =
                new ShowEnableGPSDialogUseCase(Schedulers.io(), AndroidSchedulers.mainThread());

        iterator.execute(new DisposableObserver<Status>() {
            @Override
            public void onNext(Status status) {
                switch (status.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS: {
                        if (mLocationFragmentView != null) {
                            mHasEnabledGPS = true;
                            mLocationFragmentView.onGPSEnabled();
                        }
                        break;
                    }
                    case CommonStatusCodes.RESOLUTION_REQUIRED: {
                        try {
                            status.startResolutionForResult(activity,
                                    AppModuleConstants.GPS_DIALOG_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            //TODO Show error
                        }
                        break;
                    }
                    default: {
                        //TODO Show error
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, googleApiClient);

    }

    @Override
    public void showGPSDialog(final Activity activity) {
        IUseCaseIterator iterator =
                new GetGoogleAPIClientUseCase(Schedulers.io(),
                        AndroidSchedulers.mainThread());
        iterator.execute(new DisposableObserver<GoogleApiClient>() {
            @Override
            public void onNext(GoogleApiClient value) {
                if (value != null) {
                    showGPSDialog(activity, value);
                }
            }

            @Override
            public void onError(Throwable e) {
                //TODO Show error
            }

            @Override
            public void onComplete() {

            }
        }, new GoogleAPIClientParams(LocationServices.API, activity));
    }

    @Override
    public void checkLocationAndContinueWithServiceSchedule(final LatLng latLng, final String address,
                                                            final String indications,
                                                            Context context, final View view) {


        if (latLng != null && !TextUtils.isEmpty(address)) {
            IUseCaseIterator iterator =
                    new VerifyAddressAndLatLngUseCase(Schedulers.computation(),
                            AndroidSchedulers.mainThread());

            iterator.execute(new DisposableObserver<Float>() {
                @Override
                public void onNext(Float value) {
                    if (value != null && value < 150 && mFragmentCallbacks != null
                            && mViewServiceRequest != null) {
                        mViewServiceRequest.setmLatLng(latLng);
                        mViewServiceRequest.setmAddress(!TextUtils.isEmpty(indications) ?
                                address + ".\nIndicaciones: " + indications : address);
                        mFragmentCallbacks.onViewClicked(view, mViewServiceRequest);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            }, new VerifyAddressLatLngParams(latLng, address, context));
        }
    }


    @Override
    public void onDestroy() {
        mLocationFragmentView = null;
        mFragmentCallbacks = null;
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppModuleConstants.GPS_DIALOG_REQUEST_CODE && resultCode == RESULT_OK) {
            mHasEnabledGPS = true;
        }

        if (mLocationFragmentView != null) {
            mLocationFragmentView.onGPSEnabled();
        }
    }

    @Override
    public void subscribeTextViewToTextWatcherEvent(@NonNull TextView textView, @NonNull ITextViewValidatorMapper validatorMapper) {
        mCompositeDisposable.add(RxTextView.textChangeEvents(textView)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(validatorMapper)
                .subscribe(this::onTextChangeMappedEvent));
    }

    @Override
    public void onTextChangeMappedEvent(TextChangedMappedEvent textChangedMappedEvent) {
        if (textChangedMappedEvent == null || mLocationFragmentView == null) {
            return;
        }

        switch (textChangedMappedEvent.getSourceId()) {
            case R.id.et_address: {
                mLocationFragmentView.changeETIndicationsVisibility(textChangedMappedEvent.isValid() ?
                        View.VISIBLE : View.INVISIBLE);
                mLocationFragmentView.changeBTNNextAvailability(textChangedMappedEvent.isValid());
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == AppModuleConstants.LOCATION_PERMISSION_RESULT) {
            boolean userHasDeniedPermissions = false;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    userHasDeniedPermissions = true;
                    break;
                }
            }
            if (!userHasDeniedPermissions) {
                if (mLocationFragmentView != null) {
                    mLocationFragmentView.onLocationPermissionsGranted();
                }
            } else {
                mLocationFragmentView.showMessage("Error", "Permissions denied",
                        UtilModuleConstants.SHOW_ERROR_MESSAGE_ACTION,
                        false);
            }
        }
    }


}
