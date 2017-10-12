package co.com.lafemmeapp.core.domain.use_cases.customer;

import javax.inject.Inject;

import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.entities.abstracts.User;
import co.com.lafemmeapp.core.domain.mappers.DBSessionResponseSessionResponseMapper;
import co.com.lafemmeapp.core.domain.mappers.SessionResponseCustomerMapper;
import co.com.lafemmeapp.core.domain.mappers.SessionResponseDBSessionResponseMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.core.domain.use_cases.UseCaseFactory;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.local.entities.DBSessionRessponse;
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import co.com.lafemmeapp.dataprovider.params.LoginParams;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 4/18/17.
 */

public class LoginUseCase extends UseCase<SessionResponse, LoginParams> {

    @Inject
    IRepositoryFactory mRepositoryFactory;

    public LoginUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);

    }

    @Override
    public Observable<SessionResponse> buildUseCaseObservable(LoginParams loginParams) {
        return mRepositoryFactory
                .getSessionRepositoty().login(new LoginRequest(loginParams.getEmail(),
                        loginParams.getPassword()))
                .flatMap(new Function<SessionResponse, ObservableSource<DBSessionRessponse>>() {
                    @Override
                    public ObservableSource<DBSessionRessponse> apply(SessionResponse sessionResponse)
                            throws Exception {
                        return mRepositoryFactory
                                .getSessionRepositoty()
                                .storeSession(SessionResponseDBSessionResponseMapper
                                        .Companion.getInstance().apply(sessionResponse));


                    }
                }).map(DBSessionResponseSessionResponseMapper.Companion
                        .getInstance())
                .flatMap(new Function<SessionResponse, ObservableSource<SessionResponse>>() {
                    @Override
                    public ObservableSource<SessionResponse> apply(final SessionResponse sessionResponse) throws Exception {
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
                });

    }


}
