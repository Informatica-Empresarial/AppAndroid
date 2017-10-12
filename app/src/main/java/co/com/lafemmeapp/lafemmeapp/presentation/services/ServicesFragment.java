package co.com.lafemmeapp.lafemmeapp.presentation.services;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.ViewService;
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.di.AppModule;
import co.com.lafemmeapp.lafemmeapp.mappers.ServicePriceTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment;
import co.com.lafemmeapp.lafemmeapp.presentation.utils.RecyclerViewDividerItemDecoration;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends BaseFragment implements IServiceFragmentView {


    private ProgressBar mPBServices;

    private RecyclerView mRVServices;

    private TextView mTVPrice;

    private Button mBTNNExt;

    private IServiceFragmentPresenter mServiceFragmentPresenter;

    private ServiceRecyclerViewAdapter mServiceRecyclerViewAdapter;


    public ServicesFragment() {

    }

    public static ServicesFragment newInstance(Location location, String city) {
        ServicesFragment servicesFragment =
                new ServicesFragment();

        if (location != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppModuleConstants.LOCATION_KEY, location);
            bundle.putString(AppModuleConstants.CITIES_KEY, city);
            servicesFragment.setArguments(bundle);

        }

        return servicesFragment;
    }

    public static ServicesFragment newInstance(ViewServicesRequest viewServicesRequest) {
        ServicesFragment servicesFragment =
                new ServicesFragment();


        if (viewServicesRequest != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppModuleConstants.VIEW_SERVICE_REQUEST_KEY, viewServicesRequest);
            servicesFragment.setArguments(bundle);
        }



        return servicesFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mServiceFragmentPresenter != null) {
            mServiceFragmentPresenter.onCreate(getArguments());
        }
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_services, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mServiceFragmentPresenter != null) {
            mServiceFragmentPresenter.onCreateView(view);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mServiceFragmentPresenter = new ServiceFragmentPresenter(this);
        mServiceFragmentPresenter.onAttach((IFragmentCallbacks) activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mServiceFragmentPresenter = new ServiceFragmentPresenter(this);
        mServiceFragmentPresenter.onAttach((IFragmentCallbacks) context);

    }

    @Override
    public void initViewComponents(View view) {
        super.hideKeyboard(view, getActivity().getApplicationContext());
        mRVServices = (RecyclerView) view.findViewById(R.id.rv_services);
        mPBServices = (ProgressBar) view.findViewById(R.id.pb_services);
        mTVPrice = (TextView) view.findViewById(R.id.tv_price);
        mBTNNExt = (Button) view.findViewById(R.id.btn_next);
        mRVServices.addItemDecoration(new RecyclerViewDividerItemDecoration(getResources()
                .getDrawable(R.drawable.recycler_view_divider)));
    }

    @Override
    public void initComponents() {
        mRVServices.setHasFixedSize(true);
        mRVServices.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mServiceFragmentPresenter != null) {
            mServiceFragmentPresenter.subscribeTextViewToTextWatcherEvent(mTVPrice,
                    ServicePriceTextViewValidatorMapper.getInstance());
        }
        mBTNNExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (mServiceFragmentPresenter != null) {
                    mServiceFragmentPresenter.onViewClicked(v, null);
                }
            }
        });
    }


    @Override
    public void showMessage(String title, String description, String action, boolean showBothButtons) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity())
                    .showMessage(title, description, action, showBothButtons);
        }
    }

    @Override
    public void populateView() {

    }

    @Override
    public void showServices(List<ViewService> serviceList) {
        if (mServiceFragmentPresenter != null) {
            mServiceRecyclerViewAdapter =
                    new ServiceRecyclerViewAdapter(serviceList, getActivity(),
                            mServiceFragmentPresenter);
            mRVServices.setAdapter(mServiceRecyclerViewAdapter);
        }

    }

    @Override
    public void showProgressBar() {
        if (mPBServices != null) {
            mPBServices.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissProgressBar() {
        if (mPBServices != null) {
            mPBServices.setVisibility(View.GONE);
        }
    }

    @Override
    public void changeServicesAmountPrice(int price) {
        if (mTVPrice != null) {
            mTVPrice.setText(String.format(Locale.getDefault(),
                    "$%,d", price));
        }
    }

    @Override
    public void changeBTNNextAvailability(boolean enabled) {
        if (mBTNNExt != null) {
            mBTNNExt.setEnabled(enabled);
            mBTNNExt.setBackground(enabled ?
                    getResources().getDrawable(R.drawable.main_button_rounded_background) :
                    getResources().getDrawable(R.drawable.main_button_disable_rounded_background));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mServiceFragmentPresenter != null) {
            mServiceFragmentPresenter.onDestroy();
        }
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }
}
