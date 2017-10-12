package co.com.lafemmeapp.lafemmeapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.system.CheckInternetConnectionUseCase;
import co.com.lafemmeapp.lafemmeapp.IConnectivityChangesCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oscargallon on 5/13/17.
 */

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    private IConnectivityChangesCallback mConnectivityChangesCallback;

    public ConnectivityChangeReceiver(IConnectivityChangesCallback mConnectivityChangesCallback) {
        this.mConnectivityChangesCallback = mConnectivityChangesCallback;
    }

    /***
     * HEADER ONLyy
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo =
                    connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                IUseCaseIterator iterator =
                        new CheckInternetConnectionUseCase(Schedulers.io(),
                                AndroidSchedulers.mainThread());
                iterator.execute(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean value) {
                        answers(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        answers(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }, null);

            } else if (mConnectivityChangesCallback != null) {
                answers(false);
            }
        }
    }

    private void answers(boolean hasInternet) {
        if (mConnectivityChangesCallback != null) {
            mConnectivityChangesCallback.onConnectivityChange(hasInternet);
        }
    }
}
