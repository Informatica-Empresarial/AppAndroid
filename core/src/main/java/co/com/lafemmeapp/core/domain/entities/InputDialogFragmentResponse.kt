package co.com.lafemmeapp.core.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by oscargallon on 6/13/17.
 */
data class InputDialogFragmentResponse(val action: String,
                                       val wasPositive: Boolean,
                                       val text: String? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<InputDialogFragmentResponse> = object : Parcelable.Creator<InputDialogFragmentResponse> {
            override fun createFromParcel(source: Parcel): InputDialogFragmentResponse = InputDialogFragmentResponse(source)
            override fun newArray(size: Int): Array<InputDialogFragmentResponse?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    1 == source.readInt(),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(action)
        dest.writeInt((if (wasPositive) 1 else 0))
        dest.writeString(text)
    }
}