package co.com.lafemmeapp.core.domain.entities;

import android.net.Uri;

/**
 * Created by oscargallon on 4/20/17.
 */

public class GoogleUserData {

    private final String email;

    private final String givenName;

    private final String familyName;

    private final String displayName;

    private final Uri photoUrl;

    public GoogleUserData(String email, String givenName, String familyName, String displayName, Uri photoUrl) {
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
