package co.com.lafemmeapp.dataprovider.network.entities;

/**
 * Created by Stephys on 7/05/17.
 */

public class APICity {

    private final String name;
    private final String country;
    private final double lat;
    private final double lon;
    private final boolean selectable;

    public APICity(String name, String country, double lat, double lon, boolean selectable) {
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.selectable = selectable;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean isSelectable() {
        return selectable;
    }
}
