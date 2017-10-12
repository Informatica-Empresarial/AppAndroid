package co.com.lafemmeapp.lafemmeapp.presentation.history

import android.app.Activity
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.lafemmeapp.events.OnAppointmentRatedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.google.android.gms.common.api.GoogleApiClient
import io.reactivex.observers.DisposableObserver

/**
 * Created by oscargallon on 5/22/17.
 */
interface IHistoryFragmentPresenter : IBaseFragmentPresenter, IFragmentCallbacks,
        SwipeRefreshLayout.OnRefreshListener {

    fun getUserAppointmentsHistory()

    fun filterAppointments(onGoing: Boolean)

    fun toggleSOS(context: Context)

    fun getGoogleApiClientAndShowGPSDialog(activity: Activity)

    fun checkSession(observer: DisposableObserver<User>)

    fun checkSOS()

    fun initHistory()


}