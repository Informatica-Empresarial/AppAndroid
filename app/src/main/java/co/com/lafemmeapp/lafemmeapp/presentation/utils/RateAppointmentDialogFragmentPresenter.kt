package co.com.lafemmeapp.lafemmeapp.presentation.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.entities.RateAppointmentParams
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
import com.jakewharton.rxbinding2.widget.RxRatingBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

/**
 * Created by oscargallon on 6/10/17.
 */
class RateAppointmentDialogFragmentPresenter(val mRateAppointmentDialogFragmentView: IRateAppointmentDialogFragmentView,
                                             context: Context)
    : IRateAppointmentDialogFragmentPresenter {


    var mFragmentCallbacks: IFragmentCallbacks? = null

    var mAppointment: Appointment? = null

    var mContextWeakReference = WeakReference(context)

    override fun onCreateView(view: View) {
        mRateAppointmentDialogFragmentView.initViewComponents(view)
        mRateAppointmentDialogFragmentView.initComponents()
        mAppointment?.specialist?.let {
            if (!TextUtils.isEmpty(mAppointment!!.specialist!!.avatar)) {
                mRateAppointmentDialogFragmentView.showSpecialistAvatar(mAppointment!!.specialist!!.avatar)
            }
        }
    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks?) {
        mFragmentCallbacks = fragmentCallback

    }

    override fun onCreate(arguments: Bundle) {
        mAppointment = arguments.getParcelable(AppModuleConstants.VIEW_HISTORY_DETAIL)
    }


    fun changeStatus(numStarts: Int) {

        mContextWeakReference.get().let {
            val status: String =
                    when (numStarts) {
                        1 -> mContextWeakReference.get()!!.getString(R.string.one_start_description)
                        2 -> mContextWeakReference.get()!!.getString(R.string.two_starts_description)
                        3 -> mContextWeakReference.get()!!.getString(R.string.three_starts_description)
                        4 -> mContextWeakReference.get()!!.getString(R.string.four_starts_description)
                        5 -> mContextWeakReference.get()!!.getString(R.string.five_starts_description)
                        else -> ""
                    }

            if (numStarts < 5) {
                mRateAppointmentDialogFragmentView.showHideOptionsButtons(View.VISIBLE)
            } else {
                mRateAppointmentDialogFragmentView.showHideOptionsButtons(View.GONE)
            }
            mRateAppointmentDialogFragmentView.changeStatus(status)

        }

    }

    override fun subscribeToRatingBarChanges(ratingBar: RatingBar) {


        RxRatingBar.ratingChangeEvents(ratingBar)
                .skipInitialValue()
                .distinctUntilChanged()
                .delay(100, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<RatingBarChangeEvent>() {
                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: RatingBarChangeEvent?) {
                        t.let {
                            changeStatus(t!!.rating().toInt())
                        }

                    }
                })

    }

    override fun sendRate(options: String?, rate: Float) {
        mRateAppointmentDialogFragmentView.showProgressDialog()
        LaFemmeApplication.getInstance()
                .getmUseCaseFactory()
                .rateAppointmentUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread())
                .execute(object : DisposableObserver<Appointment>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: Appointment) {
                        mRateAppointmentDialogFragmentView.onAppointmentRated(t)
                        mRateAppointmentDialogFragmentView.hideProgressDialog()
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                        mRateAppointmentDialogFragmentView.hideProgressDialog()
                    }
                }, RateAppointmentParams(rate.toInt(),
                        options, mAppointment!!.uuid!!))

    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subscribeTextViewToTextWatcherEvent(textView: TextView, validatorMapper: ITextViewValidatorMapper) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChangeMappedEvent(textChangedMappedEvent: TextChangedMappedEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}