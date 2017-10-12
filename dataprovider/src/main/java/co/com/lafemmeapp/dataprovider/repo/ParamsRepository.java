package co.com.lafemmeapp.dataprovider.repo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.local.DBDataSource;
import co.com.lafemmeapp.dataprovider.network.APIProvider;
import co.com.lafemmeapp.dataprovider.network.api_routes.IParamsAPI;
import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import co.com.lafemmeapp.dataprovider.network.entities.APISpecialistUser;
import co.com.lafemmeapp.dataprovider.network.entities.UpdateSpecialistLocationRequest;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IParamsRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Stephys on 7/05/17.
 */

public class ParamsRepository implements IParamsRepository {

    private IParamsAPI paramsAPI;


    public ParamsRepository() {
        paramsAPI = APIProvider.getInstance()
                .getRetrofit().create(IParamsAPI.class);
    }

    @Override
    public Observable<List<APICity>> getCities() {
        return paramsAPI.getCities();
    }

    @Override
    public Observable<List<APICity>> getCitiesFromDB() {
        return Observable.create(new ObservableOnSubscribe<List<APICity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<APICity>> emmiter) throws Exception {
                if (DBDataSource.getInstance().exist(Constants.DBCITY)) {
                    List<APICity> apiCities = new Gson().fromJson(DBDataSource.getInstance()
                            .get(Constants.DBCITY).toString(), new TypeToken<List<APICity>>() {
                    }.getType());
                    emmiter.onNext(apiCities);
                    emmiter.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<List<APICity>> saveCities(final List<APICity> apiCities) {
        return Observable.create(new ObservableOnSubscribe<List<APICity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<APICity>> emmiter) throws Exception {
                DBDataSource.getInstance()
                        .put(Constants.DBCITY, new Gson().toJson(apiCities));
                emmiter.onNext(apiCities);
                emmiter.onComplete();
            }
        });
    }

    @Override
    public Observable<Boolean> saveHasWatchOnboarding(final Boolean b) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emmiter) throws Exception {
                DBDataSource.getInstance()
                        .put(Constants.DB_HAS_WATCH_ONBOARDING, new Gson().toJson(b));
                emmiter.onNext(true);
                emmiter.onComplete();
            }
        });
    }

    @Override
    public Observable<Boolean> getHasWatchOnboarding() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emmiter) throws Exception {
                emmiter.onNext(DBDataSource.getInstance().exist(Constants.DB_HAS_WATCH_ONBOARDING));
                emmiter.onComplete();
            }
        });
    }

    @Override
    public Observable<APISpecialistUser> updateSpecialistUserLocation(String token,
                                                                      String uuid,
                                                                      UpdateSpecialistLocationRequest
                                                                              updateSpecialistLocationRequest) {
        return paramsAPI.updateSpecialistLocation(token,
                uuid,
                updateSpecialistLocationRequest);
    }

    @Override
    public Observable<APISpecialistUser> toggleSOS(String token, String uuid,
                                                   UpdateSpecialistLocationRequest
                                                           updateSpecialistLocationRequest) {
        return paramsAPI.toggleSOS(token, uuid,
                updateSpecialistLocationRequest);
    }

    @Override
    public Observable<APISpecialistUser> turnOffSOS(String token, String uuid) {
        return paramsAPI.turnOffSOS(token, uuid,
                new UpdateSpecialistLocationRequest(null));
    }
}
