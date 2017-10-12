package co.com.lafemmeapp.lafemmeapp.presentation.brand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.Constants;
import co.com.lafemmeapp.core.domain.entities.City;
import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.params.GoogleAPIClientParams;
import co.com.lafemmeapp.core.domain.params.LocationRequestParams;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.customer.GetGoogleAPIClientUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.GetUserLocationUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.ShowEnableGPSDialogUseCase;
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException;
import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.UtilModuleConstants;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by oscargallon on 5/2/17.
 */

public class BrandFragmentPresenter implements IBrandFragmentPresenter {

    private IBrandFragmentView mBrandFragmentView;

    private IFragmentCallbacks mFragmentCallbacks;

    private Location mLocation;

    public BrandFragmentPresenter(IBrandFragmentView mBrandFragmentView) {
        this.mBrandFragmentView = mBrandFragmentView;
    }

    @Override
    public void onCreate(Bundle arguments) {

    }

    @Override
    public void getLocation(@NonNull Context context) {
        IUseCaseIterator iterator
                = new GetUserLocationUseCase(Schedulers.computation(),
                AndroidSchedulers.mainThread());

        iterator.execute(new DisposableObserver<Location>() {
            @Override
            public void onNext(Location value) {
                mLocation = value;

            }

            @Override
            public void onError(Throwable e) {
                Log.i("LOCATION", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i("LOCATION", "Complete");
            }
        }, new LocationRequestParams(context, LocationManager.NETWORK_PROVIDER));
    }

    @Override
    public void onCreateView(View view) {
        if (mBrandFragmentView != null) {
            mBrandFragmentView.initViewComponents(view);
            mBrandFragmentView.initComponents();
            mBrandFragmentView.requestPermissions();
        }
    }

    @Override
    public void onAttach(IFragmentCallbacks fragmentCallback) {
        mFragmentCallbacks = fragmentCallback;
    }

    private void showGPSDialog(final Activity activity, GoogleApiClient googleApiClient) {
        IUseCaseIterator iterator =
                new ShowEnableGPSDialogUseCase(Schedulers.io(), AndroidSchedulers.mainThread());
        iterator.execute(new DisposableObserver<Status>() {
            @Override
            public void onNext(Status status) {
                switch (status.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS: {
                        if (mBrandFragmentView != null) {
                            mBrandFragmentView.onGPSEnabled();
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
    public void onDestroy() {
        mBrandFragmentView = null;
        mFragmentCallbacks = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppModuleConstants.GPS_DIALOG_REQUEST_CODE && resultCode == RESULT_OK) {
            if (mBrandFragmentView != null) {
                mBrandFragmentView.onGPSEnabled();
            }
        }
    }

    @Override
    public void subscribeTextViewToTextWatcherEvent(@NonNull TextView textView,
                                                    @NonNull ITextViewValidatorMapper validatorMapper) {

    }

    @Override
    public void onTextChangeMappedEvent(TextChangedMappedEvent textChangedMappedEvent) {

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == AppModuleConstants.LOCATION_PERMISSION_RESULT) {
            boolean userHasDeniedPermissions = false;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    userHasDeniedPermissions = true;
                    break;
                }
            }
            if (!userHasDeniedPermissions) {
                if (mBrandFragmentView != null) {
                    mBrandFragmentView.onLocationPermissionsGranted();
                }
            } else {
                mBrandFragmentView.showMessage("Error", "Permissions denied",
                        UtilModuleConstants.SHOW_ERROR_MESSAGE_ACTION, false);
            }
        }
    }

    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (mFragmentCallbacks != null) {
            mFragmentCallbacks.onViewClicked(view, mLocation);

        }
    }


}
