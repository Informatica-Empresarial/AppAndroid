package co.com.lafemmeapp.core.domain.use_cases.services;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.entities.ViewService;
import co.com.lafemmeapp.core.domain.mappers.APIServiceListServiceListMapper;
import co.com.lafemmeapp.core.domain.mappers.ServiceViewServiceMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException;
import co.com.lafemmeapp.dataprovider.network.entities.APIServices;
import co.com.lafemmeapp.dataprovider.network.entities.GetServiceRequest;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/2/17.
 */

public class GetServicesUseCase extends UseCase<List<ViewService>, GetServiceRequest> {

    @Inject
    IRepositoryFactory mRepositoryFactory;

    public GetServicesUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }


    @Override
    public Observable<List<ViewService>> buildUseCaseObservable(final GetServiceRequest getServiceRequest) {
        return mRepositoryFactory
                .getValuesRepository()
                .getValue(Constants.DBTOKEN_KEY)
                .flatMap(new Function<String, ObservableSource<List<APIServices>>>() {
                    @Override
                    public ObservableSource<List<APIServices>> apply(String token) throws Exception {
                        if (token == null) {
                            throw new NoSessionException();
                        }
                        return mRepositoryFactory
                                .getServiceRepository()
                                .getServices(String.format(Locale.getDefault(),
                                        "%s %s", Constants.TOKEN_PREFIX,
                                        token),
                                        getServiceRequest);
                    }
                }).flatMap(new Function<List<APIServices>, ObservableSource<List<APIServices>>>() {
                    @Override
                    public ObservableSource<List<APIServices>> apply(List<APIServices> apiServicesList) throws Exception {
                        return mRepositoryFactory
                                .getServiceRepository()
                                .saveServices(apiServicesList);
                    }
                })
                .map(APIServiceListServiceListMapper.getInstance())
                .map(ServiceViewServiceMapper.getInstance());


    }
}
