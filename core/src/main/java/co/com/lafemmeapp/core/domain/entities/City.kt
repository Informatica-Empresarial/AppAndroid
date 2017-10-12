package co.com.lafemmeapp.core.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by oscargallon on 7/10/17.
 */
data class City(val name: String,
                val country: String,
                val lat: Double,
                val lon: Double,
                val selectable: Boolean = false) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<City> = object : Parcelable.Creator<City> {
            override fun createFromParcel(source: Parcel): City = City(source)
            override fun newArray(size: Int): Array<City?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readDouble(),
    source.readDouble(),
    1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(country)
        dest.writeDouble(lat)
        dest.writeDouble(lon)
        dest.writeInt((if (selectable) 1 else 0))
    }
}