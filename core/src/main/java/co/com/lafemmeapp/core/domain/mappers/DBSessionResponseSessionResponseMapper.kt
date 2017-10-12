package co.com.lafemmeapp.core.domain.mappers

import co.com.lafemmeapp.dataprovider.local.entities.DBSessionRessponse
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import io.reactivex.functions.Function

/**
 * Created by oscargallon on 6/2/17.
 */
class DBSessionResponseSessionResponseMapper private constructor() : Function<DBSessionRessponse,
        SessionResponse> {

    private object Holder {
        val instance = DBSessionResponseSessionResponseMapper()
    }

    companion object {
        val instance by lazy {
            Holder.instance
        }
    }

    override fun apply(t: DBSessionRessponse): SessionResponse {
        return SessionResponse(t.name, t.lastName,
                t.address, t.email,
                t.phoneNumber, t.uuid,
                t.avatar,
                t.token,
                arrayListOf(t.role),
                if (t.SOS !== null) t.SOS!! else false,
                t.city)
    }
}