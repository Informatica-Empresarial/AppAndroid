package co.com.lafemmeapp.core.domain.mappers;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import co.com.lafemmeapp.core.domain.entities.FacebookUserData;
import co.com.lafemmeapp.core.domain.entities.FacebookUserDataBuilder;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 4/27/17.
 */

public class JsonObjectFacebookUserDataMapper implements Function<JSONObject, FacebookUserData> {

    private final String FIRST_NAME_KEY = "first_name";
    private final String LAST_NAME_KEY = "last_name";
    private final String EMAIL_KEY = "email";
    private final String GENDER_KEY = "gender";
    private final String BIRTHDAY_KEY = "birthday";
    private final String LOCATION_KEY = "location";
    private final String LOCATION_NAME_KEY = "name";
    private final String ID_KEY = "id";

    private final static JsonObjectFacebookUserDataMapper instance =
            new JsonObjectFacebookUserDataMapper();

    private JsonObjectFacebookUserDataMapper() {

    }

    public static JsonObjectFacebookUserDataMapper getInstance() {
        return instance;
    }

    @Override
    public FacebookUserData apply(JSONObject jsonObject) throws Exception {
        FacebookUserDataBuilder facebookUserData = new FacebookUserDataBuilder();

        facebookUserData.setId(jsonObject.getString(ID_KEY));

        try {
            URL profile_pic = new URL("https://graph.facebook.com/" + jsonObject.getString(ID_KEY)
                    + "/picture?width=200&height=150");

            facebookUserData.setId(profile_pic.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        /**
         * optString will check if the key exists and if the key exists will
         * add get the value
         */
        facebookUserData.setFirstName(jsonObject.optString(FIRST_NAME_KEY));
        facebookUserData.setLastName(jsonObject.optString(LAST_NAME_KEY));
        facebookUserData.setEmail(jsonObject.optString(EMAIL_KEY));
        facebookUserData.setGender(jsonObject.optString(GENDER_KEY));
        facebookUserData.setBirthday(jsonObject.optString(BIRTHDAY_KEY));
        if (jsonObject.has(LOCATION_KEY)) {
            facebookUserData.setLocation(jsonObject.getJSONObject(LOCATION_KEY)
                    .getString(LOCATION_NAME_KEY));
        }


        return facebookUserData.createFacebookUserData();

    }
}
