package co.com.lafemmeapp.core.domain.mappers;

import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.List;

import co.com.lafemmeapp.core.domain.entities.AppointmentAvailability;
import co.com.lafemmeapp.core.domain.entities.AppointmentAvailabilityBuilder;
import co.com.lafemmeapp.core.domain.entities.AppointmentService;
import co.com.lafemmeapp.core.domain.entities.AppointmentServiceBuilder;
import co.com.lafemmeapp.core.domain.entities.Service;
import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.core.domain.entities.SpecialistUserBuilder;
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointmentAvailability;
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointmentService;
import co.com.lafemmeapp.dataprovider.network.entities.APISpecialistUser;
import co.com.lafemmeapp.dataprovider.network.entities.APIValidStartDateTimes;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/8/17.
 */

public class APIAppointmentAvailabilityAppointmentAvailabilityMapper
        implements Function<APIAppointmentAvailability, AppointmentAvailability> {

    private final static APIAppointmentAvailabilityAppointmentAvailabilityMapper
            instance = new APIAppointmentAvailabilityAppointmentAvailabilityMapper();

    private APIAppointmentAvailabilityAppointmentAvailabilityMapper() {

    }

    public static APIAppointmentAvailabilityAppointmentAvailabilityMapper getInstance() {
        return instance;
    }

    @Override
    public AppointmentAvailability apply(APIAppointmentAvailability apiAppointmentAvailability)
            throws Exception {


        List<AppointmentService> appointmentServiceList =
                new ArrayList<>();
        for (APIAppointmentService apiAppointmentService :
                apiAppointmentAvailability.getApiAppointmentServiceList()) {

            Service service = apiAppointmentService.getService() != null ?
                    APIServiceServiceMapper.getInstance()
                            .apply(apiAppointmentService.getService()) : null;

            appointmentServiceList.add(new AppointmentServiceBuilder()
                    .setAppointmentUuid(apiAppointmentService.getAppointmentUuid())
                    .setServiceUuid(apiAppointmentService.getServiceUuid())
                    .setCount(apiAppointmentService.getCount())
                    .setPrice(apiAppointmentService.getPrice())
                    .setService(service)
                    .createAppointmentService());
        }

        List<SpecialistUser> specialistUserList = new ArrayList<>();
        for (APISpecialistUser apiSpecialistUser : apiAppointmentAvailability.getAvailableSpecialists()) {
            specialistUserList
                    .add(APISpecialistUserSpecialistUser.getInstance()
                            .apply(apiSpecialistUser));
        }


        return new AppointmentAvailabilityBuilder()
                .setAppointmentServices(appointmentServiceList)
                .setAvailableSpecialists(specialistUserList)
                .setStatus(apiAppointmentAvailability.getStatus())
                .setTotalDuration(apiAppointmentAvailability.getTotalDuration())
                .setTotalPrice(apiAppointmentAvailability.getTotalDuration())
                .setUuid(apiAppointmentAvailability.getUuid())
                .createAppointmentService();
    }
}
