package co.com.lafemmeapp.core.domain.entities

import android.os.Parcel
import android.os.Parcelable
import co.com.lafemmeapp.dataprovider.network.entities.LocationRequest
import com.google.gson.annotations.SerializedName

/**
 * Created by oscargallon on 5/18/17.
 * This class represents an appointment
 */
data class Appointment(val uuid: String? = null, val status: String? = null, val startDateTime: String? = null,
                       val endDateTime: String? = null, val location: Location? = null,
                       val hasDisCountCoupon: Boolean? = false, val currency: String? = null,
                       val totalPrice: String? = null,
                       val specialist: SpecialistUser? = null,
                       val customer: Customer? = null,
                       @SerializedName("appointment_services") val appointmentServices: List<AppointmentService>? = null,
                       val appointmentRatings: List<AppointmentRating>? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Appointment> = object : Parcelable.Creator<Appointment> {
            override fun createFromParcel(source: Parcel): Appointment = Appointment(source)
            override fun newArray(size: Int): Array<Appointment?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable<Location>(Location::class.java.classLoader),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readString(),
            source.readParcelable<SpecialistUser>(SpecialistUser::class.java.classLoader),
            source.readParcelable<Customer>(Customer::class.java.classLoader),
            source.createTypedArrayList(AppointmentService.CREATOR),
            source.createTypedArrayList(AppointmentRating.CREATOR)

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(uuid)
        dest.writeString(status)
        dest.writeString(startDateTime)
        dest.writeString(endDateTime)
        dest.writeParcelable(location, 0)
        dest.writeValue(hasDisCountCoupon)
        dest.writeString(currency)
        dest.writeString(totalPrice)
        dest.writeParcelable(specialist, flags)
        dest.writeParcelable(customer, flags)
        dest.writeTypedList(appointmentServices)
        dest.writeTypedList(appointmentRatings)
    }
}