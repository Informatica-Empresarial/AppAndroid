package co.com.lafemmeapp.dataprovider.network;

import com.facebook.AccessToken;

import org.json.JSONObject;

import io.reactivex.Observable;

/**
 * Created by oscargallon on 4/27/17.
 */

public interface IGraphFacebookProvider {

    Observable<JSONObject> getFacebookUserProfileInfo(AccessToken token);

    Observable<Boolean> LogOutFacebookUser(final AccessToken accessToken);
}
