package co.com.lafemmeapp.core.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oscargallon on 5/2/17.
 */

public class ServiceAvailability {

    private final String uuid;

    private final boolean enabled;

    private final double lat;

    private final double lng;

    private final int radius;

    public ServiceAvailability(String uuid, boolean enabled, double lat, double lng, int radius) {
        this.uuid = uuid;
        this.enabled = enabled;
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getRadius() {
        return radius;
    }

}
