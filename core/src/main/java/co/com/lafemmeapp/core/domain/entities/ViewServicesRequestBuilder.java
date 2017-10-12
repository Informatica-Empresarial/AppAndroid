package co.com.lafemmeapp.core.domain.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ViewServicesRequestBuilder {
    private List<ViewService> mViewServicesSelected;
    private LatLng mLatLng;
    private String mAddress;
    private SpecialistUser mSpecialistUserSelected;
    private String mChooseDate;
    private boolean mSOS;
    private String mCity;

    public ViewServicesRequestBuilder setmViewServicesSelected(List<ViewService> mViewServicesSelected) {
        this.mViewServicesSelected = mViewServicesSelected;
        return this;
    }

    public ViewServicesRequestBuilder setmLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
        return this;
    }

    public ViewServicesRequestBuilder setmAddress(String mAddress) {
        this.mAddress = mAddress;
        return this;
    }

    public ViewServicesRequestBuilder setSpecialistUserSelected(SpecialistUser specialistUser) {
        this.mSpecialistUserSelected = specialistUser;
        return this;
    }

    public ViewServicesRequestBuilder setChooseDate(String chooseDate) {
        this.mChooseDate = chooseDate;
        return this;
    }

    public ViewServicesRequestBuilder setSOS(boolean sos){
        this.mSOS = sos;
        return this;
    }

    public ViewServicesRequestBuilder setmCity(String city){
        this.mCity = city;
        return this;
    }

    public ViewServicesRequest createViewServicesRequest() {
        return new ViewServicesRequest(mViewServicesSelected, mLatLng, mAddress,
                mSpecialistUserSelected, mChooseDate, mSOS, mCity);
    }
}