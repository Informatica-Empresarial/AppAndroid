package co.com.lafemmeapp.lafemmeapp.presentation.brand;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator;
import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrandFragment extends BaseFragment implements IBrandFragmentView {


    public static BrandFragment newInstance() {
        return new BrandFragment();
    }

    private IBrandFragmentPresenter mBrandFragmentPresenter;

    private Button mBTNSchedule;

    private Button mBTNSOS;

    private TextView mTVTitle;


    public BrandFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBrandFragmentPresenter != null) {
            mBrandFragmentPresenter.onCreate(getArguments());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        if (mBrandFragmentPresenter != null) {
            mBrandFragmentPresenter.onCreateView(view);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBrandFragmentPresenter != null) {
            mBrandFragmentPresenter.onDestroy();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IFragmentCallbacks) {
            mBrandFragmentPresenter = new BrandFragmentPresenter(this);
            mBrandFragmentPresenter.onAttach((IFragmentCallbacks) activity);
        } else {
            throw new RuntimeException("You must implement IFragmentCallbacks");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentCallbacks) {
            mBrandFragmentPresenter = new BrandFragmentPresenter(this);
            mBrandFragmentPresenter.onAttach((IFragmentCallbacks) context);
        } else {
            throw new RuntimeException("You must implement IFragmentCallbacks");
        }
    }


    @Override
    public void initViewComponents(View view) {
        mBTNSchedule = (Button) view.findViewById(R.id.btn_schedule);
        mBTNSOS = (Button) view.findViewById(R.id.btn_sos);
        mTVTitle = (TextView) view.findViewById(R.id.tv_title);
        super.hideKeyboard(view, getActivity().getApplicationContext());

    }

    @Override
    public void initComponents() {
        mBTNSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBrandFragmentPresenter != null) {
                    mBrandFragmentPresenter.onViewClicked(v, null);
                }
            }
        });

        mBTNSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBrandFragmentPresenter != null) {
                    mBrandFragmentPresenter.onViewClicked(v, null);
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
    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (FragmentCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    || FragmentCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                FragmentCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        AppModuleConstants.LOCATION_PERMISSION_RESULT);

            } else {
                FragmentCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        AppModuleConstants.LOCATION_PERMISSION_RESULT);
            }
        } else if (mBrandFragmentPresenter != null) {
            mBrandFragmentPresenter.showGPSDialog(getActivity());
        }
    }

    @Override
    public void setViewsVisibility(Boolean hide) {
        if (hide) {
            mBTNSchedule.setVisibility(View.GONE);
            mTVTitle.setVisibility(View.GONE);
        } else {
            mBTNSchedule.setVisibility(View.VISIBLE);
            mTVTitle.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public View getBTNSchedule() {
        return mBTNSchedule;
    }


    @Override
    public void populateView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mBrandFragmentPresenter != null) {
            mBrandFragmentPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mBrandFragmentPresenter != null) {
            mBrandFragmentPresenter.onRequestPermissionsResult(requestCode,
                    permissions, grantResults);
        }
    }

    @Override
    public void onLocationPermissionsGranted() {

    }

    @Override
    public void onGPSEnabled() {
        if (mBrandFragmentPresenter != null) {
            mBrandFragmentPresenter.getLocation(getActivity());
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
