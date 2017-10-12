package co.com.lafemmeapp.lafemmeapp.push_notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import co.com.lafemmeapp.lafemmeapp.R

/**
 * Created by oscargallon on 6/9/17.
 */
class NotificationsUtil {

    companion object {
        fun getNotification(context: Context, title: String, message: String,
                            pendingIntent: PendingIntent, cancelable: Boolean): Notification {


            return NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_logo_grey_scale)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_logo_grey_scale))
                    .setContentTitle(title)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setAutoCancel(cancelable)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .build()


        }
    }
}