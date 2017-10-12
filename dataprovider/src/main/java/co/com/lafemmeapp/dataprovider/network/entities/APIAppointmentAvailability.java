package co.com.lafemmeapp.dataprovider.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oscargallon on 5/8/17.
 */

public class APIAppointmentAvailability {

    private final String uuid;

    private final int totalPrice;

    private final int totalDuration;

    private final String status;

    private final List<APISpecialistUser> availableSpecialists;

    @SerializedName("appointment_services")
    private final List<APIAppointmentService> apiAppointmentServiceList;

    public APIAppointmentAvailability(String uuid, int totalPrice, int totalDuration,
                                      String status, List<APISpecialistUser> availableSpecialists,
                                      List<APIAppointmentService> apiAppointmentServiceList) {
        this.uuid = uuid;
        this.totalPrice = totalPrice;
        this.totalDuration = totalDuration;
        this.status = status;
        this.availableSpecialists = availableSpecialists;
        this.apiAppointmentServiceList = apiAppointmentServiceList;
    }

    public String getUuid() {
        return uuid;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public String getStatus() {
        return status;
    }

    public List<APISpecialistUser> getAvailableSpecialists() {
        return availableSpecialists;
    }

    public List<APIAppointmentService> getApiAppointmentServiceList() {
        return apiAppointmentServiceList;
    }
}
