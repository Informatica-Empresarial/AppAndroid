package co.com.lafemmeapp.lafemmeapp.push_notifications

import android.content.Context
import android.util.Log
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator
import co.com.lafemmeapp.core.domain.use_cases.push_notifications.GetAndSendDeviceTokenUseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.local.DBDataSource
import com.google.android.gms.iid.InstanceIDListenerService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by oscargallon on 5/29/17.
 */
class TokenListenerService : InstanceIDListenerService() {

    override fun onTokenRefresh() {
        if (DBDataSource.getInstance().exist(Constants
                .DB_HAS_WATCH_ONBOARDING)) {
            DBDataSource.getInstance()
                    .remove(Constants.DB_HAS_WATCH_ONBOARDING)
        }
        val iterator: IUseCaseIterator<Boolean, Context>
                = GetAndSendDeviceTokenUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread())
        iterator.execute(object : DisposableObserver<Boolean>() {
            override fun onNext(value: Boolean?) {
                Log.i("GCM", "TOKEN UPDATED AND SENT")


            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                Log.i("GCM", "ERROR")

            }

            override fun onComplete() {


            }
        }, this)
    }
}