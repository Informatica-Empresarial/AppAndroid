package co.com.lafemmeapp.core.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by oscargallon on 5/18/17.
 */

data class Location(val lat: Double, val lng: Double,
               val address: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Location> = object : Parcelable.Creator<Location> {
            override fun createFromParcel(source: Parcel): Location = Location(source)
            override fun newArray(size: Int): Array<Location?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readDouble(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeDouble(lat)
        dest.writeDouble(lng)
        dest.writeString(address)
    }
}
