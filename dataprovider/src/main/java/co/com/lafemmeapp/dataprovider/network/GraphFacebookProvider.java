package co.com.lafemmeapp.dataprovider.network;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by oscargallon on 4/27/17.
 */

public class GraphFacebookProvider implements IGraphFacebookProvider {

    @Override
    public Observable<JSONObject> getFacebookUserProfileInfo(final AccessToken token) {
        return Observable.create(new ObservableOnSubscribe<JSONObject>() {
            @Override
            public void subscribe(final ObservableEmitter<JSONObject> emitter) throws Exception {
                GraphRequest request = GraphRequest.newMeRequest(token,
                        new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        emitter.onNext(object);
                        emitter.onComplete();
                    }

                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, " +
                        "first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }
        });
    }

    public Observable<Boolean> LogOutFacebookUser(final AccessToken accessToken){
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null,
                        HttpMethod.DELETE, new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {

                        LoginManager.getInstance().logOut();
                        emitter.onNext(true);
                        emitter.onComplete();
                    }
                }).executeAsync();
            }
        });
    }


}
