package co.com.lafemmeapp.providers.push_notifications

import android.content.Context
import co.com.lafemmeapp.providers.R
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import io.reactivex.Observable

/**
 * Created by oscargallon on 5/28/17.
 */

class PushNotificacionRegisterProvider : IPushNotificacionRegisterProvider {

    override fun getDeviceToken(context: Context): Observable<String> {
        return Observable.create { emitter ->
            val instanceID = InstanceID.getInstance(context)
            emitter.onNext(instanceID.getToken(context.getString(R.string.gcm_project_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null))
            emitter.onComplete()
        }
    }
}
