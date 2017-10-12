package co.com.lafemmeapp.utilmodule.presentation.alert_dialog;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import co.com.lafemmeapp.utilmodule.R;
import co.com.lafemmeapp.utilmodule.presentation.UtilModuleConstants;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

public class AlertFragmentDialog extends DialogFragment implements IAlertFragmentDialogView,
        View.OnClickListener {


    private TextView mTVMessage;

    private Toolbar mToolbar;

    private Button mBTNPositive;

    private Button mBTNNegative;

    private IAlertFragmentDialogPresenter mAlertFragmentDialogPresenter;


    public AlertFragmentDialog() {
        // Required empty public constructor
    }

    public static AlertFragmentDialog newInstance(AlertFragmentInitialDialogParams alertDialogFragmentInitialParams) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(UtilModuleConstants.ALERT_DIALOG_FRAGMENT_PARAMS, alertDialogFragmentInitialParams);
        AlertFragmentDialog alertFragmentDialog = new AlertFragmentDialog();
        alertFragmentDialog.setArguments(bundle);
        return alertFragmentDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAlertFragmentDialogPresenter != null) {
            mAlertFragmentDialogPresenter.onCreate(getArguments());
        }
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert_fragment_dialog, container, false);
        if (mAlertFragmentDialogPresenter != null) {
            mAlertFragmentDialogPresenter.onCreateView(view);
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAlertFragmentDialogPresenter = new AlertFragmentDialogPresenter(this);
        mAlertFragmentDialogPresenter.onAttach((IFragmentCallbacks) activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAlertFragmentDialogPresenter = new AlertFragmentDialogPresenter(this);
        mAlertFragmentDialogPresenter.onAttach((IFragmentCallbacks) context);
    }

    @Override
    public void setDialogTitle(String text) {
        if (mToolbar != null && text != null) {
            mToolbar.setTitle(text);
        }
    }

    @Override
    public void setDialogMessage(String text) {
        if (mTVMessage != null && text != null) {
            mTVMessage.setText(text);
        }
    }

    @Override
    public void setPositiveButtonText(String text) {
        if (mBTNPositive != null && text != null) {
            mBTNPositive.setText(text);
        }
    }

    @Override
    public void setNegativeButtonText(String text) {
        if (mBTNNegative != null && text != null) {
            mBTNNegative.setText(text);
        }
    }

    @Override
    public void hideNegativeButton() {
        if (mBTNNegative != null) {
            mBTNNegative.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViewComponents(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mBTNNegative = (Button) view.findViewById(R.id.btn_negative);
        mBTNPositive = (Button) view.findViewById(R.id.btn_positive);
        mTVMessage = (TextView) view.findViewById(R.id.tv_message);
    }

    @Override
    public void initComponents() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mBTNNegative.setOnClickListener(this);

        mBTNPositive.setOnClickListener(this);
    }


    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
       
    }

    @Override
    public void populateView() {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void onClick(View v) {
        if (mAlertFragmentDialogPresenter != null) {
            mAlertFragmentDialogPresenter
                    .actionTriggered(v, v.getId() == R.id.btn_positive);
            dismissAllowingStateLoss();
        }
    }
}
