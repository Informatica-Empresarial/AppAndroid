package co.com.lafemmeapp.lafemmeapp.presentation.dashboard;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import co.com.lafemmeapp.core.domain.entities.Appointment;
import co.com.lafemmeapp.core.domain.entities.City;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.history.HistoryFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.RateAppointmentDialogFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.select_city.SelectCityDialogFragment;
import co.com.lafemmeapp.utilmodule.presentation.alert_dialog.AlertFragmentDialog;
import co.com.lafemmeapp.utilmodule.presentation.alert_dialog.AlertFragmentDialogInitialParamsBuilder;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IDashboardActivityView, IFragmentCallbacks {

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private NavigationView mNavigationView;

    private Toolbar mToolbar;

    private IDashboardActivityPresenter mDashboardActivityPresenter;

    private Fragment mCurrentFragment;

    private TextView mTVHeaderUserName;

    private TextView mTVHeaderUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mDashboardActivityPresenter = new DashboardActivityPresenter(this);
        mDashboardActivityPresenter.onCreate(getIntent() != null ?
                getIntent().getExtras() : null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.getDeviceToken(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.onNewIntent(intent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.onBackPressed();
        } else {
            callSuperBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void setNavigationDrawerMenu(int menuId) {
        if (mNavigationView != null) {
            mNavigationView.getMenu().clear();
            mNavigationView.inflateMenu(menuId);
        }
    }

    @Override
    public void setNavigationHeader(int header) {
        if (header != -1) {
            View headerView = mNavigationView.inflateHeaderView(header);
            if (headerView != null) {
                mTVHeaderUserEmail = (TextView) headerView
                        .findViewById(R.id.tv_header_user_email);
                mTVHeaderUserName = (TextView) headerView
                        .findViewById(R.id.tv_header_user_name);
            }
        } else if (mNavigationView.getHeaderCount() != 0) {
            mNavigationView.removeHeaderView(mNavigationView.getHeaderView(0));
            mTVHeaderUserEmail = null;
            mTVHeaderUserName = null;
        }
    }

    @Override
    public void changeHeaderUserName(String userName) {
        if (mTVHeaderUserName != null && userName != null) {
            mTVHeaderUserName.setText(userName);
        }
    }

    @Override
    public void changeHeaderUserEmail(String userEmail) {
        if (mTVHeaderUserEmail != null && userEmail != null) {
            mTVHeaderUserEmail.setText(userEmail);
        }
    }

    @Override
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setCurrentFragment(Fragment fragment) {
        mCurrentFragment = fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.onNavigationItemSelected(item.getItemId());

        }

        return true;
    }

    @Override
    public void initViewComponents() {

        setmTVInternetConnection((TextView) findViewById(R.id.tv_internet_connection));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.getNavigationDrawerMenu(true);
        }


    }

    @Override
    public void initComponents() {
        setSupportActionBar(mToolbar);
        getSupportActionBar()
                .setTitle(getString(R.string.splash_title));
        mActionBarDrawerToggle =
                new ActionBarDrawerToggle(
                        this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
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
    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void changeFragment(Fragment fragment, String tag) {
        if (fragment != null && mDashboardActivityPresenter != null) {
            mCurrentFragment = fragment;
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            if (mDashboardActivityPresenter.getStackSize() != 0) {
                fragmentTransaction.setCustomAnimations(R.animator.slide_right_2,
                        R.animator.slide_left_2);
            }
            fragmentTransaction.add(R.id.fm_fragment, fragment, tag)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
            if (mDashboardActivityPresenter != null) {
                mDashboardActivityPresenter.onFragmentReplaced(fragment);
            }
        }
    }

    @Override
    public void removeCurrentFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getFragmentManager()
                    .beginTransaction();

            if(mDashboardActivityPresenter.getStackSize()!=0){
                fragmentTransaction.setCustomAnimations(R.animator.slide_right_2,
                        R.animator.slide_right);
            }


            fragmentTransaction.remove(fragment)
                    .commitAllowingStateLoss();
            getFragmentManager().popBackStack();
            mCurrentFragment = null;
        }


    }

    @Override
    public void callSuperBackPressed() {
        finishAffinity();
    }

    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.onViewClicked(view, parcelable);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.onRequestPermissionsResult(requestCode,
                    permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mDashboardActivityPresenter != null) {
            mDashboardActivityPresenter.onActivityResult(requestCode, resultCode, data);
        }
        if (mCurrentFragment != null) {
            mCurrentFragment.onActivityResult(requestCode, resultCode, data);
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
