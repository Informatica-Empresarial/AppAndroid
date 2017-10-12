package co.com.lafemmeapp.core.domain.entities;

public class FacebookUserDataBuilder {
    private String id;
    private String idFacebook;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String birthday;
    private String location;
    private String profilePic;

    public FacebookUserDataBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public FacebookUserDataBuilder setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
        return this;
    }

    public FacebookUserDataBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public FacebookUserDataBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public FacebookUserDataBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public FacebookUserDataBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public FacebookUserDataBuilder setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public FacebookUserDataBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public FacebookUserDataBuilder setProfilePic(String profilePic) {
        this.profilePic = profilePic;
        return this;
    }

    public FacebookUserData createFacebookUserData() {
        return new FacebookUserData(id, idFacebook, firstName, lastName, email, gender, birthday, location, profilePic);
    }
}