package co.com.lafemmeapp.lafemmeapp.presentation.history_detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.ViewServiceBuilder
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.params.StartFinishAppointmentParams
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.lafemmeapp.events.OnAppointmentRatedEvent
import co.com.lafemmeapp.lafemmeapp.events.OnAppointmentStartedFinished
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.squareup.otto.Subscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Stephys on 24/05/17.
 */
class HistoryDetailFragmentPresenter
(override var mHistoryDetailFragmentView: IHistoryDetailFragmentView) : IHistoryDetailFragmentPresenter {


    private var mAppointment: Appointment? = null
    private var mFragmentCallback: IFragmentCallbacks? = null
    private val mSimpleDateFormat = SimpleDateFormat(AppModuleConstants.DATE_TIME_FORMAT, Locale.getDefault())
    private val mSimpleNameFormat = SimpleDateFormat(AppModuleConstants.DATE_NAME_FORMAT, Locale.getDefault())

    init {
        mSimpleDateFormat.timeZone = TimeZone.getTimeZone("GTM")
        mSimpleNameFormat.timeZone = TimeZone.getTimeZone("GTM")
    }


    override fun onCreateView(view: View?) {
        mHistoryDetailFragmentView.initViewComponents(view)
        mHistoryDetailFragmentView.initComponents()
        mHistoryDetailFragmentView.populateView()
        checkSession()

    }

    fun populateView(isSpecialist: Boolean) {
        if (mAppointment !== null) {
            mHistoryDetailFragmentView.setAddress(mAppointment?.location!!.address)
            mHistoryDetailFragmentView.setPrice(String.format(Locale.getDefault(),
                    "$%,d", Integer.valueOf(mAppointment?.totalPrice!!)))
            mHistoryDetailFragmentView.setServices(mAppointment?.appointmentServices!!.map { appointmentService ->
                ViewServiceBuilder()
                        .setPrice(Integer.toString(appointmentService.price))
                        .setQuantity(appointmentService.count)
                        .setName(appointmentService.service.name)
                        .createViewService()
            })

            mHistoryDetailFragmentView.setSpecialistNameOurCustomer(if (isSpecialist) {
                "${mAppointment!!.customer!!.name} ${mAppointment!!.customer!!.lastName}"

            } else {
                "${mAppointment!!.specialist!!.name} ${mAppointment!!.specialist!!.lastName}"
            })
            mHistoryDetailFragmentView.setPhone(if (isSpecialist) {
                mAppointment!!.customer!!.phoneNumber
            } else mAppointment?.specialist!!.phoneNumber)


            mHistoryDetailFragmentView.changeNameTitle(isSpecialist)
            setDateAndTime()


        }
    }

    fun checkSession() {
        mHistoryDetailFragmentView.showProgressDialog()
        LaFemmeApplication.getInstance()
                .getmUseCaseFactory()
                .getSessionUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread())
                .execute(object : DisposableObserver<User>() {
                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                        mHistoryDetailFragmentView.hideProgressDialog()
                    }

                    override fun onNext(user: User) {

                        mAppointment.let {
                            populateView(user is SpecialistUser)
                            if (user is SpecialistUser) {
                                mHistoryDetailFragmentView.showHideShowAddress(View.VISIBLE)
                                when (mAppointment!!.status!!) {
                                    AppModuleConstants.STATUS_IN_PROGRESS -> {
                                        mHistoryDetailFragmentView.showHideBTNInitService(View.GONE)
                                        mHistoryDetailFragmentView.showHideBTNFinishService(View.VISIBLE)
                                        mHistoryDetailFragmentView.showHideRateAppointmentButton(View
                                                .GONE)
                                        mHistoryDetailFragmentView.showCancelButton(View.GONE)
                                    }
                                    AppModuleConstants.STATUS_APPOINTMENT -> {
                                        mHistoryDetailFragmentView.showHideBTNInitService(View.VISIBLE)
                                        mHistoryDetailFragmentView.showHideBTNFinishService(View.GONE)
                                        mHistoryDetailFragmentView.showHideRateAppointmentButton(View
                                                .GONE)
                                        mHistoryDetailFragmentView.showCancelButton(View.VISIBLE)
                                    }
                                    AppModuleConstants.STATUS_FINISHED -> {
                                        mHistoryDetailFragmentView.showHideBTNInitService(View.GONE)
                                        mHistoryDetailFragmentView.showHideBTNFinishService(View.GONE)
                                        mHistoryDetailFragmentView
                                                .showHideRateAppointmentButton(View.GONE)
                                        mHistoryDetailFragmentView.showCancelButton(View.GONE)

                                    }
                                }


                            } else {
                                mHistoryDetailFragmentView.showHideShowAddress(View.GONE)
                                when (mAppointment!!.status!!) {
                                    AppModuleConstants.STATUS_FINISHED -> {
                                        mHistoryDetailFragmentView.showHideBTNInitService(View.GONE)
                                        mHistoryDetailFragmentView.showHideBTNFinishService(View.GONE)
                                        showHideBTNRate(user)
                                        mHistoryDetailFragmentView.showCancelButton(View.GONE)
                                    }
                                    AppModuleConstants.STATUS_IN_PROGRESS -> {
                                        mHistoryDetailFragmentView.showHideBTNInitService(View.GONE)
                                        mHistoryDetailFragmentView.showHideBTNFinishService(View.GONE)
                                        mHistoryDetailFragmentView.showHideRateAppointmentButton(View
                                                .GONE)
                                        mHistoryDetailFragmentView.showCancelButton(View.GONE)
                                    }
                                    AppModuleConstants.STATUS_APPOINTMENT -> {
                                        mHistoryDetailFragmentView.showHideBTNInitService(View.GONE)
                                        mHistoryDetailFragmentView.showHideBTNFinishService(View.GONE)
                                        mHistoryDetailFragmentView.showHideRateAppointmentButton(View
                                                .GONE)
                                        mHistoryDetailFragmentView.showCancelButton(View.VISIBLE)
                                    }
                                }

                            }


                        }

                        mHistoryDetailFragmentView.hideProgressDialog()
                    }
                }, null)
    }


    fun showHideBTNRate(user: User) {
        mAppointment.let {
            if (mAppointment!!.appointmentRatings !== null) {
                var hasFound = false
                for (rating in mAppointment!!.appointmentRatings!!.iterator()) {
                    if (rating.userUuid == user.uuid) {
                        mHistoryDetailFragmentView
                                .showHideRateAppointmentButton(View.GONE)
                        hasFound = true
                        break
                    }

                }
                if (!hasFound) {
                    mHistoryDetailFragmentView
                            .showHideRateAppointmentButton(View.VISIBLE)
                }

            } else {
                mHistoryDetailFragmentView
                        .showHideRateAppointmentButton(View.VISIBLE)
            }
        }

    }

    override fun showAddress(activity: Activity) {
        mAppointment.let {
            val uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%s", mAppointment!!.location
            !!.lat, mAppointment!!.location!!.lng, mAppointment!!.location
            !!.address)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            val title = activity.getString(R.string.show_address_label)
            val chooser = Intent.createChooser(intent, title)
            if (intent.resolveActivity(activity.packageManager) !== null) {
                activity.startActivity(chooser)
            }
        }
    }

    fun setDateAndTime() {
        val startDate = mSimpleDateFormat.parse(mAppointment?.startDateTime)
        val endDate = mSimpleDateFormat.parse(mAppointment?.endDateTime)
        val startCalendar = Calendar.getInstance()
        startCalendar.time = startDate
        val endCalendar = Calendar.getInstance()
        endCalendar.time = endDate
        val finalMinutes = if (endCalendar.get(Calendar.MINUTE) > 10)
            "${endCalendar.get(Calendar.MINUTE)}" else "0${endCalendar.get(Calendar.MINUTE)}"
        mHistoryDetailFragmentView.setTime("Inicio: " +
                "${startCalendar.get(Calendar.HOUR_OF_DAY)}:${startCalendar.get(Calendar.MINUTE)}" +
                " Cierre: ${endCalendar.get(Calendar.HOUR_OF_DAY)}:$finalMinutes")
        val localStartDate = DateTime(startDate)
        mHistoryDetailFragmentView.setDate("${localStartDate.dayOfMonth().asText} ${localStartDate.monthOfYear().asText} " +
                "${localStartDate.year().asText}")
    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks?) {
        mFragmentCallback = fragmentCallback
        LaFemmeApplication.getInstance()
                .getmBus()
                .register(this)
    }

    override fun onCreate(arguments: Bundle?) {
        if (arguments !== null) {
            mAppointment = arguments.getParcelable(AppModuleConstants.VIEW_HISTORY_DETAIL)
        }
    }

    override fun cancelAppointment() {
        if (mAppointment !== null) {
            mHistoryDetailFragmentView.showProgressDialog()
            val iterator: IUseCaseIterator<Appointment, String> =
                    LaFemmeApplication.getInstance()
                            .getmUseCaseFactory()
                            .cancelAppointmentUseCase(Schedulers.io(),
                                    AndroidSchedulers.mainThread())

            iterator.execute(object : DisposableObserver<Appointment>() {
                override fun onNext(value: Appointment?) {
                    mHistoryDetailFragmentView.onAppointmentCanceled()
                    mHistoryDetailFragmentView.hideProgressDialog()

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    mHistoryDetailFragmentView.hideProgressDialog()
                }

                override fun onComplete() {

                }

            }, mAppointment?.uuid)
        }
    }

    override fun onViewClicked(view: View, parcelable: Parcelable?) {
        if (mFragmentCallback !== null) {
            when (view.id) {
                R.id.btn_rate_service -> mFragmentCallback!!.onViewClicked(view, mAppointment)
                else -> mFragmentCallback!!.onViewClicked(view, parcelable)
            }


        }
    }

    override fun startFinishAppointment(start: Boolean) {

        mAppointment.let {
            mHistoryDetailFragmentView.showProgressDialog()
            LaFemmeApplication
                    .getInstance()
                    .getmUseCaseFactory()
                    .startFinishAppointmentUseCase(Schedulers.computation(),
                            AndroidSchedulers.mainThread())
                    .execute(object : DisposableObserver<Appointment>() {
                        override fun onComplete() {
                        }

                        override fun onNext(t: Appointment) {
                            if (start) {
                                mHistoryDetailFragmentView.showHideBTNFinishService(View.VISIBLE)
                                mHistoryDetailFragmentView.showHideBTNInitService(View.GONE)

                            } else {
                                mHistoryDetailFragmentView.showHideBTNFinishService(View.GONE)
                                mHistoryDetailFragmentView.showHideBTNInitService(View.GONE)
                            }
                            mHistoryDetailFragmentView.showCancelButton(View.GONE)
                            LaFemmeApplication
                                    .getInstance()
                                    .getmBus()
                                    .post(OnAppointmentStartedFinished(start, t))
                            mHistoryDetailFragmentView.hideProgressDialog()
                        }

                        override fun onError(e: Throwable?) {
                            e?.printStackTrace()
                            mHistoryDetailFragmentView.hideProgressDialog()
                        }
                    }, StartFinishAppointmentParams(start,
                            mAppointment!!.uuid!!))
        }

    }

    override fun onDestroy() {
        mFragmentCallback = null
        LaFemmeApplication.getInstance()
                .getmBus()
                .unregister(this)

    }

    @Subscribe
    fun onAppointmentRated(onAppointmentRatedEvent: OnAppointmentRatedEvent) {
        mHistoryDetailFragmentView.showHideRateAppointmentButton(View.GONE)
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