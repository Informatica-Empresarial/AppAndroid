package co.com.lafemmeapp.lafemmeapp.application;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.multidex.MultiDex;

import com.squareup.otto.Bus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.use_cases.IUseCaseFactory;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.UseCaseFactory;
import co.com.lafemmeapp.dataprovider.local.DBDataSource;
import co.com.lafemmeapp.dataprovider.local.exceptions.DBErrorException;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.di.AppModule;
import co.com.lafemmeapp.lafemmeapp.di.DaggerAppComponent;
import co.com.lafemmeapp.lafemmeapp.presentation.sos.SOSIntentService;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by oscargallon on 4/18/17.
 * This will be the class that handles the {@link Application} on this App
 * When the {@link Application} is created we create the database in our DB source
 * and when the {@link Application} {@link Context} is attached we install the {@link MultiDex}
 */
public class LaFemmeApplication extends Application {

    @Inject
    IUseCaseFactory mUseCaseFactory;

    private static LaFemmeApplication instance;

    public static LaFemmeApplication getInstance() {
        return instance;
    }

    private Bus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build()
                .inject(this);


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        mBus = new Bus();

        try {
            DBDataSource.getInstance()
                    .initDataSource(this);
        } catch (DBErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public IUseCaseFactory getmUseCaseFactory() {
        return mUseCaseFactory;
    }

    public Intent getSOSAlamarRequestIntent() {
        return new Intent(getApplicationContext(), SOSIntentService.class);
    }

    public PendingIntent getSOSAlarmRequestPendingIntent() {
        return PendingIntent.getService(getApplicationContext(),
                AppModuleConstants.SOS_ALARM_REQUEST_CODE, getSOSAlamarRequestIntent(),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void initSOSService() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES),
                getSOSAlarmRequestPendingIntent());

    }

    public void cancelSOSService() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        alarmManager.cancel(getSOSAlarmRequestPendingIntent());
        final IUseCaseIterator<Boolean, Void> iterator =
                mUseCaseFactory.deleteSOSActivationDateUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread());

        iterator.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                iterator.dispose();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                iterator.dispose();
            }

            @Override
            public void onComplete() {

            }
        }, null);

    }

    public Bus getmBus() {
        return mBus;
    }
}
