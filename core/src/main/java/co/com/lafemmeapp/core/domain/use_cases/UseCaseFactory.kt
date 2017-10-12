package co.com.lafemmeapp.core.domain.use_cases

import android.content.Context
import android.location.Location
import co.com.lafemmeapp.core.domain.di.DaggerRepositoryFactoryComponent


import co.com.lafemmeapp.core.domain.di.RepositoryFactoryComponent
import co.com.lafemmeapp.core.domain.di.RepositoryFactoryModule
import co.com.lafemmeapp.core.domain.entities.*
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.params.GetAppointmentAvailabilityRequestParams
import co.com.lafemmeapp.core.domain.params.StartFinishAppointmentParams
import co.com.lafemmeapp.core.domain.params.ToggleSOSParams
import co.com.lafemmeapp.core.domain.use_cases.appointments.*
import co.com.lafemmeapp.core.domain.use_cases.customer.LoginUseCase
import co.com.lafemmeapp.core.domain.use_cases.customer.RegisterUseCase
import co.com.lafemmeapp.core.domain.use_cases.push_notifications.DeleteDeviceUseCase
import co.com.lafemmeapp.core.domain.use_cases.push_notifications.GetAndSendDeviceTokenUseCase
import co.com.lafemmeapp.core.domain.use_cases.services.GetServicesUseCase
import co.com.lafemmeapp.core.domain.use_cases.session.*
import co.com.lafemmeapp.core.domain.use_cases.specialist.*
import co.com.lafemmeapp.core.domain.use_cases.system.*
import co.com.lafemmeapp.dataprovider.network.entities.APICity
import co.com.lafemmeapp.dataprovider.network.entities.GetServiceRequest
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.params.*
import io.reactivex.Scheduler

/**
 * Created by oscargallon on 6/2/17.
 */
class UseCaseFactory(mContext: Context) : IUseCaseFactory {


    private val mRepositoryFactoryComponent: RepositoryFactoryComponent =
            DaggerRepositoryFactoryComponent
                    .builder()
                    .repositoryFactoryModule(RepositoryFactoryModule(mContext))
                    .build()

