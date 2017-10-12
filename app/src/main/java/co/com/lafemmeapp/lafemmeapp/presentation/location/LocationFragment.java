package co.com.lafemmeapp.lafemmeapp.presentation.location;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest;
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants;
import co.com.lafemmeapp.lafemmeapp.R;
import co.com.lafemmeapp.lafemmeapp.mappers.AddressTextViewValidatorMapper;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity;
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class LocationFragment extends BaseFragment implements ILocationFragmentView {

    private GoogleMap mGoogleMap;

    private EditText mETAddress;

    private Button mBTNNext;

    private Marker mMarker;

    private ILocationFragmentPresenter mLocationFragmentPresenter;

    private ImageView mIVGetLocation;

    private EditText mETIndications;


    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance(ViewServicesRequest viewServicesRequest) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY, viewServicesRequest);
        fragment.setArguments(args);
        return fragment;
    }

    public static LocationFragment newInstance() {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationFragmentPresenter.onCreate(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mLocationFragmentPresenter != null) {
            mLocationFragmentPresenter.onCreateView(view);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLocationFragmentPresenter = new LocationFragmentPresenter(this);
        mLocationFragmentPresenter.onAttach((IFragmentCallbacks) activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocationFragmentPresenter = new LocationFragmentPresenter(this);
        mLocationFragmentPresenter.onAttach((IFragmentCallbacks) context);
    }

    @Override
    public MapFragment getMapFragment() {
        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, mapFragment);
        fragmentTransaction.commit();
        return mapFragment;
    }

    @Override
    public void showAddress(String address) {
        if (mETAddress != null) {
            mETAddress.setText(address);
        }
    }


    @Override
    public void initViewComponents(View view) {
        mETAddress = (EditText) view.findViewById(R.id.et_address);
        mBTNNext = (Button) view.findViewById(R.id.btn_next_location);
        mIVGetLocation = (ImageView) view.findViewById(R.id.iv_get_location);
        mETIndications = (EditText) view.findViewById(R.id.et_indications);

        mIVGetLocation.setOnClickListener(v -> {
            if (mLocationFragmentPresenter != null) {
                mLocationFragmentPresenter.getLocation(getActivity());
            }
        });

        mETAddress.setOnClickListener(v -> {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .build();
            try {
                Intent intent = new PlaceAutocomplete
                        .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .setFilter(typeFilter)
                        .build(getActivity());

                startActivityForResult(intent, 301);
            } catch (GooglePlayServicesRepairableException |
                    GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

        });


        mBTNNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLocationFragmentPresenter != null
                        && mMarker != null) {
                    mLocationFragmentPresenter
                            .checkLocationAndContinueWithServiceSchedule(mMarker.getPosition(),
                                    mETAddress.getText().toString(),
                                    mETIndications.getText().toString(),
                                    getActivity(),
                                    v);
                }
            }
        });


    }

    @Override
    public void addMarker(LatLng latLng) {
        if (latLng != null && mGoogleMap != null) {
            if (!mGoogleMap.getCameraPosition().target.equals(latLng)) {
                final CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(16)
                        .build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            if (mMarker == null) {
                Drawable drawable =
                        getResources().getDrawable(R.drawable.ic_pin);


                mMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.marker_snippet))
                        .draggable(true));
                Bitmap bitmap =
                        mLocationFragmentPresenter.getMarkerIcon(getResources());
                if (bitmap != null) {
                    mMarker.setIcon(BitmapDescriptorFactory
                            .fromBitmap(bitmap));
                    bitmap.recycle();
                    bitmap = null;
                }

            } else {
                mMarker.setPosition(latLng);
            }


        }
    }

    @Override
    public void setMap(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnCameraMoveListener(this);
        Handler h
                = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLocationFragmentPresenter != null) {
                    mLocationFragmentPresenter.getLocation(getActivity());
                }
            }
        }, 1000);

    }

    @Override
    public void onLocationPermissionsGranted() {
        if (mLocationFragmentPresenter != null) {
            mLocationFragmentPresenter.showGPSDialog(getActivity());
        }
    }

    @Override
    public void onGPSEnabled() {
        if (mLocationFragmentPresenter != null) {
            mLocationFragmentPresenter.loadMap(getActivity());
        }
    }

    @Override
    public void cleanAddress() {
        if (mETAddress != null) {
            mETAddress.setText("");
        }
    }

    @Override
    public void changeBTNNextAvailability(boolean enabled) {
        if (mBTNNext != null) {
            mBTNNext.setEnabled(enabled);
            mBTNNext.setBackground(enabled ?
                    getResources().getDrawable(R.drawable.main_button_rounded_background) :
                    getResources().getDrawable(R.drawable.main_button_disable_rounded_background));
        }

    }

    @Override
    public void changeETIndicationsVisibility(int visibility) {
        if (mETIndications != null) {
            mETIndications.setVisibility(visibility);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mLocationFragmentPresenter != null) {
            mLocationFragmentPresenter.onStart();
        }
    }

    @Override
    public void initComponents() {
        mETAddress.setFocusable(false);
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
        } else {
            if (mLocationFragmentPresenter != null) {
                mLocationFragmentPresenter.showGPSDialog(getActivity());
            }
        }

        if (mLocationFragmentPresenter != null) {
            mLocationFragmentPresenter.subscribeTextViewToTextWatcherEvent(mETAddress,
                    AddressTextViewValidatorMapper.getInstance());
        }


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
    public void onCameraIdle() {
        if (mGoogleMap != null && mLocationFragmentPresenter != null) {
            addMarker(mGoogleMap.getCameraPosition()
                    .target);
            if (mMarker != null) {
                mMarker.showInfoWindow();
            }

            mLocationFragmentPresenter
                    .getAddressFromLatLng(getActivity(),
                            mGoogleMap.getCameraPosition().target);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationFragmentPresenter != null) {
            mLocationFragmentPresenter.onDestroy();
        }
    }

    @Override
    public void onCameraMove() {
        if (mGoogleMap != null) {
            if (mMarker != null) {
                mMarker.hideInfoWindow();
            }
            addMarker(mGoogleMap.getCameraPosition()
                    .target);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mLocationFragmentPresenter != null) {
            mLocationFragmentPresenter.onRequestPermissionsResult(requestCode,
                    permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);

                if (place != null && place.getAddress() != null) {
                    String placeDetailsStr = place.getAddress().toString();

                    mETAddress.setText(placeDetailsStr);

                    if (place.getLatLng() != null) {
                        mLocationFragmentPresenter.getAddressFromLatLng(getActivity(), place.getLatLng());
                        addMarker(place.getLatLng());
                    } else {
                        mLocationFragmentPresenter
                                .getLatLngFromAddress(getActivity(),
                                        mETAddress.getText().toString());
                    }
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("PLACES", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("PLACES", "Canceled");
            }
        } else {
            if (mLocationFragmentPresenter != null) {
                mLocationFragmentPresenter.onActivityResult(requestCode,
                        resultCode, data);
            }
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
