package co.com.lafemmeapp.core.domain.use_cases.appointments;

import java.util.Locale;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.entities.AppointmentAvailability;
import co.com.lafemmeapp.core.domain.mappers.APIAppointmentAvailabilityAppointmentAvailabilityMapper;
import co.com.lafemmeapp.core.domain.params.GetAppointmentAvailabilityRequestParams;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.local.DBDataSource;
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointmentAvailability;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentAvailabilityRequest;
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/8/17.
 */

public class GetAppointmentAvailabilityUseCase extends UseCase<AppointmentAvailability,
        GetAppointmentAvailabilityRequestParams> {

    @Inject
    IRepositoryFactory mRepositoryFactory;


    public GetAppointmentAvailabilityUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<AppointmentAvailability>
    buildUseCaseObservable(final GetAppointmentAvailabilityRequestParams
                                   getAppointmentAvailabilityRequestParams) {
        return mRepositoryFactory
                .getValuesRepository()
                .getValue(Constants.DBTOKEN_KEY)
                .flatMap(new Function<String, ObservableSource<APIAppointmentAvailability>>() {
                    @Override
                    public ObservableSource<APIAppointmentAvailability> apply(String token) throws Exception {
                        return mRepositoryFactory
                                .getAppointmentRepository()
                                .getAppointmentAvailability(String.format(Locale.getDefault(),
                                        "%s %s", Constants.TOKEN_PREFIX,
                                        token),
                                        new AppointmentAvailabilityRequest(getAppointmentAvailabilityRequestParams
                                                .getLat(),
                                                getAppointmentAvailabilityRequestParams.getLng(),
                                                getAppointmentAvailabilityRequestParams
                                                        .getAppointmentServiceRequest(),
                                                getAppointmentAvailabilityRequestParams.getFromDateTime(),
                                                getAppointmentAvailabilityRequestParams.getToDateTime()));
                    }
                }).map(APIAppointmentAvailabilityAppointmentAvailabilityMapper.getInstance());

    }
}
