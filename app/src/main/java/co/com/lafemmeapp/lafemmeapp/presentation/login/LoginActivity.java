package co.com.lafemmeapp.lafemmeapp.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;

import co.com.lafemmeapp.core.domain.entities.InputFragmentDialogParams;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.mappers.EmailTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.mappers.PasswordTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.input_fragment_dialog.InputDialogFragment;

/**
 * TODO: refactor password reset
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    private ILoginPresenter mLoginPresenter;

    private EditText mETEmail;

    private EditText mETPassword;

    private Button mBTNLogin;

    private Toolbar mToolbar;

    private Button mBTNFBKLogin;

    private TextView mTVForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenter(this, this);
        mLoginPresenter.onCreate(getIntent() != null ?
                getIntent().getExtras() : null);
    }

    @Override
    public void initViewComponents() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mETEmail = (EditText) findViewById(R.id.et_email);
        mETPassword = (EditText) findViewById(R.id.et_password);
        mBTNLogin = (Button) findViewById(R.id.btn_login);
        mBTNFBKLogin = (Button) findViewById(R.id.btn_fbk_login);
        mLoginPresenter.initGoogleApiClient();
        mTVForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);

    }

    @Override
    public void initComponents() {
        setSupportActionBar(mToolbar);
        setmTVInternetConnection((TextView) findViewById(R.id.tv_internet_connection));
        mLoginPresenter.subscribeTextViewToTextWatcherEvent(mETEmail,
                EmailTextViewValidatorMapper.getInstance());

        mLoginPresenter.subscribeTextViewToTextWatcherEvent(mETPassword,
                PasswordTextViewValidatorMapper.getInstance());

        mBTNLogin.setEnabled(false);

        mBTNLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginPresenter != null) {
                    mLoginPresenter.login(mETEmail.getText().toString(),
                            mETPassword.getText().toString());
                }
            }
        });

        mBTNFBKLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFacebookLogin();
            }
        });

        mTVForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                android.app.Fragment prev = getFragmentManager()
                        .findFragmentByTag("inputDialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                InputFragmentDialogParams inputFragmentDialogParams =
                        new InputFragmentDialogParams(getString(R.string.password_reset_label),
                                getString(R.string.reset_password_message), true,
                                getString(R.string.accept_label), getString(R.string.cancel_label),
                                "forgotPassword", getString(R.string.et_email_label));


                InputDialogFragment inputFragmentDialog = InputDialogFragment.Companion
                        .newInstance(inputFragmentDialogParams);
                inputFragmentDialog.show(ft, "inputDialog");
            }
        });

        getSupportActionBar()
                .setTitle(R.string.login_title_label);


    }

    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        super.showMessage(title, description, action, showBothButtons);
    }

    @Override
    public void populateView() {
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void changeActivity(Bundle arguments, Class destinationActivity) {
        super.changeActivity(arguments, destinationActivity);
    }

    /**
     * This method show an error in the password field
     *
     * @param error error to show
     */
    @Override
    public void showEmailError(String error) {
        if (mETEmail != null && error != null) {
            mETEmail.setError(error);
        }
    }

    /**
     * This method show an error in the password field
     *
     * @param error error to show
     */
    @Override
    public void showPasswordError(String error) {
        if (mETPassword != null && error != null) {
            mETPassword.setError(error);
        }
    }

    /**
     * This method set the enable value fot he login {@link Button}
     *
     * @param enable value to set
     */
    @Override
    public void enableOrDisableLoginButton(boolean enable) {
        if (mBTNLogin != null) {
            mBTNLogin.setEnabled(enable);
            mBTNLogin.setBackground(enable ?
                    getResources().getDrawable(R.drawable.main_button_rounded_background) :
                    getResources().getDrawable(R.drawable.main_button_disable_rounded_background));
        }
    }

    /**
     * This method checks if the email field has an error
     *
     * @return true is an error exists, false otherwise
     */
    @Override
    public boolean hasEmailFieldError() {
        return (mETEmail == null
                || !TextUtils.isEmpty(mETEmail.getError())
                || TextUtils.isEmpty(mETEmail.getText()));
    }

    /**
     * This method checks if the password field has an error
     *
     * @return true is an error exists, false otherwise
     */
    @Override
    public boolean hasPasswordFieldError() {
        return (mETPassword == null
                || !TextUtils.isEmpty(mETPassword.getError())
                || TextUtils.isEmpty(mETPassword.getText()));
    }

    /**
     * This method will set a text in the email {@link EditText}
     *
     * @param email text to set
     */
    @Override
    public void setEmail(String email) {
        if (mETEmail != null && email != null) {
            mETEmail.setText(email);
        }
    }

    /**
     * This method will set a text in the password {@link EditText}
     *
     * @param password text to set
     */
    @Override
    public void setPassword(String password) {
        if (mETPassword != null && password != null) {
            mETPassword.setText(password);
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
    public void onFacebookLogOutSuccess() {
        if (mLoginPresenter != null) {
            mLoginPresenter.loginWithFacebook(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mLoginPresenter != null) {
            mLoginPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoginPresenter != null) {
            mLoginPresenter.onDestroy();
        }
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
    public void onViewClicked(View view, Parcelable parcelable) {

        if (mLoginPresenter != null) {
            mLoginPresenter.onViewClicked(view, parcelable);
        }

    }

    private void handleFacebookLogin() {
        if (mLoginPresenter != null) {
            if (AccessToken.getCurrentAccessToken() == null) {
                mLoginPresenter.loginWithFacebook(this);

            } else {
                mLoginPresenter.logOutFromFacebook(AccessToken.getCurrentAccessToken());

            }
        }

    }
}
