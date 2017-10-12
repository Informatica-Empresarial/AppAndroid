package co.com.lafemmeapp.dataprovider.network.entities

/**
 * Created by oscargallon on 6/15/17.
 */
data class ApiError(val statusCode: Int,
                    val error: String,
                    val message: String? = "Something went wrong")