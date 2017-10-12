package co.com.lafemmeapp.lafemmeapp.presentation.dashboard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.Appointment;
import co.com.lafemmeapp.core.domain.entities.City;
import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest;
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequestBuilder;
import co.com.lafemmeapp.core.domain.entities.abstracts.User;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.lafemmeapp.events.TurnOffSOSEvent;
import co.com.lafemmeapp.lafemmeapp.presentation.brand.BrandFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.history.HistoryFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.history_detail.HistoryDetailFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.location.LocationFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.login.LoginActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.profile.ProfileFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.registry.RegisterActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time.SelectSpecialistTimeFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_user.SelectSpecialistUserFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.services.ServicesFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.RateAppointmentDialogFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.select_city.SelectCityDialogFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.voucher.VoucherFragment;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.UtilModuleConstants;
import co.com.lafemmeapp.utilmodule.presentation.events.AlertFragmentDialogActionTriggeredEvent;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oscargallon on 4/25/17.
 */
public class DashboardActivityPresenter implements IDashboardActivityPresenter {

    private IDashboardActivityView mDashboardActivityView;

    private IDashboardNavigator mDashboardNavigator;


    public DashboardActivityPresenter(IDashboardActivityView mDashboardActivityView) {
        this.mDashboardActivityView = mDashboardActivityView;
        mDashboardNavigator = new DashboardNavigator(mDashboardActivityView);
    }

