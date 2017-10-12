package co.com.lafemmeapp.dataprovider.network.entities;

public class RegisterRequestBuilder {
    private String dni;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String password;
    private String city;
    private String accountId;

    public RegisterRequestBuilder setDNI(String dni) {
        this.dni = dni;
        return this;
    }

    public RegisterRequestBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public RegisterRequestBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RegisterRequestBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public RegisterRequestBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public RegisterRequestBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public RegisterRequestBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterRequestBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public RegisterRequestBuilder setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public RegisterRequest createRegisterRequest() {
        return new RegisterRequest(dni, firstName, lastName, address, email, phoneNumber, password, city, accountId);
    }
}