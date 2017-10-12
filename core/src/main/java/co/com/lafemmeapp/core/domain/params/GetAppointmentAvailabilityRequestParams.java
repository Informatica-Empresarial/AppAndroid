package co.com.lafemmeapp.core.domain.params;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.AppointmentServiceRequest;

/**
 * Created by oscargallon on 5/8/17.
 */

public class GetAppointmentAvailabilityRequestParams {

    private final String fromDateTime;

    private final String toDateTime;

    private final List<AppointmentServiceRequest> appointmentServiceRequest;

    private final double lat;

    private final double lng;

    public GetAppointmentAvailabilityRequestParams(String fromDateTime, String toDateTime,
                                                   List<AppointmentServiceRequest>
                                                           appointmentServiceRequest,
                                                   double lat, double lng) {
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.appointmentServiceRequest = appointmentServiceRequest;
        this.lat = lat;
        this.lng = lng;
    }

    public String getFromDateTime() {
        return fromDateTime;
    }

    public String getToDateTime() {
        return toDateTime;
    }

    public List<AppointmentServiceRequest> getAppointmentServiceRequest() {
        return appointmentServiceRequest;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