    @Override
    public void onCreate(Bundle arguments) {
        if (mDashboardActivityView != null) {
            mDashboardActivityView.initViewComponents();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == AppModuleConstants.LOGIN_REQUEST_CODE
                || requestCode == AppModuleConstants.REGISTER_REQUEST_CODE) &&
                resultCode == Activity.RESULT_OK) {
            getNavigationDrawerMenu(false);
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

    private void checkIfHasToTurnOffSOS(SpecialistUser specialistUser) {
        if (specialistUser.isSOS()) {
            LaFemmeApplication
                    .getInstance()
                    .getmUseCaseFactory()
                    .turnOffSOSUseCase(Schedulers.computation(),
                            AndroidSchedulers.mainThread())
                    .execute(new DisposableObserver<SpecialistUser>() {
                        @Override
                        public void onNext(SpecialistUser specialistUser) {
                            deleteDevice();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            deleteDevice();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }, null);
        } else {
            deleteDevice();
        }
    }

    private void logOut(AlertFragmentDialogActionTriggeredEvent alertFragmentDialogActionTriggeredEvent) {

        if (alertFragmentDialogActionTriggeredEvent != null
                && alertFragmentDialogActionTriggeredEvent.isWasPositiveButton()) {
            mDashboardActivityView.showProgressDialog();
            LaFemmeApplication
                    .getInstance()
                    .getmUseCaseFactory()
                    .getSessionUseCase(Schedulers.computation(),
                            AndroidSchedulers.mainThread())
                    .execute(new DisposableObserver<User>() {
                        @Override
                        public void onNext(User user) {
                            if (user instanceof SpecialistUser) {
                                checkIfHasToTurnOffSOS((SpecialistUser) user);
                            } else {
                                deleteDevice();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            mDashboardActivityView.hideProgressDialog();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }, null);


        }

    }

    private void deleteDevice() {
        LaFemmeApplication
                .getInstance()
                .getmUseCaseFactory()
                .deleteDeviceUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread())
                .execute(new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        finishLogOut();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        finishLogOut();
                    }

                    @Override
                    public void onComplete() {

                    }
                }, LaFemmeApplication.getInstance());

    }

    private void finishLogOut() {
        if (mDashboardActivityView != null) {
            IUseCaseIterator iterator =
                    LaFemmeApplication
                            .getInstance()
                            .getmUseCaseFactory()
                            .getCloseSessionUseCase(Schedulers.computation(),
                                    AndroidSchedulers.mainThread());
            iterator.execute(new DisposableObserver<Boolean>() {
                @Override
                public void onNext(Boolean value) {
                    mDashboardActivityView.hideProgressDialog();
                    if (mDashboardActivityView != null) {
                        mDashboardActivityView.setNavigationDrawerMenu(R.menu.menu_without_session);
                        mDashboardActivityView.setNavigationHeader(-1);
                        while (mDashboardNavigator.getStackSize() != 0) {
                            Fragment fragment =
                                    mDashboardNavigator.getFragmentFromStack();
                            if (!(fragment instanceof BrandFragment)) {
                                mDashboardActivityView.removeCurrentFragment(fragment);
                                mDashboardActivityView.changeFragment(BrandFragment.newInstance(), null);
                            }

                        }

                    }
                    mDashboardActivityView.hideProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    mDashboardActivityView.hideProgressDialog();
                }

                @Override
                public void onComplete() {
                }
            }, null);
        }

    }

    @Override
    public void getDeviceToken(@NonNull Context context) {
        final WeakReference<Context> weakReference =
                new WeakReference<>(context);
        IUseCaseIterator<Boolean, Context> iterator =
                LaFemmeApplication
                        .getInstance()
                        .getmUseCaseFactory()
                        .getAndSendDeviceTokenUseCase(Schedulers.computation(),
                                AndroidSchedulers.mainThread());

        iterator.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean value) {
                Log.i("GCM", "TOKEN SENT");


            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.i("GCM", "ERROR");

            }

            @Override
            public void onComplete() {
                weakReference.clear();

            }
        }, weakReference.get());
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intent != null && intent.getExtras() != null && mDashboardActivityView != null) {

            if (intent.getExtras().containsKey(AppModuleConstants.NOTIFICATION_ACTION_KEY)) {
                String action = (String) intent.getExtras().get(AppModuleConstants.NOTIFICATION_ACTION_KEY);
                switch (action) {
                    case AppModuleConstants.SOS_APPOINTMENT_ACTION:
                    case AppModuleConstants.CANCELLED_APPOINTMENT_ACTION:
                    case AppModuleConstants.NEW_APPOINTMENT_ACTION: {
                        mDashboardActivityView.changeFragment(HistoryDetailFragment
                                .Companion.
                                        newInstance((Appointment)
                                                intent
                                                        .getParcelableExtra(AppModuleConstants
                                                                .APPOINTMENT_KEY)), null);
                        break;

                    }
                    case AppModuleConstants.RATE_APPOINMENT_ACTION: {
                        mDashboardActivityView.changeFragment(RateAppointmentDialogFragment
                                .Companion.
                                        newInstance((Appointment)
                                                intent
                                                        .getParcelableExtra(AppModuleConstants
                                                                .APPOINTMENT_KEY)), null);
                        break;
                    }
                }


            } else if (intent.getExtras().containsKey(AppModuleConstants.SOS_EXTRA_KEY)) {
                LaFemmeApplication
                        .getInstance()
                        .getmBus()
                        .post(new TurnOffSOSEvent(true));
                mDashboardActivityView.showMessage("S", "s", "", false);
            }

        }
    }

    @Override
    public int getStackSize() {
        return mDashboardNavigator != null ? mDashboardNavigator.getStackSize() :
                0;
    }

    private void returnToHomeFragment() {
        if (mDashboardNavigator != null && mDashboardNavigator.getStackSize() > 1) {
            while (mDashboardNavigator.getStackSize() > 1) {
                mDashboardActivityView.
                        removeCurrentFragment(mDashboardNavigator
                                .getFragmentFromStack());
            }
            BrandFragment brandFragment = (BrandFragment) mDashboardNavigator
                    .getFragmentFromStack();
            mDashboardActivityView.setCurrentFragment(brandFragment);
            mDashboardNavigator.addFragmentToStack(brandFragment);
        }
    }

    @Override
    public void onNavigationItemSelected(int id) {
        if (mDashboardActivityView != null) {
            switch (id) {
                case R.id.appointments_history: {
                    if (mDashboardActivityView.getCurrentFragment() == null
                            || !(mDashboardActivityView.getCurrentFragment()
                            instanceof HistoryFragment)) {
                        mDashboardActivityView.changeFragment(HistoryFragment.Companion
                                        .newInstance(),
                                null);
                    }

                    break;
                }
                case R.id.register: {
                    mDashboardActivityView.startActivityForResult(null, AppModuleConstants.REGISTER_REQUEST_CODE,
                            RegisterActivity.class);
                    break;
                }
                case R.id.log_out: {
                    mDashboardActivityView.showMessage("Salir", "Estas seguro que deseas cerrar tu session?",
                            UtilModuleConstants.LOG_OUT_MESSAGE_ACTION,
                            true);

                    break;
                }
                case R.id.log_in: {
                    mDashboardActivityView.startActivityForResult(null,
                            AppModuleConstants.LOGIN_REQUEST_CODE,
                            LoginActivity.class);
                    break;
                }
                case R.id.home: {
                    returnToHomeFragment();
                    break;
                }
                case R.id.profile: {
                    mDashboardActivityView.changeFragment(ProfileFragment.Companion.newInstance(), null);
                    break;
                }

            }
            mDashboardActivityView.closeDrawer();
        }


    }

    @Override
    public void onFragmentReplaced(Fragment fragment) {
        if (mDashboardNavigator != null) {
            mDashboardNavigator.addFragmentToStack(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDashboardNavigator != null && mDashboardActivityView != null) {
            Fragment fragment =
                    mDashboardNavigator.getFragmentFromStack();
            if (fragment == null) {
                mDashboardActivityView.callSuperBackPressed();
            } else {
                mDashboardActivityView.removeCurrentFragment(fragment);
                if (mDashboardNavigator.getStackSize() == 0) {
                    mDashboardActivityView.callSuperBackPressed();
                }
            }
        }
    }

    private int getIdMenuToShow(User user) {
        if (user != null && user instanceof SpecialistUser) {
            return R.menu.menu_with_specialist_session;
        } else if (user != null) {
            return R.menu.menu_with_session;
        }
        return R.menu.menu_without_session;
    }

    @Override
    public void getNavigationDrawerMenu(final boolean initView) {
        checkSession(new DisposableObserver<User>() {
            @Override
            public void onNext(User value) {
                if (mDashboardActivityView != null) {
                    mDashboardActivityView
                            .setNavigationDrawerMenu(getIdMenuToShow(value));
                    mDashboardActivityView.setNavigationHeader(value != null ?
                            R.layout.nav_header_dashboard : -1);
                    mDashboardActivityView.changeHeaderUserName(value != null ?
                            String.format(Locale.getDefault(),
                                    "%s %s", value.getName(),
                                    value.getLastName()) : null);
                    mDashboardActivityView.changeHeaderUserEmail(value != null ?
                            value.getEmail() : null);
                    if (initView) {
                        mDashboardActivityView.initComponents();
                        mDashboardActivityView.changeFragment(
                                value != null &&
                                        value instanceof SpecialistUser ?
                                        HistoryFragment.Companion
                                                .newInstance() : BrandFragment.newInstance(),
                                null);
                    } else if (value != null && value instanceof SpecialistUser) {
                        while (mDashboardNavigator.getStackSize() != 0) {
                            mDashboardActivityView.removeCurrentFragment(mDashboardNavigator.getFragmentFromStack());

                        }
                        mDashboardActivityView.changeFragment(HistoryFragment.Companion.newInstance(), null);
                    } else {
                        while (mDashboardNavigator.getStackSize() != 0) {
                            mDashboardActivityView.removeCurrentFragment(mDashboardNavigator.getFragmentFromStack());

                        }
                        mDashboardActivityView.changeFragment(BrandFragment.newInstance(), null);
                    }

                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NoSessionException) {
                    if (mDashboardActivityView != null) {
                        mDashboardActivityView
                                .setNavigationDrawerMenu(R.menu.menu_without_session);
                        if (initView) {
                            mDashboardActivityView.initComponents();
                            mDashboardActivityView.changeFragment(BrandFragment.newInstance(),
                                    null);
                        }

                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void checkSession(DisposableObserver<User> customerDisposableObserver) {
        IUseCaseIterator iterator =
                LaFemmeApplication
                        .getInstance()
                        .getmUseCaseFactory()
                        .getSessionUseCase(Schedulers.computation(),
                                AndroidSchedulers.mainThread());

        iterator.execute(customerDisposableObserver, null);
    }


    private void btnScheduleClicked(final Parcelable parcelable) {
        checkSession(new DisposableObserver<User>() {
            @Override
            public void onNext(User value) {
                if (mDashboardActivityView != null) {
                    if (value != null) {

                        Location location = new Location(LocationManager.GPS_PROVIDER);
                        City city = (City) parcelable;
                        location.setLatitude(city.getLat());
                        location.setLongitude(city.getLon());
                        mDashboardActivityView.changeFragment(ServicesFragment
                                .newInstance(location, city.getName()), null);
                    } else {
                        mDashboardActivityView.startActivityForResult(null,
                                AppModuleConstants.LOGIN_REQUEST_CODE,
                                LoginActivity.class);
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NoSessionException) {
                    if (mDashboardActivityView != null) {
                        mDashboardActivityView.startActivityForResult(null,
                                AppModuleConstants.LOGIN_REQUEST_CODE,
                                LoginActivity.class);
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void btnSOSCLicked() {
        checkSession(new DisposableObserver<User>() {
            @Override
            public void onNext(User value) {
                if (mDashboardActivityView != null) {
                    if (value != null) {
                        ViewServicesRequest viewServicesRequest =
                                new ViewServicesRequestBuilder().createViewServicesRequest();
                        viewServicesRequest.setmSOS(true);
                        mDashboardActivityView
                                .changeFragment(LocationFragment
                                        .newInstance(viewServicesRequest), null);
                    } else {
                        mDashboardActivityView.startActivityForResult(null,
                                AppModuleConstants.LOGIN_REQUEST_CODE,
                                LoginActivity.class);
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NoSessionException) {
                    if (mDashboardActivityView != null) {
                        mDashboardActivityView.startActivityForResult(null,
                                AppModuleConstants.LOGIN_REQUEST_CODE,
                                LoginActivity.class);
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void decideIfHasToShowSelectCityDialog() {
        checkSession(new DisposableObserver<User>() {
            @Override
            public void onNext(User value) {
                if (mDashboardActivityView != null) {
                    if (value != null) {

                        mDashboardActivityView.changeFragment(SelectCityDialogFragment.Companion
                                .newInstance(), null);
                    } else {
                        mDashboardActivityView.startActivityForResult(null,
                                AppModuleConstants.LOGIN_REQUEST_CODE,
                                LoginActivity.class);
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof NoSessionException) {
                    if (mDashboardActivityView != null) {
                        mDashboardActivityView.startActivityForResult(null,
                                AppModuleConstants.LOGIN_REQUEST_CODE,
                                LoginActivity.class);
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (view != null && mDashboardActivityView != null) {
            switch (view.getId()) {
                case R.id.btn_schedule: {
                    decideIfHasToShowSelectCityDialog();
                    break;
                }
                case R.id.btn_sos: {
                    btnSOSCLicked();
                    break;
                }
                case R.id.btn_next: {
                    if (parcelable != null) {
                        if (((ViewServicesRequest) parcelable)
                                .ismSOS()) {
                            mDashboardActivityView
                                    .changeFragment(SelectSpecialistUserFragment
                                                    .newInstance((ViewServicesRequest) parcelable),
                                            null);
                        } else {
                            mDashboardActivityView
                                    .changeFragment(LocationFragment.newInstance((ViewServicesRequest) parcelable),
                                            null);
                        }

                    }
                    break;
                }
                case R.id.btn_next_location: {

                    if (parcelable != null) {
                        if (((ViewServicesRequest) parcelable)
                                .ismSOS()) {
                            mDashboardActivityView
                                    .changeFragment(ServicesFragment
                                            .newInstance((ViewServicesRequest) parcelable), null);
                        } else {
                            mDashboardActivityView
                                    .changeFragment(SelectSpecialistUserFragment
                                                    .newInstance((ViewServicesRequest) parcelable),
                                            null);
                        }
                    }

                    break;
                }
                case R.id.btn_random_specialist:
                case R.id.rl_specialist_container: {

                    if (parcelable != null) {
                        if (((ViewServicesRequest) parcelable).ismSOS()) {
                            ViewServicesRequest viewServicesRequest = (ViewServicesRequest) parcelable;
                            viewServicesRequest.setmChooseDate(viewServicesRequest
                                    .getmSpecialistUserSelected()
                                    .getValidStartDateTimes().get(0));
                            mDashboardActivityView
                                    .changeFragment(VoucherFragment.Companion.newInstance(viewServicesRequest)
                                            , null);
                        } else {
                            mDashboardActivityView
                                    .changeFragment(SelectSpecialistTimeFragment.Companion.newInstance((ViewServicesRequest) parcelable),
                                            null);
                        }
                    }
                }
                break;

                case R.id.btn_next_select_specialist_time: {
                    mDashboardActivityView
                            .changeFragment(VoucherFragment.Companion.newInstance((ViewServicesRequest) parcelable)
                                    , null);
                    break;
                }
                case R.id.btn_next_voucher: {
                    mDashboardActivityView.showMessage("HOLA",
                            "this was triggerd from kotlin",
                            "NO ACTO", false);
                    break;
                }
                case R.id.btn_cancel_appointment: {
                    returnToHomeFragment();
                    mDashboardActivityView.changeFragment(HistoryFragment.Companion
                            .newInstance(), null);
                    break;
                }
                case R.id.btn_cancel_voucher:
                case R.id.btn_accept_appointment_confirmation: {
                    returnToHomeFragment();
                    break;
                }
                case R.id.btn_negative:
                case R.id.btn_positive: {
                    if (parcelable != null) {
                        AlertFragmentDialogActionTriggeredEvent alertFragmentDialogActionTriggeredEvent
                                = (AlertFragmentDialogActionTriggeredEvent) parcelable;
                        if (alertFragmentDialogActionTriggeredEvent
                                .getAction().equals(UtilModuleConstants
                                        .LOG_OUT_MESSAGE_ACTION)) {
                            logOut(alertFragmentDialogActionTriggeredEvent);
                        }
                    }

                    break;
                }
                case R.id.btn_logout: {
                    mDashboardActivityView.showMessage("Salir",
                            "Estas seguro que deseas cerrar tu session?",
                            UtilModuleConstants.LOG_OUT_MESSAGE_ACTION,
                            true);
                    break;
                }
                case R.id.iv_show_appoitments_detail: {
                    mDashboardActivityView
                            .changeFragment(HistoryDetailFragment.Companion
                                            .newInstance((Appointment) parcelable)
                                    , null);
                    break;
                }
                case R.id.btn_rate_service: {
                    mDashboardActivityView.changeFragment(RateAppointmentDialogFragment.Companion
                            .newInstance((Appointment) parcelable), null);
                    break;
                }

                case R.id.cl_cities_container: {
                    btnScheduleClicked(parcelable);
                }
            }
        }
    }
}
