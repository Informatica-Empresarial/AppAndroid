package co.com.lafemmeapp.core.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oscargallon on 5/8/17.
 */

public class AppointmentAvailability {

    private final String uuid;

    private final int totalPrice;

    private final int totalDuration;

    private final String status;

    private final List<SpecialistUser> availableSpecialists;

    @SerializedName("appointment_services")
    private final List<AppointmentService> appointmentServices;

    public AppointmentAvailability(String uuid, int totalPrice, int totalDuration,
                                   String status, List<SpecialistUser> availableSpecialists,
                                   List<AppointmentService> appointmentServices) {
        this.uuid = uuid;
        this.totalPrice = totalPrice;
        this.totalDuration = totalDuration;
        this.status = status;
        this.availableSpecialists = availableSpecialists;
        this.appointmentServices = appointmentServices;
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

    public List<SpecialistUser> getAvailableSpecialists() {
        return availableSpecialists;
    }


}
