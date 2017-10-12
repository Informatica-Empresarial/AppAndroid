package co.com.lafemmeapp.core.domain.mappers

import co.com.lafemmeapp.core.domain.entities.City
import co.com.lafemmeapp.dataprovider.network.entities.APICity
import io.reactivex.functions.Function

/**
 * Created by oscargallon on 7/10/17.
 */
class APICItyCityMapper private constructor(): Function<APICity, City> {
    override fun apply(apiCity: APICity): City {
        return City(apiCity.name,
                apiCity.country,
                apiCity.lat,
                apiCity.lon,
                apiCity.isSelectable)
    }

    private object Holder{
        val instance  = APICItyCityMapper()
    }

    companion object {
        val instance:APICItyCityMapper by lazy {
            Holder.instance
        }
    }

}