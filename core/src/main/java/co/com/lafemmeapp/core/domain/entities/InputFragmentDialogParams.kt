package co.com.lafemmeapp.core.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by oscargallon on 6/13/17.
 */
data  class InputFragmentDialogParams(val title: String? = null,
                                      val message: String? = null,
                                      val showBothButtons: Boolean = false,
                                      val positiveButtonText: String? = null,
                                      val negativeButtonText: String? = null,
                                      val action: String? = null,
                                      val inputHint: String? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<InputFragmentDialogParams> = object : Parcelable.Creator<InputFragmentDialogParams> {
            override fun createFromParcel(source: Parcel): InputFragmentDialogParams = InputFragmentDialogParams(source)
            override fun newArray(size: Int): Array<InputFragmentDialogParams?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    1 == source.readInt(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(message)
        dest.writeInt((if (showBothButtons) 1 else 0))
        dest.writeString(positiveButtonText)
        dest.writeString(negativeButtonText)
        dest.writeString(action)
        dest.writeString(inputHint)
    }
}