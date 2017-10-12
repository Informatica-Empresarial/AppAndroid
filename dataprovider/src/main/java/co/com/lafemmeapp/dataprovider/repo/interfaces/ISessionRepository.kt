package co.com.lafemmeapp.dataprovider.repo.interfaces

import android.arch.persistence.room.Query
import co.com.lafemmeapp.dataprovider.local.entities.DBSessionRessponse
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.params.EditProfileRequest
import co.com.lafemmeapp.dataprovider.params.RequestPasswordResetParams
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body

/**
 * Created by oscargallon on 6/2/17.
 */
interface ISessionRepository {

    @Throws(Exception::class)
    fun storeSession(sessionResponse: DBSessionRessponse): Observable<DBSessionRessponse>

    fun login(loginRequest: LoginRequest): Observable<SessionResponse>

    fun loginWithFacebook(token: String): Observable<SessionResponse>

    fun getSession(): Observable<SessionResponse>

    fun deleteSession(): Observable<Boolean>

    fun updateSOS(isActive: Boolean, uuid: String)

    fun requestPasswordReset(email: String)
            : Observable<String>

    fun updateUser(token: String, editProfileRequest: EditProfileRequest): Observable<SessionResponse>


    fun updatePhoneNumber( arg0: String, arg1: String)


    fun updateEmail( arg0: String, arg1: String)
}