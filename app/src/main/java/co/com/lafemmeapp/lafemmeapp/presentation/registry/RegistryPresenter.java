package co.com.lafemmeapp.lafemmeapp.presentation.registry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.entities.FacebookUserData;
import co.com.lafemmeapp.core.domain.entities.GoogleUserData;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.customer.GetInfoFromFacebookUserUseCase;
import co.com.lafemmeapp.core.domain.use_cases.customer.GetInfoFromGoogleUserUseCase;
import co.com.lafemmeapp.core.domain.use_cases.customer.LogOutWithFacebookUseCase;
import co.com.lafemmeapp.core.domain.use_cases.customer.RegisterUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.GetCitiesUseCase;
import co.com.lafemmeapp.dataprovider.params.RegisterParams;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.lafemmeapp.presentation.dashboard.DashboardActivity;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Stephys on 13/04/17.
 */
public class RegistryPresenter implements IRegistryPresenter {

    private IRegisterView mRegisterView;

    private CallbackManager mCallbackManager;

    private IUseCaseIterator mRegistryUseCaseIterator;

    private CompositeDisposable mCompositeDisposable;

    private HashMap<Integer, Boolean> mTextViewValidatorHashMap;

    private Observable<Boolean> mFieldsValidatorObservable;

    private boolean wasCalledFromOnBoarding;

