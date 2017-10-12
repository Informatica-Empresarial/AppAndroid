package co.com.lafemmeapp.core.domain.use_cases.system

import co.com.lafemmeapp.core.domain.entities.City
import co.com.lafemmeapp.core.domain.mappers.APICItyCityMapper
import co.com.lafemmeapp.core.domain.use_cases.UseCase
import co.com.lafemmeapp.dataprovider.network.entities.APICity
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Created by oscargallon on 7/10/17.
 */
class GetSelectableCitiesUseCase(mSubscribeOnScheduler: Scheduler,
                                 mObserverOnScheduler: Scheduler) :
        UseCase<List<City>, Void?>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mRepositoryFactory: IRepositoryFactory

    override fun buildUseCaseObservable(params: Void?): Observable<List<City>> {
        return mRepositoryFactory
                .iParamsRepository.cities
                .flatMap({ apiCities ->
                    mRepositoryFactory
                            .iParamsRepository.saveCities(apiCities)
                }).map { apiCities ->
            apiCities.filter { apiCity ->
                apiCity.isSelectable
            }
        }.map { apiCities ->

            apiCities.map { apiCity ->
                APICItyCityMapper.instance
                        .apply(apiCity)

            }
        }
    }
}