package co.com.lafemmeapp.core.domain.use_cases.system;

import java.util.List;

import co.com.lafemmeapp.core.domain.mappers.ApiCityStringListMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by Stephys on 7/05/17.
 */

public class GetCitiesFromDBUseCase extends UseCase<List<String>, Void> {


    private IRepositoryFactory mRepositoryFactory;


    public GetCitiesFromDBUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<List<String>> buildUseCaseObservable(Void aVoid) {
        return mRepositoryFactory
                .getIParamsRepository().getCities()
                .flatMap(new Function<List<APICity>, ObservableSource<List<APICity>>>() {
                    @Override
                    public ObservableSource<List<APICity>> apply(final List<APICity> apiCities) throws Exception {
                        return mRepositoryFactory
                                .getIParamsRepository().saveCities(apiCities);
                    }
                })
                .map(ApiCityStringListMapper.getInstance());
    }
}
