package co.com.lafemmeapp.lafemmeapp.presentation.history

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.params.GoogleAPIClientParams
import co.com.lafemmeapp.core.domain.params.ToggleSOSParams
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator
import co.com.lafemmeapp.core.domain.use_cases.customer.GetGoogleAPIClientUseCase
import co.com.lafemmeapp.core.domain.use_cases.session.GetSessionUseCase
import co.com.lafemmeapp.core.domain.use_cases.system.ShowEnableGPSDialogUseCase
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.lafemmeapp.events.OnAppointmentRatedEvent
import co.com.lafemmeapp.lafemmeapp.events.OnAppointmentStartedFinished
import co.com.lafemmeapp.lafemmeapp.events.TurnOffSOSEvent
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.UtilModuleConstants
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.squareup.otto.Subscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by oscargallon on 5/22/17.
 */
class HistoryFragmentPresenter(val mHistoryFragmentView: IHistoryFragmentView) : IHistoryFragmentPresenter {


    var mFragmentCallback: IFragmentCallbacks? = null

    var mAppointments: ArrayList<Appointment>? = null

    override fun onCreateView(view: View) {
        mHistoryFragmentView.initViewComponents(view)
        mHistoryFragmentView.initComponents()

    }

    override fun initHistory() {
        checkSession(object : DisposableObserver<User>() {
            override fun onComplete() {


            }

            override fun onNext(t: User?) {
                t.let {
                    mHistoryFragmentView.onSessionChecked(t is SpecialistUser)
                    mHistoryFragmentView.changeLYSOSAvailabilityVisibility(t is SpecialistUser)
                }
                mHistoryFragmentView.hideProgressDialog()

            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                mHistoryFragmentView.hideProgressDialog()
            }
        })
    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks?) {
        mFragmentCallback = fragmentCallback
        LaFemmeApplication.getInstance()
                .getmBus()
                .register(this)
    }

    override fun onCreate(arguments: Bundle?) {
        if (arguments !== null) {

        }
    }

    override fun checkSOS() {
        checkSession(object : DisposableObserver<User>() {
            override fun onComplete() {

            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
            }

            override fun onNext(t: User?) {
                t.let {
                    mHistoryFragmentView.checkOrUCheckSW((t as SpecialistUser).isSOS)
                }
                mHistoryFragmentView.hideProgressDialog()
            }

        })
    }


