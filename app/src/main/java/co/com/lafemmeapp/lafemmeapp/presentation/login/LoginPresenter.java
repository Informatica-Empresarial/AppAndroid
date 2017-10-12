package co.com.lafemmeapp.lafemmeapp.presentation.login;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.entities.InputDialogFragmentResponse;
import co.com.lafemmeapp.core.domain.entities.abstracts.User;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.customer.LogOutWithFacebookUseCase;
import co.com.lafemmeapp.core.domain.use_cases.customer.LoginUseCase;
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import co.com.lafemmeapp.dataprovider.params.LoginParams;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by oscargallon on 4/20/17.
 * This is the {@link ILoginPresenter} implementation
 * This presenter will control the {@link LoginActivity}
 */
public class LoginPresenter implements ILoginPresenter {


    private static final int RC_SAVE = 1;
    private static final int RC_HINT = 2;
    private static final int RC_READ = 3;

    private CredentialRequest mCredentialRequest;

    private ILoginView mLoginView;

    private GoogleApiClient mGoogleApiClient;
    /**
     * So we call Disposable the methods  that listen events,
     * as we are listing multiple events we have to have a list
     * of the Disposable (listeners) that are running on the app in order to unSubscribe those once
     * we are not using them any more.
     * That list in RX is called {@link CompositeDisposable}
     */
    private CompositeDisposable mCompositeDisposable;


    private WeakReference<Activity> mActivityWeakReference;

    private CallbackManager mCallbackManager;
    //endregion

    public LoginPresenter(ILoginView mLoginView, Activity context) {
        this.mLoginView = mLoginView;
        mCompositeDisposable = new CompositeDisposable();
        mActivityWeakReference = new WeakReference<>(context);
        mCallbackManager = CallbackManager.Factory
                .create();

    }

    //region Activity Listeners
    @Override
    public void onCreate(Bundle arguments) {
        if (mLoginView != null) {
            mLoginView.initViewComponents();
            mLoginView.initComponents();
            mLoginView.populateView();
        }
    }

