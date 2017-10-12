package co.com.lafemmeapp.lafemmeapp.presentation.on_boarding.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 4/25/17.
 */

public class OnBoardingFragmentPresenter implements IOnBoardingFragmentPresenter {

    private IOnBoardingFragmentView mOnBoardingFragmentView;

    private IFragmentCallbacks mOnBoardingFragmentCallbacks;

    private String mTitle;

    private String mDescription;

    private int mImageResouce;


    public OnBoardingFragmentPresenter(IOnBoardingFragmentView mOnBoardingFragmentView) {
        this.mOnBoardingFragmentView = mOnBoardingFragmentView;
    }

    @Override
    public void onCreate(Bundle arguments) {
        if (arguments != null) {
            mTitle = arguments.getString(AppModuleConstants.TITLE_KEY);
            mDescription = arguments.getString(AppModuleConstants.DESCRIPTION_KEY);
            mImageResouce = arguments.getInt(AppModuleConstants.IMAGE_RESOURCE_KEY);

        }
    }

    @Override
    public void onCreateView(View view) {
        if (mOnBoardingFragmentView != null) {
            mOnBoardingFragmentView.initViewComponents(view);
            mOnBoardingFragmentView.initComponents();
            mOnBoardingFragmentView.setTVTitleText(mTitle);
            mOnBoardingFragmentView.setTVDescriptionText(mDescription);
            mOnBoardingFragmentView.setImageResource(mImageResouce);

        }
    }

    @Override
    public void onAttach(IFragmentCallbacks fragmentCallback) {
        mOnBoardingFragmentCallbacks = fragmentCallback;
    }


    @Override
    public void onDestroy() {
        mOnBoardingFragmentCallbacks = null;
        mOnBoardingFragmentView = null;
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }


}
