package co.com.lafemmeapp.core.domain.entities;

public class ServiceAvailabilityBuilder {
    private String uuid;
    private boolean enabled;
    private double lat;
    private double lng;
    private int radius;

    public ServiceAvailabilityBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public ServiceAvailabilityBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public ServiceAvailabilityBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public ServiceAvailabilityBuilder setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public ServiceAvailabilityBuilder setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public ServiceAvailability createServiceAvailability() {
        return new ServiceAvailability(uuid, enabled, lat, lng, radius);
    }
}