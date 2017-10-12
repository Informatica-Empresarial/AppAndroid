package co.com.lafemmeapp.lafemmeapp.presentation.services;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.com.lafemmeapp.core.domain.entities.Service;
import co.com.lafemmeapp.core.domain.entities.ViewService;
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest;
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequestBuilder;
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.core.domain.use_cases.services.GetServicesUseCase;
import co.com.lafemmeapp.dataprovider.network.entities.GetServiceRequestBuilder;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.lafemmeapp.di.AppModule;
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.UtilModuleConstants;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oscargallon on 5/3/17.
 */

public class ServiceFragmentPresenter implements IServiceFragmentPresenter {

    private Location mLocation;

    private IFragmentCallbacks mFragmentCallbacks;

    private IServiceFragmentView mServiceFragmentView;

    private HashMap<String, ViewService> mViewServiceHashMap;

    private Observable<Integer> mServiceAmountCalculatorObservable;

    private CompositeDisposable mCompositeDisposable;

    private ViewServicesRequest mViewServiceRequest;

    private String mCity;

    public ServiceFragmentPresenter(IServiceFragmentView mServiceFragmentView) {
        this.mServiceFragmentView = mServiceFragmentView;
        mViewServiceHashMap = new HashMap<>();
        mCompositeDisposable = new CompositeDisposable();
        mServiceAmountCalculatorObservable =
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        int price = 0;
                        if (mViewServiceHashMap != null
                                && mViewServiceHashMap.size() > 0) {
                            for (Map.Entry<String, ViewService> entry : mViewServiceHashMap.entrySet()) {
                                price += Integer.valueOf(entry.getValue().getPrice())
                                        * entry.getValue().getQuantity();
                            }
                            emitter.onNext(price);
                            emitter.onComplete();
                        } else if (mViewServiceHashMap != null
                                && mViewServiceHashMap.size() == 0) {
                            emitter.onNext(0);
                            emitter.onComplete();
                        }
                    }
                });
    }


    private void calculateServicesAmountPrice() {
        if (mServiceAmountCalculatorObservable != null) {
            mServiceAmountCalculatorObservable
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<Integer>() {
                        @Override
                        public void onNext(Integer value) {
                            if (mServiceFragmentView != null) {
                                mServiceFragmentView.changeServicesAmountPrice(value);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mServiceFragmentView != null) {
                                mServiceFragmentView.changeServicesAmountPrice(0);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

    }


    @Override
    public void onCreate(Bundle arguments) {
        if (arguments != null) {

            if (arguments.containsKey(AppModuleConstants.LOCATION_KEY)) {
                mLocation = arguments.getParcelable(AppModuleConstants.LOCATION_KEY);
            }

            if (arguments.containsKey(AppModuleConstants.VIEW_SERVICE_REQUEST_KEY)) {
                mViewServiceRequest =
                        arguments.getParcelable(AppModuleConstants.VIEW_SERVICE_REQUEST_KEY);
            }

            if (arguments.containsKey(AppModuleConstants.CITIES_KEY)) {
                mCity = arguments.getString(AppModuleConstants.CITIES_KEY);
            }

        }
    }

    @Override
    public void onCreateView(View view) {
        if (mServiceFragmentView != null) {
            mServiceFragmentView.initViewComponents(view);
            mServiceFragmentView.initComponents();
            getServices();
        }
    }

    @Override
    public void onAttach(IFragmentCallbacks fragmentCallback) {
        mFragmentCallbacks = fragmentCallback;
    }

    @Override
    public void getServices() {
        if (mServiceFragmentView != null) {
            double lat = mLocation != null ? mLocation.getLatitude() : 0;
            double lng = mLocation != null ? mLocation.getLongitude() : 0;
            mServiceFragmentView.showProgressBar();
            IUseCaseIterator iterator =
                    LaFemmeApplication
                            .getInstance()
                            .getmUseCaseFactory()
                            .getServicesUseCase(Schedulers.io(), AndroidSchedulers.mainThread());

            iterator.execute(new DisposableObserver<List<ViewService>>() {
                @Override
                public void onNext(List<ViewService> value) {
                    if (mServiceFragmentView != null) {
                        mServiceFragmentView.showServices(value);
                        mServiceFragmentView.dismissProgressBar();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    if (mServiceFragmentView != null) {
                        mServiceFragmentView.showMessage("Error",
                                "Lo sentimos no encontramos especialistas",
                                UtilModuleConstants.SHOW_ERROR_MESSAGE_ACTION,
                                false);
                        mServiceFragmentView.dismissProgressBar();
                    }

                }

                @Override
                public void onComplete() {

                }
            }, new GetServiceRequestBuilder().setCurrency(AppModuleConstants.COP_CURRENCY_KEY)
                    .setEnabled(true)
                    .setLat(lat)
                    .setLng(lng)
                    .createGetServiceRequest());
        }

    }


    @Override
    public void onDestroy() {
        mServiceFragmentView = null;
        mCompositeDisposable.clear();
        mViewServiceHashMap = null;
        mFragmentCallbacks = null;
        mLocation = null;
        mServiceAmountCalculatorObservable = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void subscribeTextViewToTextWatcherEvent(@NonNull TextView textView,
                                                    @NonNull ITextViewValidatorMapper validatorMapper) {
        mCompositeDisposable.add(RxTextView.textChangeEvents(textView)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(validatorMapper)
                .subscribe(new Consumer<TextChangedMappedEvent>() {
                    @Override
                    public void accept(TextChangedMappedEvent textChangedMappedEvent) throws Exception {
                        onTextChangeMappedEvent(textChangedMappedEvent);
                    }
                }));
    }

    /**
     * This method will evaluate if the {@link TextChangedMappedEvent}
     * for a specific TextView was valid or not
     *
     * @param textChangedMappedEvent {@link TextChangedMappedEvent} that contains the {@link TextView} id
     *                               and if the textChangedMappedEvent was valid or not
     */
    @Override
    public void onTextChangeMappedEvent(TextChangedMappedEvent textChangedMappedEvent) {
        if (textChangedMappedEvent == null || mServiceFragmentView == null) {
            return;
        }

        switch (textChangedMappedEvent.getSourceId()) {
            case R.id.tv_price: {
                mServiceFragmentView.changeBTNNextAvailability(textChangedMappedEvent.isValid());
                break;
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onViewClicked(View view, Parcelable parcelable) {
        if (mFragmentCallbacks != null) {
            switch (view.getId()) {
                case R.id.btn_next: {
                    goToLocationScreen(view);
                    break;
                }
                default: {
                    mFragmentCallbacks.onViewClicked(view, parcelable);
                }
            }

        }
    }

    private void goToLocationScreen(final View view) {
        Observable.create(new ObservableOnSubscribe<List<ViewService>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ViewService>> emitter) throws Exception {
                emitter.onNext(new ArrayList<>(Collections2
                        .transform(mViewServiceHashMap.entrySet(),
                                new Function<Map.Entry<String, ViewService>, ViewService>() {
                                    @Override
                                    public ViewService apply(Map.Entry<String, ViewService> input) {
                                        return input.getValue();
                                    }
                                })));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ViewService>>() {
                    @Override
                    public void accept(List<ViewService> viewServiceList) throws Exception {

                        ViewServicesRequest viewServicesRequest =
                                mViewServiceRequest != null ?
                                        mViewServiceRequest :
                                        new ViewServicesRequestBuilder()
                                                .createViewServicesRequest();

                        if (mCity != null && viewServicesRequest.getmCity() == null) {
                            viewServicesRequest.setmCity(mCity);
                        }

                        viewServicesRequest.setmViewServicesSelected(viewServiceList);

                        if (mViewServiceRequest != null) {
                            viewServicesRequest.setmSOS(mViewServiceRequest.ismSOS());
                        }
                        mFragmentCallbacks.onViewClicked(view, viewServicesRequest);
                    }
                });


    }

    @Override
    public void onServiceQuantityChange(ViewService viewService) {
        if (mViewServiceHashMap != null && viewService != null) {
            if (viewService.getQuantity() > 0) {
                mViewServiceHashMap.put(viewService.getUuid(), viewService);
            } else {
                mViewServiceHashMap.remove(viewService.getUuid());
            }

            calculateServicesAmountPrice();
        }
    }
}
