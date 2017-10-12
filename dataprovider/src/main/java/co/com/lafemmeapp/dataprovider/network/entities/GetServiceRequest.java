package co.com.lafemmeapp.dataprovider.network.entities;


/**
 * Created by oscargallon on 5/2/17.
 */
public class GetServiceRequest {

    private final boolean enabled;

    private final String currency;

    private final double lat;

    private final double lng;

    public GetServiceRequest(boolean enabled, String currency, double lat, double lng) {
        this.enabled = enabled;
        this.currency = currency;
        this.lat = lat;
        this.lng = lng;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getCurrency() {
        return currency;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
