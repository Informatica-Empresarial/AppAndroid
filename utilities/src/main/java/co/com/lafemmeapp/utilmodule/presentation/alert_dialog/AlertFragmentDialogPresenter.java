package co.com.lafemmeapp.utilmodule.presentation.alert_dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import co.com.lafemmeapp.utilmodule.presentation.UtilModuleConstants;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.AlertFragmentDialogActionTriggeredEvent;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 5/13/17.
 */

public class AlertFragmentDialogPresenter implements IAlertFragmentDialogPresenter {

    private IAlertFragmentDialogView mAlertFragmentDialogView;

    private IFragmentCallbacks mFragmentCallbacks;

    private AlertFragmentInitialDialogParams mAlertFragmentInitialDialogParams;


    public AlertFragmentDialogPresenter(IAlertFragmentDialogView mAlertFragmentDialogView) {
        this.mAlertFragmentDialogView = mAlertFragmentDialogView;
    }

    @Override
    public void onCreate(Bundle arguments) {
        if (arguments != null) {
            mAlertFragmentInitialDialogParams =
                    arguments.getParcelable(UtilModuleConstants.ALERT_DIALOG_FRAGMENT_PARAMS);
        }
    }

    @Override
    public void onCreateView(View view) {
        if (mAlertFragmentDialogView != null
                && mAlertFragmentInitialDialogParams != null) {
            mAlertFragmentDialogView.initViewComponents(view);
            mAlertFragmentDialogView.initComponents();

            mAlertFragmentDialogView
                    .setDialogMessage(mAlertFragmentInitialDialogParams.getMessage());
            mAlertFragmentDialogView
                    .setDialogTitle(mAlertFragmentInitialDialogParams.getTitle());
            mAlertFragmentDialogView
                    .setPositiveButtonText(mAlertFragmentInitialDialogParams.getPositiveButtonText());
            mAlertFragmentDialogView
                    .setNegativeButtonText(mAlertFragmentInitialDialogParams.getNegativeButtonText());
            if (!mAlertFragmentInitialDialogParams.isShowBothButtons()) {
                mAlertFragmentDialogView.hideNegativeButton();
            }
        }
    }

    @Override
    public void onAttach(IFragmentCallbacks fragmentCallback) {
        mFragmentCallbacks = fragmentCallback;
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void subscribeTextViewToTextWatcherEvent(@NonNull TextView textView, @NonNull ITextViewValidatorMapper validatorMapper) {

    }

    @Override
    public void onTextChangeMappedEvent(TextChangedMappedEvent textChangedMappedEvent) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (mFragmentCallbacks != null) {
            mFragmentCallbacks.onViewClicked(view, parcelable);
        }
    }


    @Override
    public void actionTriggered(View view, boolean wasPositive) {
        if(mAlertFragmentInitialDialogParams!=null){
            onViewClicked(view, new AlertFragmentDialogActionTriggeredEvent(wasPositive,
                    mAlertFragmentInitialDialogParams.getAction()));
        }

    }
}
