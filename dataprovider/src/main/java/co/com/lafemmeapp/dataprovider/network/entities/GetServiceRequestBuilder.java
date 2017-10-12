package co.com.lafemmeapp.dataprovider.network.entities;

public class GetServiceRequestBuilder {
    private boolean enabled;
    private String currency;
    private double lat;
    private double lng;

    public GetServiceRequestBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public GetServiceRequestBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public GetServiceRequestBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public GetServiceRequestBuilder setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public GetServiceRequest createGetServiceRequest() {
        return new GetServiceRequest(enabled, currency, lat, lng);
    }
}