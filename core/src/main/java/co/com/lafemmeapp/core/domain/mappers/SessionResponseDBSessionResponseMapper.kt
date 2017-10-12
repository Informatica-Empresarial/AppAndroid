package co.com.lafemmeapp.core.domain.mappers

import co.com.lafemmeapp.dataprovider.local.entities.DBSessionRessponse
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import io.reactivex.functions.Function

/**
 * Created by oscargallon on 6/2/17.
 */
class SessionResponseDBSessionResponseMapper private constructor() : Function<SessionResponse, DBSessionRessponse> {
    override fun apply(t: SessionResponse): DBSessionRessponse {
        return DBSessionRessponse(name = t.name,
                lastName = t.lastName,
                address = t.address,
                email = t.email,
                phoneNumber = t.phoneNumber,
                uuid = t.uuid,
                avatar = t.avatar,
                token = t.token,
                role = t.roles[0],
                SOS = t.isSOS,
                city = t.city)
    }

    private object Holder {
        val instance = SessionResponseDBSessionResponseMapper()
    }

    companion object {
        val instance: SessionResponseDBSessionResponseMapper by lazy {
            Holder.instance
        }
    }
}