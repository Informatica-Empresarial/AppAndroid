package co.com.lafemmeapp.lafemmeapp.presentation.history_detail

import android.app.Activity
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks

/**
 * Created by Stephys on 24/05/17.
 */
interface IHistoryDetailFragmentPresenter : IBaseFragmentPresenter, IFragmentCallbacks {

    var mHistoryDetailFragmentView: IHistoryDetailFragmentView

    fun cancelAppointment()


    fun showAddress(activity: Activity)

    fun startFinishAppointment(start:Boolean)

}