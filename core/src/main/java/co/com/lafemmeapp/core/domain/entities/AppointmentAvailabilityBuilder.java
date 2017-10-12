package co.com.lafemmeapp.core.domain.entities;

import java.util.List;

public class AppointmentAvailabilityBuilder {
    private String uuid;
    private int totalPrice;
    private int totalDuration;
    private String status;
    private List<SpecialistUser> availableSpecialists;
    private List<AppointmentService> appointmentServices;

    public AppointmentAvailabilityBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public AppointmentAvailabilityBuilder setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public AppointmentAvailabilityBuilder setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
        return this;
    }

    public AppointmentAvailabilityBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public AppointmentAvailabilityBuilder setAvailableSpecialists(List<SpecialistUser> availableSpecialists) {
        this.availableSpecialists = availableSpecialists;
        return this;
    }

    public AppointmentAvailabilityBuilder setAppointmentServices(List<AppointmentService> appointmentServices) {
        this.appointmentServices = appointmentServices;
        return this;
    }

    public AppointmentAvailability createAppointmentService() {
        return new AppointmentAvailability(uuid, totalPrice, totalDuration, status,
                availableSpecialists, appointmentServices);
    }
}