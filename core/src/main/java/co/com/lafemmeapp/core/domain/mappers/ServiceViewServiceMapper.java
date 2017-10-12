package co.com.lafemmeapp.core.domain.mappers;

import java.util.ArrayList;
import java.util.List;

import co.com.lafemmeapp.core.domain.entities.Service;
import co.com.lafemmeapp.core.domain.entities.ViewService;
import co.com.lafemmeapp.core.domain.entities.ViewServiceBuilder;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/3/17.
 */

public class ServiceViewServiceMapper implements Function<List<Service>,
        List<ViewService>> {

    private final static ServiceViewServiceMapper instance =
            new ServiceViewServiceMapper();

    private ServiceViewServiceMapper() {

    }

    public static ServiceViewServiceMapper getInstance() {
        return instance;
    }

    @Override
    public List<ViewService> apply(List<Service> serviceList) throws Exception {

        List<ViewService> viewServiceList = new ArrayList<>();
        for (Service service : serviceList) {
            viewServiceList.add(new ViewServiceBuilder()
                    .setTags(service.getTags())
                    .setName(service.getName())
                    .setPrice(service.getPrice())
                    .setMinutesDuration(service.getMinutesDuration())
                    .setCurrency(service.getCurrency())
                    .setDescription(service.getDescription())
                    .setImageUrl(service.getImageUrl())
                    .setItems(service.getItems())
                    .setUuid(service.getUuid())
                    .setMaxCount(service.getMaxCount())
                    .createViewService());
        }
        return viewServiceList;
    }
}
