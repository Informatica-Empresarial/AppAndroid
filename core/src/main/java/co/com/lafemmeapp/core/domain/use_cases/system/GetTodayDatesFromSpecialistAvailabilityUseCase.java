package co.com.lafemmeapp.core.domain.use_cases.system;

import java.util.List;

import co.com.lafemmeapp.core.domain.mappers.DateStringListToTimeStringListMapper;
import co.com.lafemmeapp.core.domain.params.FilterSpecialistAvailabilityWithDateParam;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.utils.UtilsProvidersFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 5/9/17.
 */

public class GetTodayDatesFromSpecialistAvailabilityUseCase extends UseCase<List<String>, FilterSpecialistAvailabilityWithDateParam> {

    public GetTodayDatesFromSpecialistAvailabilityUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<List<String>> buildUseCaseObservable(final FilterSpecialistAvailabilityWithDateParam filterSpecialistAvailabilityWithDateParam) {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                emitter.onNext(UtilsProvidersFactory
                        .getInstance()
                        .getmDateProvider()
                        .filterDatesWithMonthAndDayFromStringDateList(filterSpecialistAvailabilityWithDateParam
                                .getDatesAvalaible(), filterSpecialistAvailabilityWithDateParam.getDate()));
                emitter.onComplete();
            }
        }).map(DateStringListToTimeStringListMapper.getInstance());
    }
}
