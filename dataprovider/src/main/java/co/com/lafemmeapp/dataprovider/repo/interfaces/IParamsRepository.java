package co.com.lafemmeapp.dataprovider.repo.interfaces;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import co.com.lafemmeapp.dataprovider.network.entities.APISpecialistUser;
import co.com.lafemmeapp.dataprovider.network.entities.UpdateSpecialistLocationRequest;
import io.reactivex.Observable;

/**
 * Created by Stephys on 7/05/17.
 */

public interface IParamsRepository {

    Observable<List<APICity>> getCities();

    Observable<List<APICity>> getCitiesFromDB();

    Observable<List<APICity>> saveCities(final List<APICity> apiCities);

    Observable<Boolean> saveHasWatchOnboarding(Boolean b);

    Observable<Boolean> getHasWatchOnboarding();

    Observable<APISpecialistUser> updateSpecialistUserLocation(String token,
                                                               String uuid,
                                                               UpdateSpecialistLocationRequest
                                                                       updateSpecialistLocationRequest);

    Observable<APISpecialistUser> toggleSOS(String token,
                                            String uuid,
                                            UpdateSpecialistLocationRequest
                                                    updateSpecialistLocationRequest);

    Observable<APISpecialistUser> turnOffSOS(String token,
                                             String uuid);


}
