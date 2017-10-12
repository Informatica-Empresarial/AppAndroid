package co.com.lafemmeapp.dataprovider.network.entities;

import java.util.List;

/**
 * Created by oscargallon on 5/8/17.
 */

public class AppointmentAvailabilityRequest {

    private final double lat;

    private final double lng;

    private final List<AppointmentServiceRequest> appointmentServices;

    private final String fromDateTime;

    private final String toDateTime;

    public AppointmentAvailabilityRequest(double lat, double lng,
                                          List<AppointmentServiceRequest> appointmentServices,
                                          String fromDateTime, String toDateTime) {
        this.lat = lat;
        this.lng = lng;
        this.appointmentServices = appointmentServices;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public List<AppointmentServiceRequest> getAppointmentServices() {
        return appointmentServices;
    }
}
