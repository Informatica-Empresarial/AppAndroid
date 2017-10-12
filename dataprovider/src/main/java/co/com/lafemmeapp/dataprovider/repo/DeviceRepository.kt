package co.com.lafemmeapp.dataprovider.repo

import co.com.lafemmeapp.dataprovider.network.APIProvider
import co.com.lafemmeapp.dataprovider.network.api_routes.IDeviceAPI
import co.com.lafemmeapp.dataprovider.network.entities.APIDeviceRegistrationResponse
import co.com.lafemmeapp.dataprovider.network.entities.DeviceRegisterParams
import co.com.lafemmeapp.dataprovider.params.DeleteDeviceRequest
import co.com.lafemmeapp.dataprovider.repo.interfaces.IDeviceRepository
import io.reactivex.Observable

/**
 * Created by oscargallon on 5/29/17.
 */
class DeviceRepository : IDeviceRepository {


    private val mDeviceAPI: IDeviceAPI = APIProvider
            .getInstance()
            .retrofit.create(IDeviceAPI::class.java)

    override fun registerDevice(token: String, deviceRegisterParams: DeviceRegisterParams)
            : Observable<APIDeviceRegistrationResponse> {
        return mDeviceAPI.registerDevice(token, deviceRegisterParams)
    }

    override fun deleteDevice(token: String, deleteDeviceRequest: DeleteDeviceRequest): Observable<Any> {

        return mDeviceAPI.deleteDevice(token, deleteDeviceRequest)
    }
}