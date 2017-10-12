package co.com.lafemmeapp.core.domain.entities.abstracts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oscargallon on 4/8/17.
 */

public class User implements Parcelable {

    private final String name;

    private final String lastName;

    private final String address;

    private final String email;

    private final String phoneNumber;

    private final String uuid;

    private final String avatar;

    private final String city;


    public User(String name, String lastName, String address, String email,
                String phoneNumber, String uuid, String avatar,
                String city) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
        this.avatar = avatar;
        this.city = city;
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAvatar() {
        return avatar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.lastName);
        dest.writeString(this.address);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.uuid);
        dest.writeString(this.avatar);
        dest.writeString(this.city);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.lastName = in.readString();
        this.address = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.uuid = in.readString();
        this.avatar = in.readString();
        this.city = in.readString();
    }


}
