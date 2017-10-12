package co.com.lafemmeapp.core.domain.use_cases.system;

import android.content.res.Resources;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import co.com.lafemmeapp.core.domain.mappers.ApiCityStringListMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.local.DBDataSource;
import co.com.lafemmeapp.dataprovider.local.LocalUtils;
import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/13/17.
 */

public class GetLocalCitiesUseCase extends UseCase<List<String>,
        Resources> {


    public GetLocalCitiesUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<List<String>> buildUseCaseObservable(final Resources resources) {
        return Observable.create(new ObservableOnSubscribe<List<APICity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<APICity>> emitter) throws Exception {
                if (!DBDataSource.getInstance()
                        .exist(Constants.DBCITY)) {

                    emitter.onNext(LocalUtils.getCitiesFromDisk(resources));
                } else {
                    emitter.onNext(LocalUtils.getCitiesFromDB());
                }

                emitter.onComplete();

            }
        }).flatMap(new Function<List<APICity>, ObservableSource<List<APICity>>>() {
            @Override
            public ObservableSource<List<APICity>> apply(final List<APICity> apiCities) throws Exception {
                return Observable.create(new ObservableOnSubscribe<List<APICity>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<APICity>> emitter) throws Exception {
                        if (apiCities.size() != 0) {
                            DBDataSource.getInstance().put(Constants.DBCITY, new Gson()
                                    .toJson(apiCities));
                        }

                        emitter.onNext(apiCities);
                        emitter.onComplete();
                    }
                });
            }
        }).map(ApiCityStringListMapper.getInstance());
    }
}
