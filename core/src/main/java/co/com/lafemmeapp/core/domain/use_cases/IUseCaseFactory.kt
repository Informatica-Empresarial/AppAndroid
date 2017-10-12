package co.com.lafemmeapp.core.domain.use_cases

import android.content.Context
import android.location.Location
import co.com.lafemmeapp.core.domain.entities.*
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.params.GetAppointmentAvailabilityRequestParams
import co.com.lafemmeapp.core.domain.params.StartFinishAppointmentParams
import co.com.lafemmeapp.core.domain.params.ToggleSOSParams
import co.com.lafemmeapp.dataprovider.network.entities.APICity
import co.com.lafemmeapp.dataprovider.network.entities.GetServiceRequest
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.params.EditProfileParams
import co.com.lafemmeapp.dataprovider.params.LoginParams
import co.com.lafemmeapp.dataprovider.params.RegisterParams
import co.com.lafemmeapp.dataprovider.params.ScheduleAppointmentParams
import io.reactivex.Scheduler

/**
 * Created by oscargallon on 6/3/17.
 */
interface IUseCaseFactory {

    fun loginUseCase(subscribeOnScheduler: Scheduler,
                     observerOnScheduler: Scheduler): IUseCaseIterator<SessionResponse,
            LoginParams>

    fun getSessionUseCase(subscribeOnScheduler: Scheduler,
                          observerOnScheduler: Scheduler): IUseCaseIterator<User, Void?>

    fun getCitiesUseCase(subscribeOnScheduler: Scheduler,
                         observerOnScheduler: Scheduler): IUseCaseIterator<List<String>, Boolean>

    fun getSelectableCitiesUseCase(subscribeOnScheduler: Scheduler,
                         observerOnScheduler: Scheduler): IUseCaseIterator<List<City>, Void?>

    fun getHasWatchOnBoardingUseCase(subscribeOnScheduler: Scheduler,
                                     observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Void>

    fun setHasWatchOnBoardingUseCase(subscribeOnScheduler: Scheduler,
                                     observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Boolean>

    fun getServicesUseCase(subscribeOnScheduler: Scheduler,
                           observerOnScheduler: Scheduler): IUseCaseIterator<List<ViewService>, GetServiceRequest>

    fun getCloseSessionUseCase(subscribeOnScheduler: Scheduler,
                               observerOnScheduler: Scheduler): IUseCaseIterator<Boolean, Void>

    fun getAppointmentAvailabilityUseCase(subscribeOnScheduler: Scheduler,
                                          observerOnScheduler: Scheduler): IUseCaseIterator<AppointmentAvailability,
            GetAppointmentAvailabilityRequestParams>

    fun getPreScheduleAppointmentUseCase(subscribeOnScheduler: Scheduler,
                                         observerOnScheduler: Scheduler):
            IUseCaseIterator<Appointment, ViewServicesRequest>

    fun scheduleAppointmentUseCase(subscribeOnScheduler: Scheduler,
                                   observerOnScheduler: Scheduler):
            IUseCaseIterator<Appointment, ScheduleAppointmentParams>

    fun getAppointmentsUseCase(subscribeOnScheduler: Scheduler,
                               observerOnScheduler: Scheduler):
            IUseCaseIterator<List<Appointment>, Void>

    fun cancelAppointmentUseCase(subscribeOnScheduler: Scheduler,
                                 observerOnScheduler: Scheduler)
            : IUseCaseIterator<Appointment, String>

    fun getAndSendDeviceTokenUseCase(subscribeOnScheduler: Scheduler,
                                     observerOnScheduler: Scheduler)
            : IUseCaseIterator<Boolean, Context>

    fun registerUseCase(subscribeOnScheduler: Scheduler,
                        observerOnScheduler: Scheduler)
            : IUseCaseIterator<Customer, RegisterParams>

    fun isOnSOSModeUseCase(subscribeOnScheduler: Scheduler,
                           observerOnScheduler: Scheduler):
            IUseCaseIterator<String, Void>

    fun updateSpecialistLocationUseCase(subscribeOnScheduler: Scheduler,
                                        observerOnScheduler: Scheduler):
            IUseCaseIterator<SpecialistUser, Location>

    fun toggleSOSUseCase(subscribeOnScheduler: Scheduler,
                         observerOnScheduler: Scheduler): IUseCaseIterator<SpecialistUser, ToggleSOSParams>

    fun deleteSOSActivationDateUseCase(subscribeOnScheduler: Scheduler,
                                       observerOnScheduler: Scheduler):
            IUseCaseIterator<Boolean, Void?>

    fun turnOffSOSUseCase(subscribeOnScheduler: Scheduler,
                          observerOnScheduler: Scheduler):
            IUseCaseIterator<SpecialistUser, Void?>

    fun getAppointmentAvailabilitySOSUseCase(subscribeOnScheduler: Scheduler,
                                             observerOnScheduler: Scheduler): IUseCaseIterator<AppointmentAvailability,
            GetAppointmentAvailabilityRequestParams>

    fun rateAppointmentUseCase(subscribeOnScheduler: Scheduler,
                               observerOnScheduler: Scheduler): IUseCaseIterator<Appointment, RateAppointmentParams>

    fun startFinishAppointmentUseCase(subscribeOnScheduler: Scheduler,
                                      observerOnScheduler: Scheduler):
            IUseCaseIterator<Appointment, StartFinishAppointmentParams>

    fun loginWithFacebookUseCase(subscribeOnScheduler: Scheduler,
                                 observerOnScheduler: Scheduler):
            IUseCaseIterator<SessionResponse, String>

    fun requestPasswordResetUseCase(subscribeOnScheduler: Scheduler,
                                    observerOnScheduler: Scheduler):
            IUseCaseIterator<String, String>

    fun deleteDeviceUseCase(subscribeOnScheduler: Scheduler,
                            observerOnScheduler: Scheduler): IUseCaseIterator<Any, Context>


    fun updateUserUseCase(subscribeOnScheduler: Scheduler,
                          observerOnScheduler: Scheduler): IUseCaseIterator<User, EditProfileParams>
}