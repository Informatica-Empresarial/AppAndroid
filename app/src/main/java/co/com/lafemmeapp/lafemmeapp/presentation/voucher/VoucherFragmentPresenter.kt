package co.com.lafemmeapp.lafemmeapp.presentation.voucher

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator
import co.com.lafemmeapp.core.domain.use_cases.appointments.GetPreScheduleAppointmentUseCase
import co.com.lafemmeapp.core.domain.use_cases.appointments.ScheduleAppointmentUseCase
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.params.ScheduleAppointmentParams
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by oscargallon on 5/17/17.
 */
class VoucherFragmentPresenter(override var mVoucherFragmentView: IVoucherFragmentView) : IVoucherFragmentPresenter {


    private var mFragmentCallback: IFragmentCallbacks? = null

    private var mViewServicesRequest: ViewServicesRequest? = null

    private val mSimpleDateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())

    private val mSimpleNameFormat = SimpleDateFormat(AppModuleConstants.DATE_NAME_FORMAT, Locale.getDefault())

    private var mAppointment: Appointment? = null

    init {

        this.mVoucherFragmentView = mVoucherFragmentView
        mSimpleDateFormat.timeZone = TimeZone.getDefault()
    }


    override fun onCreateView(view: View?) {
        mVoucherFragmentView.initViewComponents(view)
        mVoucherFragmentView.initComponents()
        getPreScheduleAppointment()

    }

    private fun getPreScheduleAppointment() {

        mVoucherFragmentView.showProgressBar()
        val iterator: IUseCaseIterator<Appointment, ViewServicesRequest>
                = LaFemmeApplication.getInstance()
                .getmUseCaseFactory()
                .getPreScheduleAppointmentUseCase(Schedulers.io(), AndroidSchedulers.mainThread())


        iterator.execute(object : DisposableObserver<Appointment>() {
            override fun onComplete() {

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mVoucherFragmentView.dissmissProgressBar()
            }

            override fun onNext(value: Appointment) {
                val startDateTime = DateTime.parse(value.startDateTime)
                        .toDateTime(DateTimeZone.forTimeZone(TimeZone.getDefault()))
                val startDateString = mSimpleDateFormat.format(startDateTime.toDate())
                val endDateTime = DateTime.parse(value.endDateTime)
                        .toDateTime(DateTimeZone.forTimeZone(TimeZone.getDefault()))
                val endDateString = mSimpleDateFormat.format(endDateTime.toDate())

                mAppointment = value
                mVoucherFragmentView.showAppointmentAddress(value.location!!.address)
                mVoucherFragmentView.showAppointmentSpecialist(value.specialist!!
                        .name, value.specialist!!.lastName)
                mVoucherFragmentView.showServices(mViewServicesRequest!!
                        .getmViewServicesSelected())
                if (value.totalPrice !== null) {
                    mVoucherFragmentView.showAppointmentPrice(Integer.valueOf(value.totalPrice))
                }
                val startDate = mSimpleDateFormat.parse(startDateString)
                val endDate = mSimpleDateFormat.parse(endDateString)
                val startCalendar = Calendar.getInstance()
                startCalendar.time = startDate
                val endCalendar = Calendar.getInstance()
                endCalendar.time = endDate
                val finalMinutes = if (endCalendar.get(Calendar.MINUTE) > 10)
                    "${endCalendar.get(Calendar.MINUTE)}" else "0${endCalendar.get(Calendar.MINUTE)}"
                mVoucherFragmentView.showAppointmentTime("inicio: " +
                        "${startCalendar.get(Calendar.HOUR_OF_DAY)}:${startCalendar.get(Calendar.MINUTE)}" +
                        " Cierre: ${endCalendar.get(Calendar.HOUR_OF_DAY)}:$finalMinutes")
                mVoucherFragmentView.showAppointmentDate(mSimpleNameFormat.format(startDate))
                mVoucherFragmentView.changeBTNNextAvailability(true)
                mVoucherFragmentView.dissmissProgressBar()


            }

        }, mViewServicesRequest)


    }


    override fun onAttach(fragmentCallback: IFragmentCallbacks) {
        mFragmentCallback = fragmentCallback
    }


    override fun onCreate(arguments: Bundle?) {
        if (arguments !== null) {
            mViewServicesRequest = arguments.getParcelable(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY)
        }
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

    private fun scheduleAppointment() {
        if (mAppointment !== null) {
            mVoucherFragmentView.showProgressDialog()
            val iterator: IUseCaseIterator<Appointment, ScheduleAppointmentParams> =
                    LaFemmeApplication.getInstance()
                            .getmUseCaseFactory()
                            .scheduleAppointmentUseCase(Schedulers.io(),
                                    AndroidSchedulers.mainThread())

            iterator.execute(object : DisposableObserver<Appointment>() {
                override fun onError(e: Throwable?) {
                    if (e !== null) e.printStackTrace()
                    mVoucherFragmentView.hideProgressDialog()
                }

                override fun onComplete() {

                }

                override fun onNext(value: Appointment?) {
                    if (value !== null && mAppointment !== null
                            && mAppointment!!.specialist !== null) {
                        mVoucherFragmentView.showConfirmationAppointmentDialog(mAppointment!!
                                .specialist!!)
                    }
                    mVoucherFragmentView.hideProgressDialog()
                }

            }, ScheduleAppointmentParams(mAppointment!!.uuid!!,
                    mViewServicesRequest!!.ismSOS()))
        }

    }

    override fun onViewClicked(view: View?, parcelable: Parcelable?) {
        if (view !== null) {
            when (view.id) {
                R.id.btn_next_voucher -> scheduleAppointment()
                else -> mFragmentCallback?.onViewClicked(view, parcelable)
            }

        }


    }

}