package co.com.lafemmeapp.core.domain.entities;

public class AppointmentServiceBuilder {
    private String appointmentUuid;
    private String serviceUuid;
    private int price;
    private int count;
    private Service service;

    public AppointmentServiceBuilder setAppointmentUuid(String appointmentUuid) {
        this.appointmentUuid = appointmentUuid;
        return this;
    }

    public AppointmentServiceBuilder setServiceUuid(String serviceUuid) {
        this.serviceUuid = serviceUuid;
        return this;
    }

    public AppointmentServiceBuilder setPrice(int price) {
        this.price = price;
        return this;
    }

    public AppointmentServiceBuilder setCount(int count) {
        this.count = count;
        return this;
    }

    public AppointmentServiceBuilder setService(Service service){
        this.service = service;
        return this;
    }

    public AppointmentService createAppointmentService() {
        return new AppointmentService(appointmentUuid, serviceUuid, price, count,service);
    }
}