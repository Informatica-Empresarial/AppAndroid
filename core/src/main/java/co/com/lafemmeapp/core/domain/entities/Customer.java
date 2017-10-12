package co.com.lafemmeapp.core.domain.entities;

import android.os.Parcel;

import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.abstracts.User;

/**
 * Created by oscargallon on 4/8/17.
 */

public class Customer extends User {


    public Customer(String name, String lastName, String address, String email, String phoneNumber, String uuid,
                    String avatar, String city) {
        super(name, lastName, address, email, phoneNumber, uuid, avatar,
                city);
    }


    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "Customer %s with name %s and last name %s", getUuid(),
                getName(), getLastName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected Customer(Parcel in) {
        super(in);
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
