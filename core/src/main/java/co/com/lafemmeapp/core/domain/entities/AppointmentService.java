package co.com.lafemmeapp.core.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oscargallon on 5/8/17.
 */

public class AppointmentService implements Parcelable {

    private final String appointmentUuid;

    private final String serviceUuid;

    private final int price;

    private final int count;

    private final Service service;

    public AppointmentService(String appointmentUuid, String serviceUuid, int price, int count, Service service) {
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

    public Service getService() {
        return service;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appointmentUuid);
        dest.writeString(this.serviceUuid);
        dest.writeInt(this.price);
        dest.writeInt(this.count);
        dest.writeParcelable(this.service, flags);
    }

    protected AppointmentService(Parcel in) {
        this.appointmentUuid = in.readString();
        this.serviceUuid = in.readString();
        this.price = in.readInt();
        this.count = in.readInt();
        this.service = in.readParcelable(Service.class.getClassLoader());
    }

    public static final Parcelable.Creator<AppointmentService> CREATOR = new Parcelable.Creator<AppointmentService>() {
        @Override
        public AppointmentService createFromParcel(Parcel source) {
            return new AppointmentService(source);
        }

        @Override
        public AppointmentService[] newArray(int size) {
            return new AppointmentService[size];
        }
    };
}
