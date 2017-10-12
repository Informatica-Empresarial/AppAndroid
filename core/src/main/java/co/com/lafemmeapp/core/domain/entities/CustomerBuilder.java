package co.com.lafemmeapp.core.domain.entities;

public class CustomerBuilder {
    private String name;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String uuid;
    private String avatar;
    private String city;

    public CustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public CustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CustomerBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public CustomerBuilder setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public CustomerBuilder setCity(String city){
        this.city = city;
        return this;
    }



    public Customer createCustomer() {
        return new Customer(name, lastName, address, email, phoneNumber, uuid, avatar,city);
    }
}