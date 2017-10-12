package co.com.lafemmeapp.dataprovider.network.api_routes

import co.com.lafemmeapp.dataprovider.network.entities.FacebookLoginRequest
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse
import co.com.lafemmeapp.dataprovider.network.entities.UserRequest
import co.com.lafemmeapp.dataprovider.params.EditProfileRequest
import co.com.lafemmeapp.dataprovider.params.RequestPasswordResetParams
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

/**
 * Created by oscargallon on 6/2/17.
 */
interface ISessionAPI {

    @POST("sessions")
    fun login(@Body loginRequest: LoginRequest): Observable<SessionResponse>

    @POST("sessions/facebook")
    fun loginWithFacebook(@Body facebookLoginRequest: FacebookLoginRequest): Observable<SessionResponse>

    @POST("request-password-reset")
    fun requestPasswordReset(@Body requestPasswordResetParams: RequestPasswordResetParams)
            : Observable<String>

    @PUT("users/{uuid")
    fun updateUser(@Header("Authorization") token: String,
                   @Body editProfileRequest: EditProfileRequest):Observable<SessionResponse>
}