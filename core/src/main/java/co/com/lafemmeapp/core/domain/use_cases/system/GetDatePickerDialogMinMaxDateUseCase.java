package co.com.lafemmeapp.core.domain.use_cases.system;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.DatePickerMinMaxDate;
import co.com.lafemmeapp.core.domain.mappers.StringDatePickerMinMaxDateMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.providers.utils.UtilsProvidersFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/9/17.
 */

public class GetDatePickerDialogMinMaxDateUseCase extends UseCase<DatePickerMinMaxDate, List<String>> {

    public GetDatePickerDialogMinMaxDateUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<DatePickerMinMaxDate> buildUseCaseObservable(final List<String> dates) {

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(UtilsProvidersFactory
                        .getInstance().getmDateProvider()
                        .getMaxDateFromDatesString(dates));
                emitter.onComplete();
            }
        }).map(StringDatePickerMinMaxDateMapper.getInstance())
                .flatMap(new Function<DatePickerMinMaxDate, ObservableSource<DatePickerMinMaxDate>>() {
                    @Override
                    public ObservableSource<DatePickerMinMaxDate> apply(final DatePickerMinMaxDate datePickerMinMaxDate) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<DatePickerMinMaxDate>() {
                            @Override
                            public void subscribe(ObservableEmitter<DatePickerMinMaxDate> emitter)
                                    throws Exception {
                                datePickerMinMaxDate.setSelectableDates(UtilsProvidersFactory
                                        .getInstance()
                                        .getmDateProvider()
                                        .getSelectableDates(dates));
                                emitter.onNext(datePickerMinMaxDate);
                                emitter.onComplete();

                            }
                        });
                    }
                });

    }
}
