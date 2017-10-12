package co.com.lafemmeapp.dataprovider.network.api_routes;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import co.com.lafemmeapp.dataprovider.network.entities.APISpecialistUser;
import co.com.lafemmeapp.dataprovider.network.entities.UpdateSpecialistLocationRequest;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Stephys on 7/05/17.
 */

public interface IParamsAPI {

    @GET("city")
    Observable<List<APICity>> getCities();

    @PUT("users/{uuid}/lastKnownLocation")
    Observable<APISpecialistUser>
    updateSpecialistLocation(@Header("Authorization") String token,
                             @Path("uuid") String uuid,
                             @Body UpdateSpecialistLocationRequest updateSpecialistLocationRequest);


    @PUT("users/{uuid}/toggleSOS")
    Observable<APISpecialistUser>
    toggleSOS(@Header("Authorization") String token,
              @Path("uuid") String uuid,
              @Body UpdateSpecialistLocationRequest updateSpecialistLocationRequest);

    @PUT("users/{uuid}/SOS/off")
    Observable<APISpecialistUser>
    turnOffSOS(@Header("Authorization") String token,
               @Path("uuid") String uuid,
               @Body UpdateSpecialistLocationRequest updateSpecialistLocationRequest);

}
