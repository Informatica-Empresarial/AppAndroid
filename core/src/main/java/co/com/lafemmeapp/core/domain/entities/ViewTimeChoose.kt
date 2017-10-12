package co.com.lafemmeapp.core.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by oscargallon on 5/18/17.
 */

data class ViewTimeChoose(val time: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ViewTimeChoose> = object : Parcelable.Creator<ViewTimeChoose> {
            override fun createFromParcel(source: Parcel): ViewTimeChoose = ViewTimeChoose(source)
            override fun newArray(size: Int): Array<ViewTimeChoose?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(time)
    }
}
