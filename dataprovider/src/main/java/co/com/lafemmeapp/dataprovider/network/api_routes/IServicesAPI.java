package co.com.lafemmeapp.dataprovider.network.api_routes;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.APIServices;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by oscargallon on 5/2/17.
 */

public interface IServicesAPI {

    @GET("services")
    Observable<List<APIServices>> getServices(@Header("Authorization") String token,
                                              @Query("enabled") boolean enabled,
                                              @Query("currency") String currency,
                                              @Query("lat") double lat,
                                              @Query("lng") double lng);
}
