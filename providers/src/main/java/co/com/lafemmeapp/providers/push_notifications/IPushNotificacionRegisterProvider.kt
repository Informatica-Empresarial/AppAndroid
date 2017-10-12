package co.com.lafemmeapp.providers.push_notifications

import android.content.Context
import io.reactivex.Observable


/**
 * Created by oscargallon on 5/28/17.
 */
interface IPushNotificacionRegisterProvider {

    fun getDeviceToken(context: Context): Observable<String>
}