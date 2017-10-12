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
 * Created by oscargallon on 5/24/17.
 */

public class APIServiceServiceMapper implements Function<APIServices, Service> {
    private static final APIServiceServiceMapper ourInstance = new APIServiceServiceMapper();

    public static APIServiceServiceMapper getInstance() {
        return ourInstance;
    }

    private APIServiceServiceMapper() {
    }

    @Override
    public Service apply(APIServices apiServices) throws Exception {
        List<ServiceAvailability> serviceAvailabilityList = new ArrayList<>();
        if(apiServices.getServiceAvailabilities()!=null){
            for (APIServiceAvailability apiServiceAvailability :
                    apiServices.getServiceAvailabilities()) {
                serviceAvailabilityList.add(new ServiceAvailabilityBuilder()
                        .setEnabled(apiServiceAvailability.isEnabled())
                        .setLat(apiServiceAvailability.getLat())
                        .setLng(apiServiceAvailability.getLng())
                        .setRadius(apiServiceAvailability.getRadius())
                        .setUuid(apiServiceAvailability.getUuid())
                        .createServiceAvailability());
            }
        }


        return new ServiceBuilder()
                .setUuid(apiServices.getUuid())
                .setEnabled(apiServices.isEnabled())
                .setAlias(apiServices.getAlias())
                .setCurrency(apiServices.getCurrency())
                .setDescription(apiServices.getDescription())
                .setImageUrl(apiServices.getImageUrl())
                .setInstruction(apiServices.getInstruction())
                .setItems(apiServices.getItems())
                .setMinutesDuration(apiServices.getMinutesDuration())
                .setName(apiServices.getName())
                .setPrice(apiServices.getPrice())
                .setMaxCount(apiServices.getMaxCount())
                .setServiceAvailabilities(serviceAvailabilityList)
                .setTags(apiServices.getTags()).createService();
    }
}
