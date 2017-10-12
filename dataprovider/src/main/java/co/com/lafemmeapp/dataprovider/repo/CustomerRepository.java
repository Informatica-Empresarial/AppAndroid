package co.com.lafemmeapp.dataprovider.repo;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException;
import co.com.lafemmeapp.dataprovider.local.DBDataSource;
import co.com.lafemmeapp.dataprovider.network.APIProvider;
import co.com.lafemmeapp.dataprovider.network.api_routes.ICustomerAPI;
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest;
import co.com.lafemmeapp.dataprovider.network.entities.RegisterRequest;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import co.com.lafemmeapp.dataprovider.repo.interfaces.ICustomerRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by oscargallon on 4/8/17.
 */
public class CustomerRepository implements ICustomerRepository {


    private final ICustomerAPI customerAPI;

    public CustomerRepository() {
        customerAPI = APIProvider.getInstance()
                .getRetrofit().create(ICustomerAPI.class);
    }


    @SuppressWarnings("unchecked")
    @Override
    public Observable getCustomer(String id) {
        return customerAPI.getCustomer(id);


    }

    @Override
    public Observable<SessionResponse> login(LoginRequest loginRequest) {
        return customerAPI.login(loginRequest);
    }

    @Override
    public Observable<SessionResponse> saveToken(@NonNull final SessionResponse sessionResponse) {
        return Observable.create(new ObservableOnSubscribe<SessionResponse>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<SessionResponse> emitter) throws Exception {
                DBDataSource.getInstance()
                        .put(Constants.DBTOKEN_KEY, sessionResponse.getToken());
                emitter.onNext(sessionResponse);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<SessionResponse> createUser(RegisterRequest registerRequest) {
        return customerAPI.createUser(registerRequest);
    }

    public Observable<SessionResponse> storeSession(@NonNull final SessionResponse sessionResponse) {
        return Observable.create(new ObservableOnSubscribe<SessionResponse>() {
            @Override
            public void subscribe(ObservableEmitter<SessionResponse> emitter) throws Exception {
                DBDataSource.getInstance()
                        .put(Constants.DBSESSION, new Gson().toJson(sessionResponse));
                emitter.onNext(sessionResponse);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<SessionResponse> getSession() {
        return Observable.create(new ObservableOnSubscribe<SessionResponse>() {
            @Override
            public void subscribe(ObservableEmitter<SessionResponse> emitter) throws Exception {
                if (DBDataSource.getInstance()
                        .exist(Constants.DBSESSION)) {
                    SessionResponse sessionResponse =
                            new Gson().fromJson((String) DBDataSource.getInstance()
                                    .get(Constants.DBSESSION), SessionResponse.class);
                    emitter.onNext(sessionResponse);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NoSessionException());
                }

            }
        });
    }

    @Override
    public Observable<Boolean> deleteSession() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                if (DBDataSource.getInstance()
                        .exist(Constants.DBSESSION)) {
                    DBDataSource.getInstance()
                            .remove(Constants.DBSESSION);


                }

                if (DBDataSource.getInstance()
                        .exist(Constants.DB_HAS_WATCH_ONBOARDING)) {
                    DBDataSource.getInstance()
                            .remove(Constants.DB_HAS_WATCH_ONBOARDING);
                }
                if (DBDataSource.getInstance()
                        .exist(Constants.DBTOKEN_KEY)) {
                    DBDataSource.getInstance()
                            .remove(Constants.DBTOKEN_KEY);
                }
                emitter.onNext(true);
                emitter.onComplete();
            }
        });
    }


}
