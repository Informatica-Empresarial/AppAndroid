package co.com.lafemmeapp.dataprovider.network.entities;

/**
 * Created by oscargallon on 5/8/17.
 */

public class AppointmentServiceRequest {

    private final String serviceUuid;

    private final int count;

    public AppointmentServiceRequest(String serviceUuid, int count) {
        this.serviceUuid = serviceUuid;
        this.count = count;
    }

    public String getServiceUuid() {
        return serviceUuid;
    }

    public int getCount() {
        return count;
    }
}
