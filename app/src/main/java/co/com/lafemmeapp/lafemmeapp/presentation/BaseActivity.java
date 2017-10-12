package co.com.lafemmeapp.lafemmeapp.presentation;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Locale;

import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.IConnectivityChangesCallback;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.receivers.ConnectivityChangeReceiver;
import co.com.lafemmeapp.utilmodule.presentation.alert_dialog.AlertFragmentDialog;
import co.com.lafemmeapp.utilmodule.presentation.alert_dialog.AlertFragmentDialogInitialParamsBuilder;
import co.com.lafemmeapp.utilmodule.presentation.custom_progress_bar.CustomProgressDialog;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseView;

public class BaseActivity extends AppCompatActivity implements
        IBaseView, IConnectivityChangesCallback {

    private ConnectivityChangeReceiver mConnectivityChangeReceiver;

    private TextView mTVInternetConnection;

    private CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mConnectivityChangeReceiver = new ConnectivityChangeReceiver(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mConnectivityChangeReceiver != null) {
            registerReceiver(mConnectivityChangeReceiver,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mConnectivityChangeReceiver != null) {
            unregisterReceiver(mConnectivityChangeReceiver);
        }
    }

    @Override
    public void initViewComponents() {

    }

    @Override
    public void initComponents() {

    }

    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager()
                .findFragmentByTag(AppModuleConstants.ALERT_DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        AlertFragmentDialog alertFragmentDialog = AlertFragmentDialog
                .newInstance(new AlertFragmentDialogInitialParamsBuilder()
                        .setMessage(description)
                        .setTitle(title)
                        .setShowBothButtons(showBothButtons)
                        .setAction(action)
                        .setPositiveButtonText(getString(R.string.accept_label))
                        .createAlertDialogFragmentInitialParams());
        alertFragmentDialog.show(ft, AppModuleConstants.ALERT_DIALOG_TAG);
    }


    @Override
    public void populateView() {

    }

    @Override
    public void changeActivity(Bundle arguments, Class destinationActivity) {
        final Intent intent = new Intent(this, destinationActivity);
        if (arguments != null) {
            intent.putExtras(arguments);
        }

        startActivity(intent);
    }

    public void setmTVInternetConnection(TextView tvInternetConnection) {
        mTVInternetConnection = tvInternetConnection;
        if (mTVInternetConnection != null) {
            mTVInternetConnection.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;
                    final int TOUCH_OFFSET = 32;

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        int[] pos = new int[2];
                        mTVInternetConnection.getLocationOnScreen(pos);
                        int editTextRight = pos[0] + mTVInternetConnection.getWidth();

                        Drawable rightDrawable = mTVInternetConnection.getCompoundDrawables()[DRAWABLE_RIGHT];
                        int drawableWidth = rightDrawable.getBounds().width();
                        float eventRawX = event.getRawX();
                        if (eventRawX >= (editTextRight - drawableWidth - TOUCH_OFFSET)) {
                            mTVInternetConnection.setVisibility(View.GONE);
                            return true;
                        }
                    }

                    return false;
                }
            });
        }

    }

    @Override
    public void onConnectivityChange(boolean isConnected) {
        Log.i("INTERNET", String.format(Locale.getDefault(),
                "Cambio: %s", isConnected));
        if (mTVInternetConnection != null) {
            mTVInternetConnection.setVisibility(isConnected ?
                    View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void startActivityForResult(Bundle arguments, int requestCode, Class destinationActivity) {

    }

    public void showProgressDialog() {
        if (mCustomProgressDialog == null) {
            mCustomProgressDialog = new CustomProgressDialog(new WeakReference<Context>(this),
                    getResources().getColor(R.color.colorPrimary));
        }
        mCustomProgressDialog.show();
    }

    public void hideProgressDialog() {
        mCustomProgressDialog.hide();
    }
}
