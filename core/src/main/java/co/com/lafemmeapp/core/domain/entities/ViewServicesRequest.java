package co.com.lafemmeapp.core.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by oscargallon on 5/8/17.
 */

public class ViewServicesRequest implements Parcelable {

    private List<ViewService> mViewServicesSelected;

    private LatLng mLatLng;

    private String mAddress;

    private SpecialistUser mSpecialistUserSelected;

    private String mChooseDate;

    private boolean mSOS;

    private String mCity;

    public ViewServicesRequest(List<ViewService> mViewServicesSelected, LatLng mLatLng,
                               String mAddress, SpecialistUser mSpecialistUserSelected,
                               String chooseDate,
                               boolean SOS, String city) {
        this.mViewServicesSelected = mViewServicesSelected;
        this.mLatLng = mLatLng;
        this.mAddress = mAddress;
        this.mSpecialistUserSelected = mSpecialistUserSelected;
        this.mChooseDate = chooseDate;
        this.mSOS = SOS;
        this.mCity = city;
    }

    public List<ViewService> getmViewServicesSelected() {
        return mViewServicesSelected;
    }

    public void setmViewServicesSelected(List<ViewService> mViewServicesSelected) {
        this.mViewServicesSelected = mViewServicesSelected;
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public void setmLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public SpecialistUser getmSpecialistUserSelected() {
        return mSpecialistUserSelected;
    }

    public void setmSpecialistUserSelected(SpecialistUser mSpecialistUserSelected) {
        this.mSpecialistUserSelected = mSpecialistUserSelected;
    }

    public void setmChooseDate(String mChooseDate) {
        this.mChooseDate = mChooseDate;
    }

    public String getmChooseDate() {
        return mChooseDate;
    }

    public boolean ismSOS() {
        return mSOS;
    }

    public void setmSOS(boolean mSOS) {
        this.mSOS = mSOS;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mViewServicesSelected);
        dest.writeParcelable(this.mLatLng, flags);
        dest.writeString(this.mAddress);
        dest.writeParcelable(this.mSpecialistUserSelected, flags);
        dest.writeString(this.mChooseDate);
        dest.writeByte(this.mSOS ? (byte) 1 : (byte) 0);
        dest.writeString(this.mCity);
    }

    protected ViewServicesRequest(Parcel in) {
        this.mViewServicesSelected = in.createTypedArrayList(ViewService.CREATOR);
        this.mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        this.mAddress = in.readString();
        this.mSpecialistUserSelected = in.readParcelable(SpecialistUser.class.getClassLoader());
        this.mChooseDate = in.readString();
        this.mSOS = in.readByte() != 0;
        this.mCity = in.readString();
    }

    public static final Creator<ViewServicesRequest> CREATOR = new Creator<ViewServicesRequest>() {
        @Override
        public ViewServicesRequest createFromParcel(Parcel source) {
            return new ViewServicesRequest(source);
        }

        @Override
        public ViewServicesRequest[] newArray(int size) {
            return new ViewServicesRequest[size];
        }
    };
}
