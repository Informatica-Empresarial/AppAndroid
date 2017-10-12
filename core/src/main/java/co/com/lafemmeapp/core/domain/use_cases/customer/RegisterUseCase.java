package co.com.lafemmeapp.core.domain.use_cases.customer;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.mappers.DBSessionResponseSessionResponseMapper;
import co.com.lafemmeapp.core.domain.mappers.SessionResponseCustomerMapper;
import co.com.lafemmeapp.core.domain.mappers.SessionResponseDBSessionResponseMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.network.entities.RegisterRequestBuilder;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import co.com.lafemmeapp.dataprovider.params.RegisterParams;
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by stephany.berrio on 5/3/17.
 */

public class RegisterUseCase extends UseCase<Customer, RegisterParams> {

    @Inject
    IRepositoryFactory mRepositoryFactory;

    public RegisterUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<Customer> buildUseCaseObservable(RegisterParams registerParams) {
        return mRepositoryFactory
                .getCustomerRepository().createUser(new RegisterRequestBuilder()
                        .setLastName(registerParams.getLastName())
                        .setFirstName(registerParams.getFirstName())
                        .setAddress(registerParams.getAddress())
                        .setCity(registerParams.getCity())
                        .setEmail(registerParams.getEmail())
                        .setPhoneNumber(registerParams.getPhoneNumber())
                        .setPassword(registerParams.getPassword())
                        .createRegisterRequest())
                .flatMap(new Function<SessionResponse, ObservableSource<SessionResponse>>() {
                    @Override
                    public ObservableSource<SessionResponse> apply(final SessionResponse sessionResponse)
                            throws Exception {
                        return Observable.create(new ObservableOnSubscribe<SessionResponse>() {
                            @Override
                            public void subscribe(ObservableEmitter<SessionResponse> e) throws Exception {
                                mRepositoryFactory
                                        .getValuesRepository()
                                        .saveValue(Constants.DBTOKEN_KEY,
                                                sessionResponse.getToken());
                                e.onNext(sessionResponse);
                                e.onComplete();
                            }
                        });
                    }
                }).flatMap(new Function<SessionResponse, ObservableSource<SessionResponse>>() {
                    @Override
                    public ObservableSource<SessionResponse> apply(SessionResponse sessionResponse) throws Exception {
                        return mRepositoryFactory
                                .getSessionRepositoty()
                                .storeSession(SessionResponseDBSessionResponseMapper
                                        .Companion.getInstance()
                                        .apply(sessionResponse))
                                .map(DBSessionResponseSessionResponseMapper.Companion
                                        .getInstance());
                    }
                })
                .map(SessionResponseCustomerMapper.getInstace());
    }

}
