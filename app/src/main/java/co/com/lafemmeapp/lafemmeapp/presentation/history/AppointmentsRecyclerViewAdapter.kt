package co.com.lafemmeapp.lafemmeapp.presentation.history

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import org.joda.time.DateTime
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by oscargallon on 5/22/17.
 */
class AppointmentsRecyclerViewAdapter(var mAppointmentsList: ArrayList<Appointment>,
                                      val mFragmentCallback: IFragmentCallbacks,
                                      val mWeakContextReference: WeakReference<Context>) :
        RecyclerView.Adapter<AppointmentViewHolder>() {


    val mSimpleDateFormat = SimpleDateFormat(AppModuleConstants.DATE_TIME_FORMAT, Locale.getDefault())

    val mSimpleNameFormat = SimpleDateFormat(AppModuleConstants.DATE_NAME_FORMAT, Locale.getDefault())


    init {
        mSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        mSimpleNameFormat.timeZone = TimeZone.getTimeZone("UTC")

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.appointment_item, parent, false)

        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = mAppointmentsList[position]
        val startDate = mSimpleDateFormat.parse(appointment.startDateTime)
        val endDate = mSimpleDateFormat.parse(appointment.endDateTime)
        val startCalendar = Calendar.getInstance()
        startCalendar.time = startDate
        val endCalendar = Calendar.getInstance()
        endCalendar.time = endDate

        val localStartDate = DateTime(startDate)
        holder.mTVDate.text = "${localStartDate.dayOfMonth().asText} ${localStartDate.monthOfYear().asText} ${localStartDate.year().asText}"

        holder.mIVLeftToken.setBackgroundColor(if (position % 2 == 0) mWeakContextReference.get()
                ?.resources!!.getColor(R.color.colorAccent) else mWeakContextReference.get()
                ?.resources!!.getColor(R.color.cloudyBlue))
        val startMinutes = if (startCalendar.get(Calendar.MINUTE) > 10) "${startCalendar.get(Calendar.MINUTE)}"
        else "0${startCalendar.get(Calendar.MINUTE)}"
        val finalMinutes = if (endCalendar.get(Calendar.MINUTE) > 10)
            "${endCalendar.get(Calendar.MINUTE)}" else "0${endCalendar.get(Calendar.MINUTE)}"
        holder.mTVTime.text = "inicio: ${startCalendar.get(Calendar.HOUR_OF_DAY)}:$startMinutes Cierre:${endCalendar.get(Calendar.HOUR_OF_DAY)}:$finalMinutes"

        holder.itemView.setOnClickListener {
            mFragmentCallback.onViewClicked(holder.mIVShowAppoitmentDetails, appointment)
        }

    }

    override fun getItemCount(): Int {
        return mAppointmentsList.size
    }

    fun getAppointmentsList(): ArrayList<Appointment> {
        return mAppointmentsList
    }

    fun setAppointmentList(appointmentList: ArrayList<Appointment>) {
        mAppointmentsList = appointmentList
        notifyDataSetChanged()
    }

    fun clear() {
        mAppointmentsList.clear()
    }
}