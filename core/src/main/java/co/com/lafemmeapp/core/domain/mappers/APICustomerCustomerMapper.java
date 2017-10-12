package co.com.lafemmeapp.core.domain.mappers;

import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.entities.CustomerBuilder;
import co.com.lafemmeapp.dataprovider.network.entities.APICustomer;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 4/8/17.
 */

public class APICustomerCustomerMapper implements Function<APICustomer, Customer> {

    private static final APICustomerCustomerMapper instance = new APICustomerCustomerMapper();

    private APICustomerCustomerMapper() {

    }

    public static APICustomerCustomerMapper getInstance() {
        return instance;
    }

    @Override
    public Customer apply(APICustomer apiCustomer) throws Exception {
        return new CustomerBuilder()
                .setName(apiCustomer.getName())
                .setLastName(apiCustomer.getLastName())
                .setAddress(apiCustomer.getAddress())
                .setEmail(apiCustomer.getEmail())
                .setAvatar(apiCustomer.getAvatar())
                .setPhoneNumber(apiCustomer.getPhoneNumber())
                .setUuid(apiCustomer.getUuid())
                .setCity(apiCustomer.getCity())
                .createCustomer();
    }
}
