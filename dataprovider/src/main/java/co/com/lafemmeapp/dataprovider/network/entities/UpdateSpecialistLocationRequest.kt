package co.com.lafemmeapp.dataprovider.network.entities

/**
 * Created by oscargallon on 6/5/17.
 */
data class UpdateSpecialistLocationRequest(val lastKnownLocation: LastKnownLocation?) {

}

data class LastKnownLocation(val lat: Double,
                             val lng: Double)

