package co.com.lafemmeapp.core.domain.mappers;


import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.entities.CustomerBuilder;
import co.com.lafemmeapp.dataprovider.network.entities.SessionResponse;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 4/18/17.
 */

public class SessionResponseCustomerMapper implements Function<SessionResponse, Customer> {

    private final static SessionResponseCustomerMapper instace = new SessionResponseCustomerMapper();

    private SessionResponseCustomerMapper() {

    }

    public static SessionResponseCustomerMapper getInstace() {
        return instace;
    }

    @Override
    public Customer apply(SessionResponse sessionResponse) throws Exception {
        return new CustomerBuilder().setUuid(sessionResponse.getUuid())
                .setAddress(sessionResponse.getAddress())
                .setName(sessionResponse.getName())
                .setPhoneNumber(sessionResponse.getPhoneNumber())
                .setEmail(sessionResponse.getEmail())
                .setLastName(sessionResponse.getLastName())
                .setCity(sessionResponse.getCity())
                .createCustomer();
    }


}
