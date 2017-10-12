package co.com.lafemmeapp.lafemmeapp.presentation.splash;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.List;

import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.system.GetCitiesUseCase;
import co.com.lafemmeapp.core.domain.use_cases.system.GetHasWatchOnboarding;
import co.com.lafemmeapp.core.domain.use_cases.system.GetLocalCitiesUseCase;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.lafemmeapp.presentation.dashboard.DashboardActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.on_boarding.OnBoardingActivity;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Stephys on 7/05/17.
 */

public class SplashPresenter implements ISplashPresenter {

    private ISplashView mSplashView;


    public SplashPresenter(ISplashView iSplashView) {
        this.mSplashView = iSplashView;
    }

    @Override
    public void onCreate(Bundle arguments) {
        if (mSplashView != null) {
            mSplashView.initViewComponents();
            mSplashView.initComponents();
        }
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
    public void getCities(final Context context) {
        if (mSplashView != null) {
            mSplashView.showProgressBar();
            final IUseCaseIterator<List<String>, Boolean> systemUseCaseIterator =
                    LaFemmeApplication.getInstance()
                            .getmUseCaseFactory()
                            .getCitiesUseCase(Schedulers.computation(),
                                    AndroidSchedulers.mainThread());

            systemUseCaseIterator.execute(new DisposableObserver<List<String>>() {
                @Override
                public void onNext(List<String> citiesList) {
                    hasWatchOnboarding();
                }

                @Override
                public void onError(Throwable e) {
                    getLocalCities(context.getResources());

                }

                @Override
                public void onComplete() {
                    systemUseCaseIterator.dispose();
                }
            }, true);
        }


    }

    private void getLocalCities(Resources resources) {
        IUseCaseIterator systemUseCaseIterator = new GetLocalCitiesUseCase(Schedulers.computation(),
                AndroidSchedulers.mainThread());
        systemUseCaseIterator.execute(new DisposableObserver<List<String>>() {
            @Override
            public void onNext(List<String> value) {
                hasWatchOnboarding();
            }

            @Override
            public void onError(Throwable e) {
                hasWatchOnboarding();
            }

            @Override
            public void onComplete() {

            }
        }, resources);
    }

    private void hasWatchOnboarding() {
        final IUseCaseIterator<Boolean, Void> iterator = LaFemmeApplication
                .getInstance()
                .getmUseCaseFactory().getHasWatchOnBoardingUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread());

        iterator.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean hasWatchOnboarding) {
                if (mSplashView != null) {
                    if (hasWatchOnboarding) {
                        mSplashView.changeActivity(null, DashboardActivity.class);
                    } else {
                        mSplashView.changeActivity(null, OnBoardingActivity.class);
                    }
                    mSplashView.dissmissProgressBar();

                }
            }

            @Override
            public void onError(Throwable e) {
                mSplashView.dissmissProgressBar();
            }

            @Override
            public void onComplete() {
                iterator.dispose();
            }
        }, null);
    }
}
