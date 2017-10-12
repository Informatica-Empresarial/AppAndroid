package co.com.lafemmeapp.lafemmeapp.presentation.splash;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends BaseActivity implements ISplashView {

    private ISplashPresenter mSplashPresenter;

    private ProgressBar mPBSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplashPresenter = new SplashPresenter(this);
        mSplashPresenter.onCreate(getIntent() != null ?
                getIntent().getExtras() : null);
    }

    @Override
    public void initViewComponents() {
        mPBSplash = (ProgressBar) findViewById(R.id.pb_splash);
    }

    @Override
    public void initComponents() {
        if (mSplashPresenter != null) {
            mSplashPresenter.getCities(this);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void populateView() {

    }

    @Override
    public void changeActivity(Bundle arguments, Class destinationActivity) {
        super.changeActivity(arguments, destinationActivity);
    }

    @Override
    public void showProgressBar() {
        if (mPBSplash != null) {
            mPBSplash.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        super.showMessage(title, description, action, showBothButtons);
    }

    @Override
    public void dissmissProgressBar() {
        if (mPBSplash != null) {
            mPBSplash.setVisibility(View.GONE);
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
}
