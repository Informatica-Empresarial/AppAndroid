package co.com.lafemmeapp.dataprovider.network.api_routes

import co.com.lafemmeapp.dataprovider.network.entities.APIDeviceRegistrationResponse
import co.com.lafemmeapp.dataprovider.network.entities.DeviceRegisterParams
import co.com.lafemmeapp.dataprovider.params.DeleteDeviceRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by oscargallon on 5/29/17.
 */
interface IDeviceAPI {

    @POST("devices/register")
    fun registerDevice(@Header("Authorization") token: String, @Body data: DeviceRegisterParams)
            : Observable<APIDeviceRegistrationResponse>

    @POST("devices/delete")
    fun deleteDevice(@Header("Authorization") token: String,
                     @Body deleteDeviceRequest: DeleteDeviceRequest):Observable<Any>
}