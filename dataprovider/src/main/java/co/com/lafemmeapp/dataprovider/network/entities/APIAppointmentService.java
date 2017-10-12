package co.com.lafemmeapp.dataprovider.network.entities;

/**
 * Created by oscargallon on 5/8/17.
 */

public class APIAppointmentService {

    private final String appointmentUuid;

    private final String serviceUuid;

    private final int price;

    private final int count;

    private final APIServices service;

    public APIAppointmentService(String appointmentUuid, String serviceUuid,
                                 int price, int count, APIServices service) {
        this.appointmentUuid = appointmentUuid;
        this.serviceUuid = serviceUuid;
        this.price = price;
        this.count = count;
        this.service = service;
    }

    public String getAppointmentUuid() {
        return appointmentUuid;
    }

    public String getServiceUuid() {
        return serviceUuid;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public APIServices getService() {
        return service;
    }
}
