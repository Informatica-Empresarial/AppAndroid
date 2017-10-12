package co.com.lafemmeapp.lafemmeapp.presentation.registry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.mappers.AddressTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.mappers.CityTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.mappers.EmailTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.mappers.NameAndLastNameValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.mappers.PasswordConfirmationTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.mappers.PasswordTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.mappers.PhoneNumberValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;

public class RegisterActivity extends BaseActivity implements IRegisterView {

    private Toolbar mToolbar;
    private Button mBTNSingInGoogle;
    private Button mBTNFbLoginButoon;
    private GoogleApiClient mGoogleApiClient;
    private IRegistryPresenter mRegisterPresenter;
    private EditText mETName;
    private EditText mETLastName;
    private EditText mETEmail;
    private EditText mETPhoneNumber;
    private EditText mETPassword;
    private EditText mETPasswordConfirmation;
    private AutoCompleteTextView mACTVCity;
    private EditText mETAddress;
    private Button mBTNRegistry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRegisterPresenter = new RegistryPresenter(this);
        mRegisterPresenter.onCreate(getIntent() != null ?
                getIntent().getExtras() : null);

    }


    @Override
    public void initGoogleConnection() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(mRegisterPresenter)
                .addOnConnectionFailedListener(mRegisterPresenter)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void initViewComponents() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mETName = (EditText) findViewById(R.id.et_name);
        mETLastName = (EditText) findViewById(R.id.et_last_name);
        mETEmail = (EditText) findViewById(R.id.et_email);
        mETPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        mETAddress = (EditText) findViewById(R.id.et_address);
        mACTVCity = (AutoCompleteTextView) findViewById(R.id.actv_city);
        mETPassword = (EditText) findViewById(R.id.et_password);
        mETPasswordConfirmation = (EditText) findViewById(R.id.et_password_confirmation);
        mBTNSingInGoogle = (Button) findViewById(R.id.sign_in_button);
        mBTNFbLoginButoon = (Button) findViewById(R.id.btn_fbk_login);
        mBTNRegistry = (Button) findViewById(R.id.btn_registry);
    }


    @Override
    public void initComponents() {
        setSupportActionBar(mToolbar);
        initGoogleConnection();
        PasswordConfirmationTextViewValidatorMapper.getInstance().setPwdToCheck(null);
        mBTNFbLoginButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFacebookLogin();
            }
        });


        mBTNRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRegisterPresenter != null) {
                    mRegisterPresenter.createUser(
                            mETName.getText().toString(),
                            mETLastName.getText().toString(),
                            mETEmail.getText().toString(),
                            mETPhoneNumber.getText().toString(),
                            mETAddress.getText().toString(),
                            mACTVCity.getText().toString(),
                            mETPassword.getText().toString());
                }
            }
        });

        mBTNSingInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, AppModuleConstants.GOOGLE_SIGN_IN_REQUEST_CODE);
            }
        });

        if (mRegisterPresenter != null) {
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mETName, NameAndLastNameValidatorMapper
                    .getInstance());
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mETLastName,
                    NameAndLastNameValidatorMapper
                            .getInstance());
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mETEmail,
                    EmailTextViewValidatorMapper.getInstance());
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mETPhoneNumber,
                    PhoneNumberValidatorMapper.getInstance());
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mETPassword,
                    PasswordTextViewValidatorMapper.getInstance());
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mETPasswordConfirmation,
                    PasswordConfirmationTextViewValidatorMapper.getInstance());
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mACTVCity,
                    CityTextViewValidatorMapper.getInstance());
            mRegisterPresenter.subscribeTextViewToTextWatcherEvent(mETAddress,
                    AddressTextViewValidatorMapper.getInstance());
        }

        getSupportActionBar()
                .setTitle(R.string.registry_label);
    }

    private void handleFacebookLogin() {
        if (mRegisterPresenter != null) {
            if (AccessToken.getCurrentAccessToken() == null) {
                {
                    mRegisterPresenter.loginWithFacebook(RegisterActivity.this);
                }
            } else {
                mRegisterPresenter.logOutFromFacebook(AccessToken.getCurrentAccessToken());

            }
        }

    }

    @Override
    public void openGoogleSingInActivity() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, AppModuleConstants.GOOGLE_SIGN_IN_REQUEST_CODE);
    }


    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        super.showMessage(title, description, action, showBothButtons);
    }

    @Override
    public void populateView() {
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mBTNSingInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleSingInActivity();
            }
        });
    }

    @Override
    public void changeActivity(Bundle arguments, Class destinationActivity) {
        super.changeActivity(arguments, destinationActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mRegisterPresenter != null) {
            mRegisterPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void showGoogleSignInButton() {
        if (mBTNSingInGoogle != null) {
            mBTNSingInGoogle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideGoogleSignInButton() {
        if (mBTNSingInGoogle != null) {
            mBTNSingInGoogle.setVisibility(View.GONE);
        }

    }

    @Override
    public void setName(String name) {
        if (name != null) {
            mETName.setText(name);
        }
    }

    @Override
    public void setLastName(String lastName) {
        if (lastName != null) {
            mETLastName.setText(lastName);
        }
    }

    @Override
    public void setEmail(String email) {
        if (email != null) {
            mETEmail.setText(email);
        }
    }

    @Override
    public void showNameError(String name) {
        if (mETName != null) {
            mETName.setError(name);
        }
    }

    @Override
    public void showLastNameError(String lastName) {
        if (mETLastName != null) {
            mETLastName.setError(lastName);
        }
    }

    @Override
    public void showPasswordError(String s) {
        if (mETPassword != null) {
            mETPassword.setError(s);
        }
    }

    @Override
    public void showPasswordConfirmationError(String s) {
        if (mETPasswordConfirmation != null) {
            mETPasswordConfirmation.setError(s);
        }
    }

    @Override
    public void showCityError(String s) {
        if (mACTVCity != null) {
            mACTVCity.setError(s);
        }
    }

    @Override
    public void showPhoneNumberError(String s) {
        if (mETPhoneNumber != null) {
            mETPhoneNumber.setError(s);
        }
    }

    @Override
    public void showAddressError(String s) {
        if (mETAddress != null) {
            mETAddress.setError(s);
        }
    }

    @Override
    public void showEmailError(String s) {
        if (mETEmail != null) {
            mETEmail.setError(s);
        }
    }

    @Override
    public void changeBTNRegistryAvailability(boolean enabled) {
        if (mBTNRegistry != null) {
            mBTNRegistry.setEnabled(enabled);
            mBTNRegistry.setBackground(enabled ?
                    getResources().getDrawable(R.drawable.main_button_rounded_background) :
                    getResources().getDrawable(R.drawable.main_button_disable_rounded_background));
        }
    }

    @Override
    public void onFacebookLogOutSuccess() {
        if (mRegisterPresenter != null) {
            mRegisterPresenter.loginWithFacebook(this);
        }
    }

    @Override
    public void setCities(List<String> cities) {

        CityTextViewValidatorMapper.getInstance().setCities(cities);
        if (cities != null && mACTVCity != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, cities);
            mACTVCity.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRegisterPresenter != null) {
            mRegisterPresenter.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void finishActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }

    @Override
    public void onViewClicked(View view, @Nullable Parcelable parcelable) {

    }


}
