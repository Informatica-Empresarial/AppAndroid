package co.com.lafemmeapp.dataprovider.repo.interfaces;


import android.support.annotation.NonNull;

import co.com.lafemmeapp.dataprovider.network.entities.APICustomer;
import co.com.lafemmeapp.dataprovider.network.entities.LoginRequest;
import co.com.lafemmeapp.dataprovider.network.entities.RegisterRequest;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import io.reactivex.Observable;

/**
 * Created by oscargallon on 4/8/17.
 */

public interface ICustomerRepository {

    Observable<APICustomer> getCustomer(String id);

    Observable<SessionResponse> login(LoginRequest loginRequest);

    Observable<SessionResponse> saveToken(SessionResponse sessionResponse);

    Observable<SessionResponse> createUser(RegisterRequest registerRequest);

    Observable<SessionResponse> storeSession(@NonNull final SessionResponse sessionResponse);

    Observable<SessionResponse> getSession();

    Observable<Boolean> deleteSession();
}
