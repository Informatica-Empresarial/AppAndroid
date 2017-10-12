package co.com.lafemmeapp.lafemmeapp.presentation.sos

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.util.Log
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.params.LocationRequestParams
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator
import co.com.lafemmeapp.core.domain.use_cases.system.GetUserLocationUseCase

import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.lafemmeapp.events.TurnOffSOSEvent
import co.com.lafemmeapp.lafemmeapp.presentation.dashboard.DashboardActivity
import co.com.lafemmeapp.lafemmeapp.push_notifications.NotificationsUtil
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by oscargallon on 6/5/17.
 *
 */
class SOSIntentService : IntentService(AppModuleConstants.SOS_INTENT_SERVICE_NAME) {

    override fun onHandleIntent(intent: Intent) {
        val maxDuration = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES)
        val iterator: IUseCaseIterator<String, Void> =
                LaFemmeApplication.getInstance()
                        .getmUseCaseFactory()
                        .isOnSOSModeUseCase(Schedulers.computation(),
                                Schedulers.computation())

        iterator.execute(object : DisposableObserver<String>() {
            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                LaFemmeApplication.getInstance()
                        .cancelSOSService()

            }

            override fun onComplete() {
            }

            override fun onNext(t: String) {

                val currentDate = Calendar.getInstance()
                val activationDate = Calendar.getInstance()
                activationDate.time = Date(t.toLong())
                if (currentDate.timeInMillis - activationDate.timeInMillis >= maxDuration) {
                    checkIfHasToToggleSOS(true)
                }
                updateLocation()


            }

        }, null)
    }


    fun updateLocation() {
        val iterator: IUseCaseIterator<Location, LocationRequestParams>
                = GetUserLocationUseCase(Schedulers.computation(),
                Schedulers.computation())

        iterator.execute(object : DisposableObserver<Location>() {
            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                checkIfHasToToggleSOS(false)
            }

            override fun onNext(t: Location?) {
                if (t !== null) {
                    val iterator: IUseCaseIterator<SpecialistUser, Location> =
                            LaFemmeApplication.getInstance()
                                    .getmUseCaseFactory()
                                    .updateSpecialistLocationUseCase(Schedulers.io(),
                                            Schedulers.computation())

                    iterator.execute(object : DisposableObserver<SpecialistUser>() {
                        override fun onComplete() {

                        }

                        override fun onNext(t: SpecialistUser?) {
                            t.let {
                                Log.i("UPDATED LOCATION", t.toString())
                            }

                        }

                        override fun onError(e: Throwable?) {
                            e?.printStackTrace()
                            checkIfHasToToggleSOS(false)
                        }

                    }, t)
                }
            }

            override fun onComplete() {}

        }, LocationRequestParams(applicationContext, LocationManager.NETWORK_PROVIDER))
    }

    fun checkIfHasToToggleSOS(timeOut: Boolean) {
        LaFemmeApplication
                .getInstance()
                .getmUseCaseFactory()
                .getSessionUseCase(Schedulers.computation(),
                        Schedulers.computation())
                .execute(object : DisposableObserver<User>() {
                    override fun onNext(t: User) {
                        if (t is SpecialistUser) {
                            if (t.isSOS) {
                                turnOffSOS(timeOut)
                            } else {
                                LaFemmeApplication
                                        .getInstance()
                                        .cancelSOSService()
                            }
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                }, null)
    }

    fun turnOffSOS(timeOut: Boolean) {
        LaFemmeApplication.getInstance()
                .getmUseCaseFactory()
                .turnOffSOSUseCase(Schedulers.computation(),
                        Schedulers.computation())
                .execute(object : DisposableObserver<SpecialistUser>() {
                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                    override fun onNext(t: SpecialistUser) {
                        if (!t.isSOS) {
                            if (timeOut) {
                                showTimeOutNotification()
                                LaFemmeApplication.getInstance()
                                        .cancelSOSService()
                            } else {
                                showErrorOnSOSNotification()
                                LaFemmeApplication.getInstance()
                                        .cancelSOSService()
                            }
                        }


                    }

                }, null)
    }

    fun getSOSNotificationPendingIntent(): PendingIntent {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra(AppModuleConstants.SOS_EXTRA_KEY, false)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        return PendingIntent.getActivity(this,
                AppModuleConstants.SOS_ERROR_NOTIFICATION_REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun showTimeOutNotification() {
        LaFemmeApplication
                .getInstance()
                .getmBus()
                .post(TurnOffSOSEvent(true))
        val pendingIntent = getSOSNotificationPendingIntent()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(AppModuleConstants.SOS_ERROR_NOTIFICATION_ID,
                NotificationsUtil.Companion
                        .getNotification(applicationContext, getString(R.string.sos_notification_title),
                                getString(R.string.sos_time_out_message), pendingIntent, true))
    }

    fun showErrorOnSOSNotification() {

        val pendingIntent = getSOSNotificationPendingIntent()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(AppModuleConstants.SOS_ERROR_NOTIFICATION_ID,
                NotificationsUtil.Companion
                        .getNotification(applicationContext, getString(R.string.sos_notification_title),
                                getString(R.string.sos_error_notification_message), pendingIntent, true))
    }

}