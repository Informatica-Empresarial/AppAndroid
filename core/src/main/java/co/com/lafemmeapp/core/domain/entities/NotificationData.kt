package co.com.lafemmeapp.core.domain.entities

import co.com.lafemmeapp.dataprovider.network.entities.APIAppointment
import com.google.gson.annotations.SerializedName

/**
 * Created by oscargallon on 6/12/17.
 */
data class NotificationData(@SerializedName("title") val mTitle: String,
                            @SerializedName("message") val mMessage: String,
                            @SerializedName("hasAppointment") val mHasAppointment: Boolean,
                            var mAppointment: Appointment? = null,
                            @SerializedName("action") val mAction: String,
                            @SerializedName("appointment")
                            val mAPIAppointment: APIAppointment? = null)