    @Override
    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;

        }
        if (mActivityWeakReference != null) {
            mActivityWeakReference.clear();
        }
        mLoginView = null;
        System.gc();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case RC_HINT:
                // Drop into handling for RC_READ
            case RC_READ:
                if (resultCode == RESULT_OK) {
                    boolean isHint = (requestCode == RC_HINT);
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    processCredentials(credential);
                } else {
                    Log.e("CREDENTIALS", "Credential Read: NOT OK");
                }

                break;
            case RC_SAVE:
                if (resultCode == RESULT_OK) {
                    Log.d("CREDENTIALS", "Credential Save: OK");
                } else {
                    Log.e("CREDENTIALS", "Credential Save: NOT OK");
                }

                break;
        }
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    //endregion

    //region Presenter Methods
    @SuppressWarnings("unchecked")
    @Override
    public void login(final String user, final String password) {
        if (mLoginView != null) {
            mLoginView.showProgressDialog();
            final IUseCaseIterator<SessionResponse, LoginParams> iterator =
                    LaFemmeApplication.getInstance()
                            .getmUseCaseFactory().loginUseCase(Schedulers.computation(),
                            AndroidSchedulers.mainThread());

            iterator.execute(new DisposableObserver<SessionResponse>() {
                @Override
                public void onNext(SessionResponse value) {
                    saveCredentialsOnGoogle(user, password);
                    if (mLoginView != null) {
                        mLoginView.finishActivity();
                    }
                    mLoginView.hideProgressDialog();
                    onComplete();
                }

                @Override
                public void onError(Throwable e) {
                    //TODO Handle the login errors
                    e.printStackTrace();
                    mLoginView.showMessage("Login", e.getMessage(),
                            "", false);
                    mLoginView.hideProgressDialog();
                    onComplete();
                }

                @Override
                public void onComplete() {

                    iterator.dispose();
                }
            }, new LoginParams(user, password));
        }


    }


    /**
     * This method just adds an Observable for an Specific {@link TextView}
     * when we call {@link RxTextView} textChangeEvents method, this method returns an
     * observable that will look at any text change inside the {@link TextView} passed
     * then we add where we want to run the method that looks the text changes with subscribeOn,
     * and in which thread we want to receive the result.
     * So as we are looking for text changes inside a {@link TextView}, we will pass a method
     * that will execute some code and return is the text is valid or not, that method is what we call
     * validatorMapper.
     * Once the validatorMapper has check is the text is valid or not we just call the onTextChangeMappedEvent
     * method to do something about it.
     * Notice
     * 1. that  we will listen text changes, so as this is a subscription we will added to
     * the {@link CompositeDisposable} object that we have in this class to remove this subscription
     * when is not useful.
     * 2.
     *
     * @param textView        The {@link TextView} in which we will listen the text changes
     * @param validatorMapper The function to evaluate if the text is valid or not
     */
    @Override
    public void subscribeTextViewToTextWatcherEvent(@NonNull final TextView textView,
                                                    @NonNull ITextViewValidatorMapper validatorMapper) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    /**
     * This method will evaluate if the {@link TextChangedMappedEvent}
     * for a specific TextView was valid or not
     *
     * @param textChangedMappedEvent {@link TextChangedMappedEvent} that contains the {@link TextView} id
     *                               and if the textChangedMappedEvent was valid or not
     */
    @Override
    public void onTextChangeMappedEvent(TextChangedMappedEvent textChangedMappedEvent) {
        if (textChangedMappedEvent == null || mLoginView == null) {
            return;
        }

        switch (textChangedMappedEvent.getSourceId()) {
            case R.id.et_email:
                if (!textChangedMappedEvent.isValid()) {
                    mLoginView.showEmailError("Must be and email with @ symbol and domain");
                }

                break;
            case R.id.et_password:
                if (!textChangedMappedEvent.isValid()) {
                    mLoginView.showPasswordError("Must be from 6 to 16 characters and" +
                            " at least one upper case Letter");
                }
        }

        mLoginView.enableOrDisableLoginButton(!mLoginView.hasEmailFieldError()
                && !mLoginView.hasPasswordFieldError());


    }
    //endregion

    //region Google Methods

    @Override
    public void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mActivityWeakReference.get())
                .addConnectionCallbacks(this)
                .addApi(Auth.CREDENTIALS_API)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void loginWithFacebook(Activity activity) {
        LoginManager
                .getInstance()
                .registerCallback(getmCallbackManager(), this);

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
                if (mLoginView != null) {
                    mLoginView.onFacebookLogOutSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        }, accessToken);
    }

    private void saveCredentialsOnGoogle(String email, String password) {
        if (mLoginView == null || mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            return;
        }
        final Credential credential =
                new Credential.Builder(email)
                        .setPassword(password)
                        .build();


        Auth.CredentialsApi.save(mGoogleApiClient, credential)
                .setResultCallback(new ResolvingResultCallbacks<Status>(mActivityWeakReference.get(), RC_SAVE) {
                    @Override
                    public void onSuccess(@NonNull Status status) {
                        Log.i("CC", "Saved");
                    }

                    @Override
                    public void onUnresolvableFailure(@NonNull Status status) {
                        Log.i("FAlla", "Saved");
                    }
                });
    }

    private void requestCredentialsFromGoogle() {
        if (mLoginView == null || mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            return;
        }
        final CredentialRequest mCredentialRequest = new CredentialRequest.Builder()
                .setPasswordLoginSupported(true)
                .build();
        //TODO show spinner
        Auth.CredentialsApi.request(mGoogleApiClient, mCredentialRequest)
                .setResultCallback(new ResultCallbacks<CredentialRequestResult>() {
                    @Override
                    public void onSuccess(@NonNull CredentialRequestResult credentialRequestResult) {
                        //Hide spinner
                        Status status = credentialRequestResult.getStatus();
                        if (status.isSuccess()) {
                            processCredentials(credentialRequestResult.getCredential());
                        } else if (status.getStatusCode() == CommonStatusCodes.RESOLUTION_REQUIRED) {
                            startStatusForCredentialResolution(status);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Status status) {
                        if (status.getStatusCode() == CommonStatusCodes.RESOLUTION_REQUIRED) {
                            startStatusForCredentialResolution(status);
                        }
                    }
                });
    }

    private void startStatusForCredentialResolution(Status status) {
        try {
            status.startResolutionForResult(mActivityWeakReference
                    .get(), RC_READ);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


    private void processCredentials(Credential credential) {
        if (mLoginView == null) {
            return;
        }
        String accountType = credential.getAccountType();
        if (accountType == null) {
            mLoginView.setEmail(credential.getId());
            mLoginView.setPassword(credential.getPassword());
        } else if (accountType.equals(IdentityProviders.GOOGLE)) {
            /**
             * TODO
             * Sign in with google or other shit for now just the same
             */
            mLoginView.setEmail(credential.getId());
            mLoginView.setPassword(credential.getPassword());
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestCredentialsFromGoogle();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("FAILD", connectionResult.getErrorMessage());
    }
    //endregion


    public CallbackManager getmCallbackManager() {
        return mCallbackManager;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        if (loginResult != null && loginResult.getAccessToken() != null
                && mLoginView != null) {
            mLoginView.showProgressDialog();
            LaFemmeApplication
                    .getInstance()
                    .getmUseCaseFactory()
                    .loginWithFacebookUseCase(Schedulers.computation(),
                            AndroidSchedulers.mainThread())
                    .execute(new DisposableObserver<SessionResponse>() {
                        @Override
                        public void onNext(SessionResponse sessionResponse) {
                            mLoginView.hideProgressDialog();
                            if (mLoginView != null) {
                                mLoginView.finishActivity();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            mLoginView.hideProgressDialog();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }, loginResult.getAccessToken().getToken());
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }


    private void forgotPasswordRequest(String email) {
        LaFemmeApplication
                .getInstance()
                .getmUseCaseFactory()
                .requestPasswordResetUseCase(Schedulers.io(),
                        AndroidSchedulers.mainThread())
                .execute(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        if (mLoginView != null) {
                            mLoginView.showMessage("Contraseña",
                                    "Tu contraseña ha sido reseteada por favor mira tu email",
                                    "whatever",
                                    false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                }, email);
    }


    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (view != null && parcelable != null && parcelable instanceof InputDialogFragmentResponse) {
            if (((InputDialogFragmentResponse) parcelable).getWasPositive()) {
                forgotPasswordRequest(((InputDialogFragmentResponse) parcelable).getText());
            }
        }
    }
}