    public RegistryPresenter(IRegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mCallbackManager = CallbackManager.Factory
                .create();

        mRegistryUseCaseIterator =
                LaFemmeApplication
                        .getInstance()
                        .getmUseCaseFactory()
                        .registerUseCase(Schedulers.computation(),
                                AndroidSchedulers.mainThread());

        mCompositeDisposable = new CompositeDisposable();
        mTextViewValidatorHashMap = new HashMap<>();
        mFieldsValidatorObservable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                if (mTextViewValidatorHashMap != null && mTextViewValidatorHashMap.size() > 0) {
                    for (Map.Entry<Integer, Boolean> entry : mTextViewValidatorHashMap
                            .entrySet()) {
                        if (!entry.getValue()) {
                            emitter.onNext(false);
                            emitter.onComplete();
                            return;
                        }
                    }
                    emitter.onNext(true);
                    emitter.onComplete();

                } else {
                    emitter.onNext(false);
                    emitter.onComplete();
                }
            }
        });


    }

    @Override
    public void onCreate(Bundle arguments) {
        if (mRegisterView != null) {
            mRegisterView.initViewComponents();
            mRegisterView.initComponents();
            mRegisterView.populateView();
            getCities();
            if (arguments != null) {
                wasCalledFromOnBoarding =
                        arguments.getBoolean(AppModuleConstants.CALLED_FROM_ONBOARDING, false);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppModuleConstants.GOOGLE_SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result != null && result.getSignInAccount() != null) {
                result.getSignInAccount().getIdToken();
                getUserDataFromGoogleSignIn(result);
            }

        }

        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getUserDataFromGoogleSignIn(GoogleSignInResult result) {
        GetInfoFromGoogleUserUseCase getInfoFromGoogleUserUseCase =
                new GetInfoFromGoogleUserUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread());
        getInfoFromGoogleUserUseCase.execute(new DisposableObserver<GoogleUserData>() {
            @Override
            public void onNext(GoogleUserData value) {
                if (mRegisterView != null) {
                    if (!value.getFamilyName().equals("null") && !value.getGivenName().equals("null")) {
                        mRegisterView.setName(value.getGivenName());
                        mRegisterView.setLastName(value.getFamilyName());
                    } else {
                        if (value.getDisplayName() != null) {
                            String[] fullName = value.getDisplayName().split(" ");
                            if (fullName.length > 1) {
                                mRegisterView.setName(fullName[0]);
                                mRegisterView.setLastName(fullName[1]);
                            }
                        }
                    }

                    mRegisterView.setEmail(value.getEmail());

                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("ALGO", e.toString());
            }

            @Override
            public void onComplete() {

            }
        }, result);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mRegisterView != null) {
            mRegisterView.hideGoogleSignInButton();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mRegisterView != null) {
            mRegisterView.showGoogleSignInButton();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDestroy() {
        mRegisterView = null;
        mCallbackManager = null;
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void subscribeTextViewToTextWatcherEvent(@NonNull TextView textView,
                                                    @NonNull ITextViewValidatorMapper validatorMapper) {

        if (mCompositeDisposable != null && mTextViewValidatorHashMap != null) {
            mTextViewValidatorHashMap.put(textView.getId(), false);
            mCompositeDisposable.add(RxTextView.textChangeEvents(textView)
                    .skipInitialValue()
                    .distinctUntilChanged()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(validatorMapper)
                    .subscribe(new Consumer<TextChangedMappedEvent>() {
                        @Override
                        public void accept(TextChangedMappedEvent textChangedMappedEvent) throws Exception {
                            onTextChangeMappedEvent(textChangedMappedEvent);
                        }
                    }));


        }


    }

    @Override
    public void onTextChangeMappedEvent(TextChangedMappedEvent textChangedMappedEvent) {
        if (textChangedMappedEvent == null || mRegisterView == null) {
            return;
        }

        if (mTextViewValidatorHashMap != null) {
            mTextViewValidatorHashMap.put(textChangedMappedEvent.getSourceId(),
                    textChangedMappedEvent.isValid());
        }

        switch (textChangedMappedEvent.getSourceId()) {
            case R.id.et_name: {
                mRegisterView.showNameError(textChangedMappedEvent.isValid() ?
                        null : "Your Name must be greater than 3 characters");
                break;
            }
            case R.id.et_last_name: {
                mRegisterView.showLastNameError(textChangedMappedEvent.isValid() ?
                        null : "Your Last Name must be greater than 3 characters");
                break;
            }
            case R.id.et_password: {
                mRegisterView.showPasswordError(textChangedMappedEvent.isValid() ?
                        null : "La contraseña debe estar entre 6 y 16 digitos");
                break;
            }
            case R.id.et_password_confirmation: {
                mRegisterView.showPasswordConfirmationError(textChangedMappedEvent.isValid() ?
                        null : "Las contraseñas no coinciden");
                break;
            }
            case R.id.actv_city: {
                mRegisterView.showCityError(textChangedMappedEvent.isValid() ?
                        null : "Por favor ingresa una ciudad valida");
                break;
            }
            case R.id.et_phone_number: {
                mRegisterView.showPhoneNumberError(textChangedMappedEvent.isValid() ?
                        null : "Por favor ingresa una telefono valido");
                break;
            }
            case R.id.et_email: {
                mRegisterView.showEmailError(textChangedMappedEvent.isValid() ?
                        null : "Por favor ingresa un email valido");
                break;
            }
            case R.id.et_address: {
                mRegisterView.showAddressError(textChangedMappedEvent.isValid() ?
                        null : "Por favor ingresa una direccion valida");
                break;
            }
        }
        if (mFieldsValidatorObservable != null) {
            mFieldsValidatorObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (mRegisterView != null) {
                                mRegisterView.changeBTNRegistryAvailability(aBoolean);
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        IUseCaseIterator<FacebookUserData, AccessToken> iterator =
                new GetInfoFromFacebookUserUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread());
        iterator.execute(new DisposableObserver<FacebookUserData>() {
            @Override
            public void onNext(FacebookUserData value) {
                if (mRegisterView != null) {
                    mRegisterView.setEmail(value.getEmail());
                    mRegisterView.setName(value.getFirstName());
                    mRegisterView.setLastName(value.getLastName());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("FACEBOOK", "ERROR");
                /**
                 * TODO show error message in the app
                 */
            }

            @Override
            public void onComplete() {

            }
        }, loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        /**
         * TODO show error message in the app
         */
        Log.i("mierda", "mierda");
    }

    @Override
    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }

    @Override
    public void createUser(String firstName, String lastName, String email, String phoneNumber,
                           String address, String city, String password) {

        if (mRegistryUseCaseIterator != null && mRegisterView != null) {
            mRegisterView.showProgressDialog();
            mRegistryUseCaseIterator.execute(new DisposableObserver<Customer>() {
                @Override
                public void onNext(Customer value) {
                    mRegisterView.hideProgressDialog();
                    if (mRegisterView != null) {
                        if (wasCalledFromOnBoarding) {
                            mRegisterView.changeActivity(null, DashboardActivity.class);
                        } else {
                            mRegisterView.finishActivity();
                        }

                    }


                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    mRegisterView.hideProgressDialog();
                    mRegisterView.showMessage("Register", e.getMessage(),
                            "", false);

                }

                @Override
                public void onComplete() {

                }
            }, new RegisterParams(firstName, lastName, address, email, phoneNumber, password, city));
        }

    }

    @Override
    public void loginWithFacebook(Activity activity) {
        LoginManager
                .getInstance()
                .registerCallback(getCallbackManager(), this);

        List<String> permission =
                new ArrayList<>();
        permission.add("email");
        permission.add("public_profile");
        permission.add("user_friends");
        LoginManager.getInstance()
                .setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK)
                .logInWithReadPermissions(activity,
                        permission);
    }

    @Override
    public void logOutFromFacebook(AccessToken accessToken) {
        IUseCaseIterator<Boolean, AccessToken> iterator =
                new LogOutWithFacebookUseCase(Schedulers.io(),
                        AndroidSchedulers.mainThread());

        iterator.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean value) {
                if (mRegisterView != null) {
                    mRegisterView.onFacebookLogOutSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, accessToken);
    }

    private void getCities() {
        final IUseCaseIterator<List<String>, Boolean> systemUseCaseIterator =
                LaFemmeApplication.getInstance()
                        .getmUseCaseFactory()
                        .getCitiesUseCase(Schedulers.computation(),
                                AndroidSchedulers.mainThread());
        systemUseCaseIterator.execute(new DisposableObserver<List<String>>() {
            @Override
            public void onNext(List<String> citiesList) {
                if (mRegisterView != null) {
                    mRegisterView.setCities(citiesList);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                systemUseCaseIterator.dispose();
            }
        }, false);

    }
}
