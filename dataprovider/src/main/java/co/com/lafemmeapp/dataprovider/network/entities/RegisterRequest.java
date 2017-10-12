package co.com.lafemmeapp.dataprovider.network.entities;

/**
 * Created by stephany.berrio on 5/3/17.
 */

public class RegisterRequest {


    private String DNI;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String phoneNumber;

    private String password;

    private String city;

    private String accountId;

    public RegisterRequest(String DNI, String firstName, String lastName, String address,
                           String email, String phoneNumber, String password, String city,
                           String accountId) {
        this.DNI = DNI;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.city = city;
        this.accountId = "00000000-0000-0000-0000-000000000000";
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
