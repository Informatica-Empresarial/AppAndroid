package co.com.lafemmeapp.core.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by oscargallon on 5/3/17.
 */

public class ViewService implements Parcelable {

    private final String uuid;

    private final String name;

    private final String description;

    private final List<String> items;

    private final List<String> tags;

    private final String imageUrl;

    private final String currency;

    private final String price;

    @SerializedName("minutes_duration")
    private final String minutesDuration;

    private int quantity;

    private final int maxCount;


    public ViewService(String uuid, String name, String description,
                       List<String> items, List<String> tags, String imageUrl,
                       String currency, String price, String minutesDuration,
                       int quantity, int maxCount) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.items = items;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.currency = currency;
        this.price = price;
        this.minutesDuration = minutesDuration;
        this.quantity = quantity;
        this.maxCount = maxCount;
    }

    public String getUuid() {
        return uuid;
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

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }

    public String getMinutesDuration() {
        return minutesDuration;
    }

    public int getQuantity() {
        return quantity;
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
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeStringList(this.items);
        dest.writeStringList(this.tags);
        dest.writeString(this.imageUrl);
        dest.writeString(this.currency);
        dest.writeString(this.price);
        dest.writeString(this.minutesDuration);
        dest.writeInt(this.quantity);
        dest.writeInt(this.maxCount);
    }

    protected ViewService(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.items = in.createStringArrayList();
        this.tags = in.createStringArrayList();
        this.imageUrl = in.readString();
        this.currency = in.readString();
        this.price = in.readString();
        this.minutesDuration = in.readString();
        this.quantity = in.readInt();
        this.maxCount = in.readInt();
    }

    public static final Creator<ViewService> CREATOR = new Creator<ViewService>() {
        @Override
        public ViewService createFromParcel(Parcel source) {
            return new ViewService(source);
        }

        @Override
        public ViewService[] newArray(int size) {
            return new ViewService[size];
        }
    };

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
