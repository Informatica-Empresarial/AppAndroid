package co.com.lafemmeapp.core.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by oscargallon on 6/12/17.
 */
data class AppointmentRating(val uuid: String, val rating: Int,
                             val comments: String?,
                             val appointmentUuid: String,
                             val userUuid: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<AppointmentRating> = object : Parcelable.Creator<AppointmentRating> {
            override fun createFromParcel(source: Parcel): AppointmentRating = AppointmentRating(source)
            override fun newArray(size: Int): Array<AppointmentRating?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readInt(),
    source.readString(),
    source.readString(),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(uuid)
        dest.writeInt(rating)
        dest.writeString(comments)
        dest.writeString(appointmentUuid)
        dest.writeString(userUuid)
    }
}