    override fun checkSession(observer: DisposableObserver<User>) {
        mHistoryFragmentView.showProgressDialog()
        val iterator: IUseCaseIterator<User, Void?>
                = LaFemmeApplication.getInstance()
                .getmUseCaseFactory()
                .getSessionUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread())
        iterator.execute(observer, null)
    }


    override fun getUserAppointmentsHistory() {


        val iterator: IUseCaseIterator<List<Appointment>, Void> =
                LaFemmeApplication.getInstance()
                        .getmUseCaseFactory()
                        .getAppointmentsUseCase(Schedulers.io(),
                                AndroidSchedulers.mainThread())

        mHistoryFragmentView.showProgressBar()
        iterator.execute(object : DisposableObserver<List<Appointment>>() {
            override fun onNext(value: List<Appointment>?) {
                if (value !== null) {
                    mAppointments = value as ArrayList<Appointment>
                    filterAppointments(true)
                }
                mHistoryFragmentView.hideProgressBar()

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mHistoryFragmentView.hideProgressBar()
            }

            override fun onComplete() {

            }

        }, null)
    }

    override fun filterAppointments(onGoing: Boolean) {
        val status: ArrayList<String>
                = if (onGoing) arrayListOf(AppModuleConstants.STATUS_APPOINTMENT,
                AppModuleConstants.STATUS_IN_PROGRESS) else arrayListOf(AppModuleConstants.STATUS_FINISHED)
        mAppointments?.let {
            mHistoryFragmentView.showAppointments(mAppointments!!
                    .filter { appointment ->
                        status.contains(appointment.status)
                    }.sortedWith(compareBy({ it.startDateTime })), onGoing)

        }
    }

    override fun toggleSOS(context: Context) {

        mHistoryFragmentView.showProgressDialog()

        val iterator: IUseCaseIterator<SpecialistUser,
                ToggleSOSParams> =
                LaFemmeApplication.getInstance()
                        .getmUseCaseFactory()
                        .toggleSOSUseCase(Schedulers.computation(),
                                AndroidSchedulers.mainThread())

        iterator.execute(object : DisposableObserver<SpecialistUser>() {
            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                mHistoryFragmentView.hideProgressDialog()
            }

            override fun onNext(t: SpecialistUser?) {
                t.let {
                    mHistoryFragmentView.checkOrUCheckSW(t!!.isSOS)
                }
                mHistoryFragmentView.hideProgressDialog()
            }

            override fun onComplete() {
            }
        }, ToggleSOSParams(LaFemmeApplication.getInstance()
                .getmUseCaseFactory().getSessionUseCase(Schedulers.computation(),
                Schedulers.computation()) as GetSessionUseCase, context))
    }


    override fun getGoogleApiClientAndShowGPSDialog(activity: Activity) {
        val iterator = GetGoogleAPIClientUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread())
        iterator.execute(object : DisposableObserver<GoogleApiClient>() {
            override fun onNext(value: GoogleApiClient) {

                showGPSDialog(activity, value)

            }

            override fun onError(e: Throwable) {
                //TODO Show error
                e.printStackTrace()
            }

            override fun onComplete() {

            }
        }, GoogleAPIClientParams(LocationServices.API, activity))
    }


    fun showGPSDialog(activity: Activity, googleApiClient: GoogleApiClient) {
        val iterator = ShowEnableGPSDialogUseCase(Schedulers.io(), AndroidSchedulers.mainThread())
        iterator.execute(object : DisposableObserver<Status>() {
            override fun onNext(status: Status) {
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        mHistoryFragmentView.onGPSEnabled()
                    }
                    CommonStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            status.startResolutionForResult(activity,
                                    AppModuleConstants.GPS_DIALOG_REQUEST_CODE)
                        } catch (e: IntentSender.SendIntentException) {
                            e.printStackTrace()
                            //TODO Show error
                        }

                    }
                    else -> {
                        //TODO Show error
                    }
                }
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        }, googleApiClient)


    }

    @Subscribe
    fun onAppointmentStartedFinished(onAppointmentStartedFinished: OnAppointmentStartedFinished) {
        mHistoryFragmentView.updateAppointment(onAppointmentStartedFinished.mAppointment)
    }

    @Subscribe
    fun onAppointmentRated(onAppointmentRatedEvent: OnAppointmentRatedEvent) {
        mHistoryFragmentView.updateAppointment(onAppointmentRatedEvent.mAppointment)
    }

    @Subscribe
    fun onSOSModeChange(turnOffSOSEvent: TurnOffSOSEvent) {
        mHistoryFragmentView.checkOrUCheckSW(false)
    }

    override fun onDestroy() {
        mFragmentCallback = null
        mAppointments = null
        LaFemmeApplication
                .getInstance()
                .getmBus()
                .unregister(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AppModuleConstants.GPS_DIALOG_REQUEST_CODE && resultCode == RESULT_OK) {
            mHistoryFragmentView.onGPSEnabled()
        }

    }

    override fun onRefresh() {
        mHistoryFragmentView.showProgressBar()
        getUserAppointmentsHistory()
        mAppointments?.clear()
    }

    override fun subscribeTextViewToTextWatcherEvent(textView: TextView, validatorMapper: ITextViewValidatorMapper) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChangeMappedEvent(textChangedMappedEvent: TextChangedMappedEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == AppModuleConstants.LOCATION_PERMISSION_RESULT) {
            val userHasDeniedPermissions =
                    permissions.indices.any { grantResults[it] != PackageManager.PERMISSION_GRANTED }
            if (!userHasDeniedPermissions) {
                mHistoryFragmentView.onLocationPermissionsGranted()
            } else {
                mHistoryFragmentView.showMessage("Error", "Permissions denied",
                        UtilModuleConstants.SHOW_ERROR_MESSAGE_ACTION, false)
            }
        }
    }

    override fun onViewClicked(view: View?, parcelable: Parcelable?) {
        if (mFragmentCallback !== null) {
            mFragmentCallback!!.onViewClicked(view, parcelable)
        }
    }

}