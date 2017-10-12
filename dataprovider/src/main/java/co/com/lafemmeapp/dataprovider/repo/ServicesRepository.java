package co.com.lafemmeapp.dataprovider.repo;

import java.util.List;

import javax.inject.Inject;

import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.local.AppDatabase;
import co.com.lafemmeapp.dataprovider.local.DBDataSource;
import co.com.lafemmeapp.dataprovider.network.APIProvider;
import co.com.lafemmeapp.dataprovider.network.api_routes.IServicesAPI;
import co.com.lafemmeapp.dataprovider.network.entities.APIServices;
import co.com.lafemmeapp.dataprovider.network.entities.GetServiceRequest;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IServicesRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by oscargallon on 5/2/17.
 */

public class ServicesRepository implements IServicesRepository {


    private IServicesAPI servicesAPI;

    @Inject
    AppDatabase mAppDatabase;


    public ServicesRepository() {
        servicesAPI = APIProvider.getInstance()
                .getRetrofit().create(IServicesAPI.class);
    }

    @Override
    public Observable<List<APIServices>> getServices(String token, GetServiceRequest getServiceRequest) {
        return servicesAPI.getServices(token, getServiceRequest.isEnabled(),
                getServiceRequest.getCurrency(),
                getServiceRequest.getLat(),
                getServiceRequest.getLng());
    }

    @Override
    public Observable<List<APIServices>> saveServices(final List<APIServices> apiServicesList) {
        return Observable.create(new ObservableOnSubscribe<List<APIServices>>() {
            @Override
            public void subscribe(ObservableEmitter<List<APIServices>> emitter) throws Exception {
                for (APIServices apiServices : apiServicesList) {
                    DBDataSource.getInstance()
                            .put(Constants.DBSERVICE_KEY, apiServices.getUuid());
                }
                emitter.onNext(apiServicesList);
                emitter.onComplete();
            }
        });
    }
}
