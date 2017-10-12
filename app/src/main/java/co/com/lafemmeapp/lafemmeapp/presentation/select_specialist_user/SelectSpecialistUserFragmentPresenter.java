package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import co.com.lafemmeapp.core.domain.entities.AppointmentAvailability;
import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.core.domain.entities.ViewService;
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest;
import co.com.lafemmeapp.core.domain.params.GetAppointmentAvailabilityRequestParams;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.dataprovider.exceptions.APiException;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentServiceRequest;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oscargallon on 5/8/17.
 */

public class SelectSpecialistUserFragmentPresenter implements ISelectSpecialistUserFragmentPresenter {

    private ViewServicesRequest mViewServicesRequest;

    private IFragmentCallbacks mFragmentCallbacks;

    private ISelectSpecialistUserFragmentView mSelectSpecialistUserFragmentView;

    private SimpleDateFormat mSimpleDateFormat;

    public SelectSpecialistUserFragmentPresenter(ISelectSpecialistUserFragmentView mScheduleFragmentView) {
        this.mSelectSpecialistUserFragmentView = mScheduleFragmentView;
        mSimpleDateFormat = new SimpleDateFormat(AppModuleConstants.DATE_TIME_FORMAT,
                Locale.getDefault());
    }


    @Override
    public void getAppointmentAvailability() {
        if (mSelectSpecialistUserFragmentView != null) {
            mSelectSpecialistUserFragmentView.showProgressBar();


            Calendar currentCalendar =
                    Calendar.getInstance();
            Date fromDate = currentCalendar
                    .getTime();
            currentCalendar
                    .add(Calendar.DAY_OF_MONTH, 15);
            Date toDate = currentCalendar.getTime();


            final List<AppointmentServiceRequest> appointmentServiceRequestList
                    = new ArrayList<>();
            for (ViewService viewService : mViewServicesRequest.getmViewServicesSelected()) {
                appointmentServiceRequestList
                        .add(new AppointmentServiceRequest(viewService.getUuid(),
                                viewService.getQuantity()));
            }

            final IUseCaseIterator iterator = mViewServicesRequest.ismSOS() ?
                    LaFemmeApplication
                            .getInstance()
                            .getmUseCaseFactory()
                            .getAppointmentAvailabilitySOSUseCase(Schedulers.computation(),
                                    AndroidSchedulers.mainThread()) :
                    LaFemmeApplication
                            .getInstance()
                            .getmUseCaseFactory()
                            .getAppointmentAvailabilityUseCase(Schedulers.computation(),
                                    AndroidSchedulers.mainThread());

            iterator.execute(new DisposableObserver<AppointmentAvailability>() {
                @Override
                public void onNext(AppointmentAvailability value) {
                    if (mSelectSpecialistUserFragmentView != null) {
                        if (value != null && value.getAvailableSpecialists() != null
                                && value.getAvailableSpecialists().size() > 0) {
                            mSelectSpecialistUserFragmentView.showSpecialistUsers(value.getAvailableSpecialists());
                            mSelectSpecialistUserFragmentView.changeBTNRandomSpeciliastVisibility(View.VISIBLE);
                            mSelectSpecialistUserFragmentView.changeLYNoSpecialistVisibility(View.GONE);
                        } else {
                            mSelectSpecialistUserFragmentView.changeBTNRandomSpeciliastVisibility(View.GONE);
                            mSelectSpecialistUserFragmentView.changeLYNoSpecialistVisibility(View.VISIBLE);
                        }

                        mSelectSpecialistUserFragmentView.dismissProgressBar();
                        iterator.dispose();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if ( e instanceof HttpException) {
                        APiException aPiException =
                                new APiException(((HttpException) e).response());

                        mSelectSpecialistUserFragmentView
                                .showMessage(LaFemmeApplication
                                        .getInstance()
                                        .getString(R.string.sorry_label), aPiException
                                        .getMessage(), "", false);

                    }
                    if (mSelectSpecialistUserFragmentView != null) {
                        mSelectSpecialistUserFragmentView.dismissProgressBar();
                    }
                    iterator.dispose();
                }

                @Override
                public void onComplete() {


                }
            }, new GetAppointmentAvailabilityRequestParams(mSimpleDateFormat.format(fromDate),
                    mSimpleDateFormat.format(toDate), appointmentServiceRequestList,
                    mViewServicesRequest.getmLatLng().latitude,
                    mViewServicesRequest.getmLatLng().longitude));
        }

    }

    @Override
    public void selectRandomSpecialist(SpecialistUserRecyclerViewAdapter
                                               specialistUserRecyclerViewAdapter,
                                       View view) {

        if (specialistUserRecyclerViewAdapter != null &&
                specialistUserRecyclerViewAdapter.getItemCount() > 0) {
            Random random = new Random();
            int position = random.nextInt(specialistUserRecyclerViewAdapter.getItemCount()
                    - 1);

            onViewClicked(view, specialistUserRecyclerViewAdapter
                    .getSpecialistAtPosition(position));

        }


    }

    @Override
    public void onCreate(Bundle arguments) {
        if (arguments != null) {
            mViewServicesRequest = arguments.getParcelable(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY);
        }
    }

    @Override
    public void onCreateView(View view) {
        if (mSelectSpecialistUserFragmentView != null) {
            mSelectSpecialistUserFragmentView.initViewComponents(view);
            mSelectSpecialistUserFragmentView.initComponents();
        }
    }

    @Override
    public void onAttach(IFragmentCallbacks fragmentCallback) {
        mFragmentCallbacks = fragmentCallback;
    }


    @Override
    public void onDestroy() {
        mFragmentCallbacks = null;
        mSelectSpecialistUserFragmentView = null;
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
        if (mFragmentCallbacks != null && mViewServicesRequest != null) {
            switch (view.getId()) {
                case R.id.btn_random_specialist:
                case R.id.rl_specialist_container: {
                    mViewServicesRequest.setmSpecialistUserSelected((SpecialistUser) parcelable);
                    mFragmentCallbacks.onViewClicked(view, mViewServicesRequest);
                    break;
                }
            }
        }
    }
}
