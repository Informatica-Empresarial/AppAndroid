package co.com.lafemmeapp.dataprovider.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oscargallon on 5/8/17.
 */

public class APISpecialistUser {

    private final int id;

    @SerializedName("firstName")
    private final String name;

    private final String lastName;

    private final String address;

    private final String email;

    private final String phoneNumber;

    private final String uuid;

    private final String avatar;

    private final String createdAt;

    private final String updatedAt;

    private final String DNI;

    private final String city;

    private final List<APIValidStartDateTimes> validStartDateTimes;

    private final boolean SOS;

    public APISpecialistUser(int id, String name, String lastName, String address,
                             String email, String phoneNumber, String uuid,
                             String avatar, String createdAt, String updatedAt,
                             String DNI, String city, List<APIValidStartDateTimes> validStartDateTimes,
                             boolean SOS) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.DNI = DNI;
        this.city = city;
        this.validStartDateTimes = validStartDateTimes;
        this.SOS = SOS;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDNI() {
        return DNI;
    }

    public List<APIValidStartDateTimes> getValidStartDateTimes() {
        return validStartDateTimes;
    }

    public boolean isSOS() {
        return SOS;
    }

    public String getCity() {
        return city;
    }
}
