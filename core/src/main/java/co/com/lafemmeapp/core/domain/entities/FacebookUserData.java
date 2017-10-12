package co.com.lafemmeapp.core.domain.entities;

/**
 * Created by oscar.gallon on 4/21/17.
 */

public class FacebookUserData {

    private final String idFacebook;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String gender;
    private final String birthday;
    private final String location;
    private final String profilePic;

    public FacebookUserData(String id, String idFacebook, String firstName, String lastName, String email, String gender, String birthday, String location, String profilePic) {
        this.idFacebook = idFacebook;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.location = location;
        this.profilePic = profilePic;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return location;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getIdFacebook() {
        return idFacebook;
    }
}