    override fun getServicesUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler):
            IUseCaseIterator<List<ViewService>, GetServiceRequest> {

        val getServicesUseCase = GetServicesUseCase(subscribeOnScheduler,
                observerOnScheduler)

        mRepositoryFactoryComponent.inject(getServicesUseCase)
        return getServicesUseCase
    }

    override fun loginUseCase(subscribeOnScheduler: Scheduler,
                              observerOnScheduler: Scheduler):
            IUseCaseIterator<SessionResponse, LoginParams> {
        val loginUseCase = LoginUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(loginUseCase)
        return loginUseCase
    }

    override fun getSessionUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler):
            IUseCaseIterator<User, Void?> {
        val getSessionUseCase = GetSessionUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(getSessionUseCase)
        return getSessionUseCase

    }

    override fun getCitiesUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<List<String>, Boolean> {
        val getCitiesUseCase = GetCitiesUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(getCitiesUseCase)
        return getCitiesUseCase
    }

    override fun getHasWatchOnBoardingUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Void> {
        val getHasWatchOnBoardingUseCase =
                GetHasWatchOnboarding(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(getHasWatchOnBoardingUseCase)
        return getHasWatchOnBoardingUseCase
    }

    override fun setHasWatchOnBoardingUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Boolean> {
        val setHasWatchOnBoardingUseCase =
                SetHasWatchOnboardingUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(setHasWatchOnBoardingUseCase)
        return setHasWatchOnBoardingUseCase
    }

    override fun getCloseSessionUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Void> {
        val closeSessionUseCase =
                CloseSessionUseCase(subscribeOnScheduler,
                        observerOnScheduler)
        mRepositoryFactoryComponent.inject(closeSessionUseCase)
        return closeSessionUseCase
    }

    override fun getAppointmentAvailabilityUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<AppointmentAvailability, GetAppointmentAvailabilityRequestParams> {
        val getAppointmentAvailabilityUseCase =
                GetAppointmentAvailabilityUseCase(subscribeOnScheduler,
                        observerOnScheduler)
        mRepositoryFactoryComponent.inject(getAppointmentAvailabilityUseCase)
        return getAppointmentAvailabilityUseCase
    }

    override fun getPreScheduleAppointmentUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Appointment, ViewServicesRequest> {
        val getPreScheduleAppointmentUseCase =
                GetPreScheduleAppointmentUseCase(subscribeOnScheduler,
                        observerOnScheduler)
        mRepositoryFactoryComponent.inject(getPreScheduleAppointmentUseCase)
        return getPreScheduleAppointmentUseCase
    }

    override fun scheduleAppointmentUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Appointment, ScheduleAppointmentParams> {
        val scheduleAppointmentUseCase =
                ScheduleAppointmentUseCase(subscribeOnScheduler,
                        observerOnScheduler)
        mRepositoryFactoryComponent.inject(scheduleAppointmentUseCase)
        return scheduleAppointmentUseCase
    }

    override fun getAppointmentsUseCase(subscribeOnScheduler: Scheduler,
                                        observerOnScheduler: Scheduler):
            IUseCaseIterator<List<Appointment>, Void> {
        val getAppointmentsUseCase =
                GetAppointmentsUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(getAppointmentsUseCase)
        return getAppointmentsUseCase
    }

    override fun cancelAppointmentUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler)
            : IUseCaseIterator<Appointment, String> {
        val cancelAppointmentUseCase =
                CancelAppointmentUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(cancelAppointmentUseCase)
        return cancelAppointmentUseCase
    }

    override fun getAndSendDeviceTokenUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Context> {
        val getAndSendDeviceTokenUseCase =
                GetAndSendDeviceTokenUseCase(subscribeOnScheduler,
                        observerOnScheduler)

        mRepositoryFactoryComponent.inject(getAndSendDeviceTokenUseCase)
        return getAndSendDeviceTokenUseCase
    }

    override fun registerUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Customer, RegisterParams> {
        val registerUseCase =
                RegisterUseCase(subscribeOnScheduler,
                        observerOnScheduler)
        mRepositoryFactoryComponent.inject(registerUseCase)
        return registerUseCase
    }

    override fun isOnSOSModeUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<String, Void> {
        val isOnSOSModeUseCase = IsOnSOSModeUseCase(subscribeOnScheduler,
                observerOnScheduler)
        mRepositoryFactoryComponent.inject(isOnSOSModeUseCase)
        return isOnSOSModeUseCase
    }

    override fun updateSpecialistLocationUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler):
            IUseCaseIterator<SpecialistUser, Location> {
        val updateSpecialistLocationUseCase =
                UpdateSpecialistLocationUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(updateSpecialistLocationUseCase)
        return updateSpecialistLocationUseCase
    }

    override fun toggleSOSUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler):
            IUseCaseIterator<SpecialistUser, ToggleSOSParams> {
        val toggleSOSUseCase = ToggleSOSUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(toggleSOSUseCase)
        return toggleSOSUseCase
    }

    override fun deleteSOSActivationDateUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Void?> {
        val deleteSOSActivationDateUseCase =
                DeleteSOSActivationDateUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(deleteSOSActivationDateUseCase)
        return deleteSOSActivationDateUseCase
    }

    override fun turnOffSOSUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<SpecialistUser, Void?> {
        val turnOffSOSUseCase =
                TurnOffSOSUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(turnOffSOSUseCase)
        return turnOffSOSUseCase
    }

    override fun getAppointmentAvailabilitySOSUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<AppointmentAvailability, GetAppointmentAvailabilityRequestParams> {
        val getAppointmentAvailabilitySOSUseCase =
                GetAppointmentAvailabilitySOSUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(getAppointmentAvailabilitySOSUseCase)
        return getAppointmentAvailabilitySOSUseCase
    }

    override fun rateAppointmentUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Appointment, RateAppointmentParams> {
        val rateAppointmentUseCase =
                RateAppointmentUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(rateAppointmentUseCase)
        return rateAppointmentUseCase
    }


    override fun startFinishAppointmentUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<Appointment, StartFinishAppointmentParams> {
        val startFinishAppointmentUseCase = StartFinishAppointmentUseCase(subscribeOnScheduler,
                observerOnScheduler)
        mRepositoryFactoryComponent.inject(startFinishAppointmentUseCase)
        return startFinishAppointmentUseCase
    }

    override fun loginWithFacebookUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<SessionResponse, String> {
        val loginWithFacebookUseCase = LoginWithFacebookUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(loginWithFacebookUseCase)
        return loginWithFacebookUseCase
    }

    override fun requestPasswordResetUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<String, String> {
        val requestPasswordResetUseCase = RequestPasswordResetUseCase(subscribeOnScheduler,
                observerOnScheduler)
        mRepositoryFactoryComponent.inject(requestPasswordResetUseCase)
        return requestPasswordResetUseCase
    }

    override fun deleteDeviceUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler)
            : IUseCaseIterator<Any, Context> {
        val deleteDeviceUseCase = DeleteDeviceUseCase(subscribeOnScheduler, observerOnScheduler)
        mRepositoryFactoryComponent.inject(deleteDeviceUseCase)
        return deleteDeviceUseCase

    }

    override fun updateUserUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler): IUseCaseIterator<User, EditProfileParams> {
        val updateUserUseCase = UpdateUserUseCase(subscribeOnScheduler,
                observerOnScheduler)
        mRepositoryFactoryComponent.inject(updateUserUseCase)
        return updateUserUseCase
    }

    override fun getSelectableCitiesUseCase(subscribeOnScheduler: Scheduler, observerOnScheduler: Scheduler):
            IUseCaseIterator<List<City>, Void?> {
        val getSelectableCitiesUseCase = GetSelectableCitiesUseCase(subscribeOnScheduler,
                observerOnScheduler)
        mRepositoryFactoryComponent.inject(getSelectableCitiesUseCase)
        return getSelectableCitiesUseCase

    }
}