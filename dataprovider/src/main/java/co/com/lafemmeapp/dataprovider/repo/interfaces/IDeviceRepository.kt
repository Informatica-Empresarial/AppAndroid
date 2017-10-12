package co.com.lafemmeapp.dataprovider.repo.interfaces

import co.com.lafemmeapp.dataprovider.network.entities.APIDeviceRegistrationResponse
import co.com.lafemmeapp.dataprovider.network.entities.DeviceRegisterParams
import co.com.lafemmeapp.dataprovider.params.DeleteDeviceRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header

/**
 * Created by oscargallon on 5/29/17.
 */

interface IDeviceRepository {
    fun registerDevice(token: String,
                       deviceRegisterParams: DeviceRegisterParams): Observable<APIDeviceRegistrationResponse>


    fun deleteDevice( token: String,
                     deleteDeviceRequest: DeleteDeviceRequest):Observable<Any>
}
