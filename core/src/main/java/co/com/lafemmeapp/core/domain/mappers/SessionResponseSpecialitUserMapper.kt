package co.com.lafemmeapp.core.domain.mappers

import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.SpecialistUserBuilder
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import io.reactivex.functions.Function

/**
 * Created by oscargallon on 6/8/17.
 */
class SessionResponseSpecialitUserMapper private constructor() : Function<SessionResponse, SpecialistUser> {

    override fun apply(sessionResponse: SessionResponse): SpecialistUser {
        return SpecialistUserBuilder()
                .setName(sessionResponse.name)
                .setLastName(sessionResponse.lastName)
                .setEmail(sessionResponse.email)
                .setAddress(sessionResponse.address)
                .setAvatar(sessionResponse.avatar)
                .setSOS(sessionResponse.isSOS)
                .setPhoneNumber(sessionResponse.phoneNumber)
                .setUuid(sessionResponse.uuid)
                .setCity(sessionResponse.city)
                .createSpecialistUser()
    }

    private object Holder {
        val instance = SessionResponseSpecialitUserMapper()
    }

    companion object {
        val instance by lazy {
            Holder.instance
        }

    }

}