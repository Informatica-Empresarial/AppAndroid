package co.com.lafemmeapp.core.domain.mappers;

import java.util.ArrayList;
import java.util.List;

import co.com.lafemmeapp.core.domain.entities.Service;
import co.com.lafemmeapp.core.domain.entities.ServiceAvailability;
import co.com.lafemmeapp.core.domain.entities.ServiceAvailabilityBuilder;
import co.com.lafemmeapp.core.domain.entities.ServiceBuilder;
import co.com.lafemmeapp.dataprovider.network.entities.APIServiceAvailability;
import co.com.lafemmeapp.dataprovider.network.entities.APIServices;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/2/17.
 */

public class APIServiceListServiceListMapper implements Function<List<APIServices>,
        List<Service>> {

    private final static APIServiceListServiceListMapper instance = new APIServiceListServiceListMapper();

    private APIServiceListServiceListMapper() {

    }

    public static APIServiceListServiceListMapper getInstance() {
        return instance;
    }

    @Override
    public List<Service> apply(List<APIServices> apiServicesList) throws Exception {
        List<Service> serviceList = new ArrayList<>();
        for (APIServices apiServices : apiServicesList) {
            serviceList.add(APIServiceServiceMapper.getInstance()
                    .apply(apiServices));

        }
        return serviceList;
    }
}
