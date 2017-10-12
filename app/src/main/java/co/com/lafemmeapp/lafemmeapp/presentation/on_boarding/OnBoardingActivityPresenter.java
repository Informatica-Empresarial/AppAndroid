package co.com.lafemmeapp.lafemmeapp.presentation.on_boarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.lafemmeapp.presentation.dashboard.DashboardActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.registry.RegisterActivity;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oscargallon on 4/23/17.
 */
public class OnBoardingActivityPresenter implements IOnBoardingActivityPresenter {

    private IOnBoardingActivityView mOnBoardingActivityView;


    public OnBoardingActivityPresenter(IOnBoardingActivityView mOnBoardingActivityView) {
        this.mOnBoardingActivityView = mOnBoardingActivityView;
    }

    @Override
    public void onCreate(Bundle arguments) {
        if (mOnBoardingActivityView != null) {
            mOnBoardingActivityView.initViewComponents();
            mOnBoardingActivityView.initComponents();
            final IUseCaseIterator<Boolean, Boolean> iterator
                    = LaFemmeApplication.getInstance()
                    .getmUseCaseFactory()
                    .setHasWatchOnBoardingUseCase(Schedulers.io(),
                            AndroidSchedulers.mainThread());

            iterator.execute(new DisposableObserver<Boolean>() {
                @Override
                public void onNext(Boolean success) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    iterator.dispose();
                }
            }, true);
        }
    }

    @Override
    public void onDestroy() {
        mOnBoardingActivityView = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppModuleConstants.REGISTER_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            if (mOnBoardingActivityView != null) {
                mOnBoardingActivityView.changeActivity(null, DashboardActivity.class);
            }
        }
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
    public void goToRegisterActivity() {
        if (mOnBoardingActivityView != null) {
            mOnBoardingActivityView.changeActivity(null, RegisterActivity.class);
        }
    }

    @Override
    public void goToServiceActivity() {

    }

    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (mOnBoardingActivityView != null && view != null) {
            switch (view.getId()) {
                case R.id.btn_sign_up: {
                    Bundle bundle =
                            new Bundle();
                    bundle.putBoolean(AppModuleConstants.CALLED_FROM_ONBOARDING, true);
                    mOnBoardingActivityView.changeActivity(bundle, RegisterActivity.class);
                    break;
                }
                case R.id.tv_skip: {
                    mOnBoardingActivityView.changeActivity(null, DashboardActivity.class);
                    break;
                }
            }
        }
    }
}
