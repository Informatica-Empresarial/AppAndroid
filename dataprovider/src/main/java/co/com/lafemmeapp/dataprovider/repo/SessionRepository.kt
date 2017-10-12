package co.com.lafemmeapp.dataprovider.repo


import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.di.RoutesModule
import co.com.lafemmeapp.dataprovider.di.scopes.DatabaseScope
import co.com.lafemmeapp.dataprovider.di.scopes.RouteScope
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException
import co.com.lafemmeapp.dataprovider.local.AppDatabase
import co.com.lafemmeapp.dataprovider.local.entities.DBSessionRessponse
import co.com.lafemmeapp.dataprovider.network.api_routes.ISessionAPI
import co.com.lafemmeapp.dataprovider.network.entities.FacebookLoginRequest
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.params.EditProfileRequest
import co.com.lafemmeapp.dataprovider.params.RequestPasswordResetParams
import co.com.lafemmeapp.dataprovider.repo.interfaces.ISessionRepository
import io.reactivex.*
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.PUT
import javax.inject.Inject

/**
 * Created by oscargallon on 6/2/17.
 */
@DatabaseScope
@RouteScope
class SessionRepository : ISessionRepository {



    @Inject
    lateinit var mISessionAPI: ISessionAPI

    @Inject
    lateinit var mDatabase: AppDatabase


    @Throws(Exception::class)
    override fun login(loginRequest: LoginRequest): Observable<SessionResponse> =
            mISessionAPI.login(loginRequest)


    @Throws(Exception::class)
    override fun storeSession(sessionResponse: DBSessionRessponse): Observable<DBSessionRessponse> {
        return Observable.create { emitter ->
            mDatabase.sessionModel()
                    .insertSession(dbSessionResponse = sessionResponse)
            emitter.onNext(sessionResponse)
        }
    }

    @Throws(Exception::class)
    override fun getSession(): Observable<SessionResponse> {
        return Single.create(SingleOnSubscribe<DBSessionRessponse> { emitter ->
            val session = mDatabase.sessionModel()
                    .getSession()
            if (session === null) {
                emitter.onError(NoSessionException())
            } else {
                emitter.onSuccess(session)
            }
        }).map { (name, lastName, address, email, phoneNumber, uuid, avatar, token, role, city, SOS) ->

            SessionResponse(name,
                    lastName, address,
                    email,
                    phoneNumber,
                    uuid,
                    avatar,
                    token,
                    arrayListOf(role),
                    if (SOS !== null) SOS else false,
                    city)

        }.toObservable()
    }

    @Throws(Exception::class)
    override fun deleteSession(): Observable<Boolean> =
            Observable.create { emitter ->
                mDatabase
                        .sessionModel()
                        .deleteSession()
                mDatabase
                        .valuesModel()
                        .delete(Constants.DBTOKEN_KEY)

                emitter.onNext(true)
                emitter.onComplete()
            }

    override fun updateSOS(isActive: Boolean, uuid: String) {
        mDatabase.sessionModel()
                .updateSOS(isActive, uuid)
    }

    override fun loginWithFacebook(token: String): Observable<SessionResponse> {
        return mISessionAPI.loginWithFacebook(FacebookLoginRequest(token))
    }

    override fun requestPasswordReset(email: String): Observable<String> {
        return mISessionAPI.requestPasswordReset(RequestPasswordResetParams(email))
    }

    override fun updateUser(token: String, editProfileRequest: EditProfileRequest)
            : Observable<SessionResponse> {
        return mISessionAPI.updateUser(token,
                editProfileRequest)
    }
    override fun updatePhoneNumber(arg0: String, arg1: String) {
        mDatabase.sessionModel()
                .updatePhoneNumber(arg0, arg1)
    }

    override fun updateEmail(arg0: String, arg1: String) {
        mDatabase.sessionModel()
                .updateEmail(arg0, arg1)
    }


}