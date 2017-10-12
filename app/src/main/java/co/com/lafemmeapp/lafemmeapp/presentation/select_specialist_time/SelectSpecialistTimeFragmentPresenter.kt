package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.DatePickerMinMaxDate
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest
import co.com.lafemmeapp.core.domain.entities.ViewTimeChoose
import co.com.lafemmeapp.core.domain.entities.abstracts.SpecialistTime
import co.com.lafemmeapp.core.domain.params.FilterSpecialistAvailabilityWithDateParam
import co.com.lafemmeapp.core.domain.use_cases.system.GetDatePickerDialogMinMaxDateUseCase
import co.com.lafemmeapp.core.domain.use_cases.system.GetTodayDatesFromSpecialistAvailabilityUseCase
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.providers.utils.UtilsProvidersFactory
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by oscargallon on 5/9/17.
 */

class SelectSpecialistTimeFragmentPresenter(private val mSelectSpecialistFragmentView: ISelectSpecialistTimeFragmentView?) : ISelectSpecialistTimeFragmentPresenter {

    private var mViewServicesRequest: ViewServicesRequest? = null

    private var mFragmentCallbacks: IFragmentCallbacks? = null

    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat(AppModuleConstants.DATE_TIME_FORMAT,
            Locale.getDefault())

    private val mSimpleDateNamedFormat: SimpleDateFormat = SimpleDateFormat(AppModuleConstants.DATE_NAME_FORMAT,
            Locale.getDefault())


    override fun onCreate(arguments: Bundle?) {
        mViewServicesRequest = arguments?.getParcelable<ViewServicesRequest>(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY)

    }

    override fun onCreateView(view: View) {
        if (mSelectSpecialistFragmentView !== null && mViewServicesRequest !== null
                && mViewServicesRequest!!.getmSpecialistUserSelected() !== null) {
            mSelectSpecialistFragmentView.initViewComponents(view)
            mSelectSpecialistFragmentView.initComponents()
            mSelectSpecialistFragmentView
                    .showSpecialistName(mViewServicesRequest!!.getmSpecialistUserSelected()
                            .name)
            mSelectSpecialistFragmentView
                    .showSpecialistAditionalInfo(mViewServicesRequest!!.getmSpecialistUserSelected()
                            .address)
            mSelectSpecialistFragmentView.showDate(mSimpleDateNamedFormat!!.format(Calendar.getInstance().time).toUpperCase())

            if (mViewServicesRequest!!.getmSpecialistUserSelected().avatar !== null) {
                mSelectSpecialistFragmentView
                        .showSpecialistAvatar(mViewServicesRequest!!.getmSpecialistUserSelected().avatar)
            }

        }
    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks) {
        mFragmentCallbacks = fragmentCallback
    }


    override fun onDestroy() {
        mFragmentCallbacks = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

    }

    override fun subscribeTextViewToTextWatcherEvent(textView: TextView, validatorMapper: ITextViewValidatorMapper) {

    }

    override fun onTextChangeMappedEvent(textChangedMappedEvent: TextChangedMappedEvent) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

    }

    override fun getSpecialistTime(date: Date) {
        if (mViewServicesRequest !== null
                && mViewServicesRequest!!.getmSpecialistUserSelected() !== null
                && mSelectSpecialistFragmentView !== null) {

            val iterator = GetTodayDatesFromSpecialistAvailabilityUseCase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            iterator.execute(object : DisposableObserver<List<String>>() {
                override fun onNext(value: List<String>) {

                    mSelectSpecialistFragmentView
                            .showSpecialistUserAvailableTimeForADate(value.map { time ->
                                SpecialistTime(time, false)
                            })

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            }, FilterSpecialistAvailabilityWithDateParam(mViewServicesRequest!!
                    .getmSpecialistUserSelected().validStartDateTimes, date))


        }
    }

    override fun showCalendar() {
        if (mSelectSpecialistFragmentView !== null
                && mViewServicesRequest !== null
                && mViewServicesRequest!!.getmSpecialistUserSelected() !== null) {
            val iterator = GetDatePickerDialogMinMaxDateUseCase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            iterator.execute(object : DisposableObserver<DatePickerMinMaxDate>() {
                override fun onNext(datePickerMinMaxDate: DatePickerMinMaxDate?) {
                    if (datePickerMinMaxDate !== null && mSelectSpecialistFragmentView !== null) {

                        mSelectSpecialistFragmentView.showDatePicker(datePickerMinMaxDate)
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            }, mViewServicesRequest!!.getmSpecialistUserSelected().validStartDateTimes)


        }
    }

    override fun onDateSet(datePickerDialog: DatePickerDialog, year: Int, month: Int, dayOfMonth: Int) {
        val dateChoosen = UtilsProvidersFactory
                .getInstance()
                .getmDateProvider()
                .getCalendar(year, month, dayOfMonth)
        getSpecialistTime(dateChoosen.time)
        if (mSelectSpecialistFragmentView !== null && mSimpleDateNamedFormat !== null) {
            mSelectSpecialistFragmentView
                    .showDate(mSimpleDateNamedFormat.format(dateChoosen.time).toUpperCase())
        }
    }


    override fun onViewClicked(view: View, parcelable: Parcelable?) {
        if (mFragmentCallbacks !== null && view.id == R.id.btn_next_select_specialist_time) {
            mFragmentCallbacks!!.onViewClicked(view, mViewServicesRequest)
        } else if (mFragmentCallbacks !== null &&
                view.id == R.id.cl_container_time && mSelectSpecialistFragmentView !== null) {
            try {
                val dateShown = mSimpleDateNamedFormat!!.parse(mSelectSpecialistFragmentView
                        .getDateShown())
                mViewServicesRequest!!.setmChooseDate((parcelable as ViewTimeChoose).time)
                val dateShownCalendar = Calendar.getInstance()
                dateShownCalendar.time = dateShown
                dateShownCalendar.set(dateShownCalendar.get(Calendar.YEAR), dateShownCalendar.get(Calendar.MONTH),
                        dateShownCalendar.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(parcelable.time
                                .split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])!!, Integer.valueOf(parcelable.time
                        .split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])!!)

                mViewServicesRequest!!.setmChooseDate(mSimpleDateFormat.format(dateShownCalendar.time))
                mSelectSpecialistFragmentView.changeBTNNextAvailability(true)
            } catch (e: ParseException) {
                e.printStackTrace()
            }


        }
    }
}
