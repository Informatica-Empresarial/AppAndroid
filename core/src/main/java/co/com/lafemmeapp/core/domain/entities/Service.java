package co.com.lafemmeapp.core.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by oscargallon on 5/2/17.
 */

public class Service implements Parcelable {

    private final String uuid;

    private final String alias;

    private final String name;

    private final String description;

    private final List<String> items;

    private final List<String> tags;

    private final String imageUrl;

    private final String instruction;

    private final String currency;

    private final String price;

    @SerializedName("minutes_duration")
    private final String minutesDuration;

    private final boolean enabled;

    private final List<ServiceAvailability> serviceAvailabilities;

    private final int maxCount;

    public Service(String uuid, String alias, String name, String description,
                   List<String> items, List<String> tags, String imageUrl,
                   String instruction, String currency, String price, String minutesDuration,
                   boolean enabled, List<ServiceAvailability> serviceAvailabilities, int maxCount) {
        this.uuid = uuid;
        this.alias = alias;
        this.name = name;
        this.description = description;
        this.items = items;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.instruction = instruction;
        this.currency = currency;
        this.price = price;
        this.minutesDuration = minutesDuration;
        this.enabled = enabled;
        this.serviceAvailabilities = serviceAvailabilities;
        this.maxCount = maxCount;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getItems() {
        return items;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }

    public String getMinutesDuration() {
        return minutesDuration;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<ServiceAvailability> getServiceAvailabilities() {
        return serviceAvailabilities;
    }

    public int getMaxCount() {
        return maxCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.alias);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeStringList(this.items);
        dest.writeStringList(this.tags);
        dest.writeString(this.imageUrl);
        dest.writeString(this.instruction);
        dest.writeString(this.currency);
        dest.writeString(this.price);
        dest.writeString(this.minutesDuration);
        dest.writeByte(this.enabled ? (byte) 1 : (byte) 0);
        dest.writeList(this.serviceAvailabilities);
        dest.writeInt(this.maxCount);
    }

    protected Service(Parcel in) {
        this.uuid = in.readString();
        this.alias = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.items = in.createStringArrayList();
        this.tags = in.createStringArrayList();
        this.imageUrl = in.readString();
        this.instruction = in.readString();
        this.currency = in.readString();
        this.price = in.readString();
        this.minutesDuration = in.readString();
        this.enabled = in.readByte() != 0;
        this.serviceAvailabilities = new ArrayList<ServiceAvailability>();
        in.readList(this.serviceAvailabilities, ServiceAvailability.class.getClassLoader());
        this.maxCount = in.readInt();
    }

    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel source) {
            return new Service(source);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
