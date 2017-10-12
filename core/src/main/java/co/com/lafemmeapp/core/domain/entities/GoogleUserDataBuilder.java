package co.com.lafemmeapp.core.domain.entities;

import android.net.Uri;

public class GoogleUserDataBuilder {
    private String email;
    private String givenName;
    private String familyName;
    private String displayName;
    private Uri photoUrl;

    public GoogleUserDataBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public GoogleUserDataBuilder setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public GoogleUserDataBuilder setFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public GoogleUserDataBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public GoogleUserDataBuilder setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public GoogleUserData createGoogleUserData() {
        return new GoogleUserData(email, givenName, familyName, displayName, photoUrl);
    }
}