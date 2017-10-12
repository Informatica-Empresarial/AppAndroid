package co.com.lafemmeapp.core.domain.entities;

import java.util.List;

public class ViewServiceBuilder {
    private String uuid;
    private String name;
    private String description;
    private List<String> items;
    private List<String> tags;
    private String imageUrl;
    private String currency;
    private String price;
    private String minutesDuration;
    private int quantity;
    private int maxCount;


    public ViewServiceBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public ViewServiceBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ViewServiceBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ViewServiceBuilder setItems(List<String> items) {
        this.items = items;
        return this;
    }

    public ViewServiceBuilder setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public ViewServiceBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ViewServiceBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ViewServiceBuilder setPrice(String price) {
        this.price = price;
        return this;
    }

    public ViewServiceBuilder setMinutesDuration(String minutesDuration) {
        this.minutesDuration = minutesDuration;
        return this;
    }

    public ViewServiceBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ViewServiceBuilder setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }




    public ViewService createViewService() {
        return new ViewService(uuid, name, description, items, tags, imageUrl,
                currency, price, minutesDuration, quantity, maxCount);
    }
}