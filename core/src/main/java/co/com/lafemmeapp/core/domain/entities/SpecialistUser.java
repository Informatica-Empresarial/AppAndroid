package co.com.lafemmeapp.core.domain.entities;

import android.os.Parcel;

import java.util.List;

import co.com.lafemmeapp.core.domain.entities.abstracts.User;

/**
 * Created by oscargallon on 5/8/17.
 */

public class SpecialistUser extends User {

    private final String DNI;

    private final List<String> validStartDateTimes;

    private final boolean SOS;

    public SpecialistUser(String name, String lastName, String address, String email,
                          String phoneNumber, String uuid, String avatar, String DNI,
                          List<String> validStartDateTimes,
                          boolean SOS, String city) {
        super(name, lastName, address, email, phoneNumber, uuid, avatar, city);
        this.DNI = DNI;
        this.validStartDateTimes = validStartDateTimes;
        this.SOS = SOS;
    }

    public String getDNI() {
        return DNI;
    }

    public List<String> getValidStartDateTimes() {
        return validStartDateTimes;
    }

    public boolean isSOS() {
        return SOS;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.DNI);
        dest.writeStringList(this.validStartDateTimes);
        dest.writeByte((byte) (SOS ? 1 : 0));
    }

    protected SpecialistUser(Parcel in) {
        super(in);
        this.DNI = in.readString();
        this.validStartDateTimes = in.createStringArrayList();
        this.SOS = in.readByte() != 0;

    }

    public static final Creator<SpecialistUser> CREATOR = new Creator<SpecialistUser>() {
        @Override
        public SpecialistUser createFromParcel(Parcel source) {
            return new SpecialistUser(source);
        }

        @Override
        public SpecialistUser[] newArray(int size) {
            return new SpecialistUser[size];
        }
    };
}
