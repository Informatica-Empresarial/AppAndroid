package co.com.lafemmeapp.dataprovider.repo.interfaces;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.APIServices;
import co.com.lafemmeapp.dataprovider.network.entities.GetServiceRequest;
import io.reactivex.Observable;

/**
 * Created by oscargallon on 5/2/17.
 */

public interface IServicesRepository {

    Observable<List<APIServices>> getServices(String token, GetServiceRequest getServiceRequest);

    Observable<List<APIServices>> saveServices(List<APIServices> apiServicesList);
}
