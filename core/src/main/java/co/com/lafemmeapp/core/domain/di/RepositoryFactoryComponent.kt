package co.com.lafemmeapp.core.domain.di

import co.com.lafemmeapp.core.domain.di.scopes.RepositoryFactoryScope
import co.com.lafemmeapp.core.domain.use_cases.appointments.*
import co.com.lafemmeapp.core.domain.use_cases.customer.*
import co.com.lafemmeapp.core.domain.use_cases.push_notifications.DeleteDeviceUseCase
import co.com.lafemmeapp.core.domain.use_cases.push_notifications.GetAndSendDeviceTokenUseCase
import co.com.lafemmeapp.core.domain.use_cases.services.GetServicesUseCase
import co.com.lafemmeapp.core.domain.use_cases.session.*
import co.com.lafemmeapp.core.domain.use_cases.specialist.*
import co.com.lafemmeapp.core.domain.use_cases.system.*
import dagger.Component

/**
 * Created by oscargallon on 6/2/17.
 */
@RepositoryFactoryScope
@Component(modules = arrayOf(RepositoryFactoryModule::class))
interface RepositoryFactoryComponent {

    fun inject(loginUseCase: LoginUseCase)

    fun inject(cancelAppointmentUseCase: CancelAppointmentUseCase)

    fun inject(getAppointmentsUseCase: GetAppointmentsUseCase)

    fun inject(getAppointmentAvailabilityUseCase: GetAppointmentAvailabilityUseCase)

    fun inject(getPreScheduleAppointmentUseCase: GetPreScheduleAppointmentUseCase)

    fun inject(scheduleAppointmentUseCase: ScheduleAppointmentUseCase)

    fun inject(closeSessionUseCase: CloseSessionUseCase)

    fun inject(getCustomerUseCase: GetCustomerUseCase)

    fun inject(getGoogleAPIClientUseCase: GetGoogleAPIClientUseCase)

    fun inject(getInfoFromFacebookUserUseCase: GetInfoFromFacebookUserUseCase)

    fun inject(getSessionUseCase: GetSessionUseCase)

    fun inject(logOutWithFacebookUseCase: LogOutWithFacebookUseCase)

    fun inject(registerUseCase: RegisterUseCase)

    fun inject(getAndSendDeviceTokenUseCase: GetAndSendDeviceTokenUseCase)

    fun inject(getServicesUseCase: GetServicesUseCase)

    fun inject(getSelectableCitiesUseCase: GetSelectableCitiesUseCase)

    fun inject(checkInternetConnectionUseCase: CheckInternetConnectionUseCase)

    fun inject(getAddressFromLatLngUseCase: GetAddressFromLatLngUseCase)

    fun inject(getCitiesFromDBUseCase: GetCitiesFromDBUseCase)

    fun inject(getCitiesUseCase: GetCitiesUseCase)

    fun inject(getHasWatchOnboarding: GetHasWatchOnboarding)

    fun inject(setHasWatchOnboardingUseCase: SetHasWatchOnboardingUseCase)

    fun inject(isOnSOSModeUseCase: IsOnSOSModeUseCase)

    fun inject(updateSpecialistLocationUseCase: UpdateSpecialistLocationUseCase)

    fun inject(toggleSOSUseCase: ToggleSOSUseCase)

    fun inject(deleteSOSActivationDateUseCase: DeleteSOSActivationDateUseCase)

    fun inject(turnOffSOSUseCase: TurnOffSOSUseCase)

    fun inject(getAppointmentAvailabilitySOSUseCase: GetAppointmentAvailabilitySOSUseCase)

    fun inject(rateAppointmentUseCase: RateAppointmentUseCase)

    fun inject(startFinishAppointmentUseCase: StartFinishAppointmentUseCase)

    fun inject(loginWithFacebookUseCase: LoginWithFacebookUseCase)

    fun inject(requestPasswordResetUseCase: RequestPasswordResetUseCase)

    fun inject(deleteDeviceUseCase: DeleteDeviceUseCase)

    fun inject(updateUserUseCase: UpdateUserUseCase)

}