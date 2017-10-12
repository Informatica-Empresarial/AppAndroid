package co.com.lafemmeapp.lafemmeapp.presentation.on_boarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.on_boarding.fragments.OnBoardingFragment;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

public class OnBoardingActivity extends BaseActivity
        implements IOnBoardingActivityView,
        IFragmentCallbacks {

    private ViewPager mVPOnBoarding;

    private TabLayout mTLOnBoarding;

    private TextView mTVSkip;

    private IOnBoardingActivityPresenter mOnBoardingActivityPresenter;

    private OnBoardingPageAdapter mOnBoardingPageAdapter;

    private Button mBTNSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mOnBoardingActivityPresenter = new OnBoardingActivityPresenter(this);
        mOnBoardingActivityPresenter.onCreate(getIntent() != null ?
                getIntent().getExtras() : null);

    }

    @Override
    public void initViewComponents() {
        mVPOnBoarding = (ViewPager) findViewById(R.id.vp_on_boarding);
        mTLOnBoarding = (TabLayout) findViewById(R.id.tl_on_boarding);
        mTVSkip = (TextView) findViewById(R.id.tv_skip);
        mBTNSignUp = (Button) findViewById(R.id.btn_sign_up);

    }

    @Override
    public void initComponents() {
        mOnBoardingPageAdapter = new OnBoardingPageAdapter(getFragmentManager());
        mOnBoardingPageAdapter.addFragmentToAdapter(OnBoardingFragment.newInstance("Bienvenida",
                getString(R.string.onboarding_first_text), R.drawable.onboarding_first));
        mOnBoardingPageAdapter.addFragmentToAdapter(OnBoardingFragment.newInstance("Comodidad",
                getString(R.string.onboarding_second_text), R.drawable.onboarding_second));
        mOnBoardingPageAdapter.addFragmentToAdapter(OnBoardingFragment.newInstance("Calidad",
                getString(R.string.onboarding_third_text), R.drawable.onboarding_third));
        mVPOnBoarding.setAdapter(mOnBoardingPageAdapter);
        mTLOnBoarding.setupWithViewPager(mVPOnBoarding);
        mTVSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBoardingActivityPresenter != null) {
                    mOnBoardingActivityPresenter.onViewClicked(v, null);
                }

            }
        });

        mBTNSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBoardingActivityPresenter != null) {
                    mOnBoardingActivityPresenter.onViewClicked(v, null);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mOnBoardingActivityPresenter != null) {
            mOnBoardingActivityPresenter.onActivityResult(requestCode, resultCode,
                    data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOnBoardingActivityPresenter != null) {
            mOnBoardingActivityPresenter.onDestroy();
        }
    }

    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        super.showMessage(title, description, action, showBothButtons);
    }

    @Override
    public void populateView() {

    }

    @Override
    public void changeActivity(Bundle arguments, Class destinationActivity) {
        super.changeActivity(arguments, destinationActivity);
    }

    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (mOnBoardingActivityPresenter != null) {
            mOnBoardingActivityPresenter.onViewClicked(view, parcelable);
        }
    }

    @Override
    public void startActivityForResult(Bundle arguments, int requestCode, Class destinationActivity) {
        Intent intent = new Intent(this, destinationActivity);
        if (arguments != null) {
            intent.putExtras(arguments);
        }
        startActivityForResult(intent, requestCode);
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
