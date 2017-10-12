package co.com.lafemmeapp.dataprovider.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oscargallon on 4/18/17.
 */

public class SessionResponse {

    @SerializedName("firstName")
    private String name;

    private String lastName;

    private String address;

    private String email;

    private String phoneNumber;

    private String uuid;

    private String avatar;

    private String token;

    private List<String> roles;

    private boolean SOS;

    private String city;

    public SessionResponse(String name, String lastName, String address,
                           String email, String phoneNumber, String uuid, String avatar,
                           String token, List<String> roles, boolean SOS,
                           String city) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
        this.avatar = avatar;
        this.token = token;
        this.roles = roles;
        this.SOS = SOS;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isSOS() {
        return SOS;
    }

    public void setSOS(boolean SOS) {
        this.SOS = SOS;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
