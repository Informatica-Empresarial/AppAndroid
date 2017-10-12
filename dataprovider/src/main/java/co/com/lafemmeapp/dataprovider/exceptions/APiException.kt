package co.com.lafemmeapp.dataprovider.exceptions

import co.com.lafemmeapp.dataprovider.network.entities.ApiError
import com.google.gson.Gson
import retrofit2.Response

/**
 * Created by oscargallon on 6/15/17.
 */
data class APiException(val mResponse: Response<*>) : Exception() {

    val mStatusCode: Int = mResponse.code()

    val mError: ApiError?

    init {
        if (mResponse.errorBody() != null) {
            mError = Gson().fromJson(mResponse.errorBody()
                    .string(), ApiError::class.java)
        } else {
            mError = null
        }

    }

    override val message: String?
        get() = if (mError != null) mError.message else super.message
}