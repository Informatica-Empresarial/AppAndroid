package co.com.lafemmeapp.lafemmeapp.push_notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import co.com.lafemmeapp.core.domain.entities.NotificationData
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAppointmentMapper
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointment
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.presentation.dashboard.DashboardActivity
import com.google.android.gms.gcm.GcmListenerService
import com.google.gson.Gson


/**
 * Created by oscargallon on 5/29/17.
 */
class PushNotificationsListenerService : GcmListenerService() {

    override fun onMessageReceived(p0: String?, p1: Bundle?) {

        if (p1 !== null && p1.containsKey(AppModuleConstants.NOTIFICATION_DATA_KEY)) {

            val data =
                    Gson().fromJson(p1[AppModuleConstants.NOTIFICATION_DATA_KEY].toString(),
                            NotificationData::class.java)
            if (data !== null) {
                if (data.mHasAppointment && data.mAPIAppointment !== null) {
                    data.mAppointment = APIAppointmentAppointmentMapper.instace
                            .apply(data.mAPIAppointment!!)

                }
                showNotification(data)
            }
        }

    }

    fun getPendingIntent(data: NotificationData): PendingIntent {
        val intent = Intent(this, DashboardActivity::class.java)
        val bundle = Bundle()
        bundle.putString(AppModuleConstants.NOTIFICATION_ACTION_KEY,
                data.mAction)
        if (data.mHasAppointment && data.mAppointment!==null) {
            bundle.putParcelable(AppModuleConstants.APPOINTMENT_KEY,
                    data.mAppointment)
        }
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        return PendingIntent.getActivity(this,
                AppModuleConstants.SOS_ERROR_NOTIFICATION_REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun showNotification(data: NotificationData) {


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, NotificationsUtil.getNotification(applicationContext,
                data.mTitle, data.mMessage, getPendingIntent(data), true))
    }
}