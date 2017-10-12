package co.com.lafemmeapp.core.domain.entities;

import java.util.List;

public class SpecialistUserBuilder {
    private String name;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String uuid;
    private String avatar;
    private String dni;
    private List<String> validStartDateTimes;
    private boolean SOS;
    private String city;

    public SpecialistUserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SpecialistUserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SpecialistUserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public SpecialistUserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public SpecialistUserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public SpecialistUserBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public SpecialistUserBuilder setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public SpecialistUserBuilder setDNI(String dni) {
        this.dni = dni;
        return this;
    }

    public SpecialistUserBuilder setValidStartDateTimes(List<String> validStartDateTimes) {
        this.validStartDateTimes = validStartDateTimes;
        return this;
    }

    public SpecialistUserBuilder setSOS(boolean SOS) {
        this.SOS = SOS;
        return this;

    }

    public SpecialistUserBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public SpecialistUser createSpecialistUser() {
        return new SpecialistUser(name, lastName, address, email,
                phoneNumber, uuid, avatar, dni, validStartDateTimes,
                SOS, city);
    }
}