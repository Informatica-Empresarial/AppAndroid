package co.com.lafemmeapp.dataprovider.network.api_routes;

import co.com.lafemmeapp.dataprovider.network.entities.APICustomer;
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest;
import co.com.lafemmeapp.dataprovider.network.entities.RegisterRequest;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by oscargallon on 4/8/17.
 */

public interface ICustomerAPI {

    @GET("customer/{id}")
    Observable<APICustomer> getCustomer(@Path("id") String id);

    @POST("sessions")
    Observable<SessionResponse> login(@Body LoginRequest loginRequest);

    @POST("users/register")
    Observable<SessionResponse> createUser(@Body RegisterRequest registerRequest);


}
