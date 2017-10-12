package co.com.lafemmeapp.core.domain.use_cases.customer;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import co.com.lafemmeapp.core.domain.entities.GoogleUserData;
import co.com.lafemmeapp.core.domain.entities.GoogleUserDataBuilder;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 4/20/17.
 * first argument what the method will return
 * second what the method receives
 */
public class GetInfoFromGoogleUserUseCase extends UseCase<GoogleUserData, GoogleSignInResult> {
    public GetInfoFromGoogleUserUseCase(Scheduler subscribeOnScheduler, Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @Override
    public Observable<GoogleUserData> buildUseCaseObservable(final GoogleSignInResult googleSignInResult) {
        return Observable.create(new ObservableOnSubscribe<GoogleUserData>() {
            @Override
            public void subscribe(ObservableEmitter<GoogleUserData> emitter) throws Exception {

                if (emitter != null) {
                    emitter.onNext(new GoogleUserDataBuilder()
                            .setDisplayName(googleSignInResult.getSignInAccount()
                                    .getDisplayName())
                            .setEmail(googleSignInResult.getSignInAccount()
                                    .getEmail())
                            .setFamilyName(googleSignInResult.getSignInAccount()
                                    .getFamilyName())
                            .setGivenName(googleSignInResult.getSignInAccount()
                                    .getGivenName())
                            .setPhotoUrl(googleSignInResult.getSignInAccount()
                                    .getPhotoUrl())
                            .createGoogleUserData());
                    emitter.onComplete();
                }
            }
        });
    }
}
