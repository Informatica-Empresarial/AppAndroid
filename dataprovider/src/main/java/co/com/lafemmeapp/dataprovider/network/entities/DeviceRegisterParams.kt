package co.com.lafemmeapp.dataprovider.network.entities

/**
 * Created by oscargallon on 5/29/17.
 */
data class DeviceRegisterParams(val userUuid: String,
                                val platform: String? = "Android",
                                val deviceUuid: String,
                                val appIdentifier: String,
                                val deviceData: String? = null)
