package co.com.lafemmeapp.core.domain.entities;

import java.util.List;


public class ServiceBuilder {
    private String uuid;
    private String alias;
    private String name;
    private String description;
    private List<String> items;
    private List<String> tags;
    private String imageUrl;
    private String instruction;
    private String currency;
    private String price;
    private String minutesDuration;
    private boolean enabled;
    private List<ServiceAvailability> serviceAvailabilities;
    private int maxCount;

    public ServiceBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public ServiceBuilder setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public ServiceBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ServiceBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ServiceBuilder setItems(List<String> items) {
        this.items = items;
        return this;
    }

    public ServiceBuilder setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public ServiceBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ServiceBuilder setInstruction(String instruction) {
        this.instruction = instruction;
        return this;
    }

    public ServiceBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ServiceBuilder setPrice(String price) {
        this.price = price;
        return this;
    }

    public ServiceBuilder setMinutesDuration(String minutesDuration) {
        this.minutesDuration = minutesDuration;
        return this;
    }

    public ServiceBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public ServiceBuilder setServiceAvailabilities(List<ServiceAvailability> serviceAvailabilities) {
        this.serviceAvailabilities = serviceAvailabilities;
        return this;
    }

    public ServiceBuilder setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public Service createService() {
        return new Service(uuid, alias, name, description, items, tags, imageUrl,
                instruction, currency, price, minutesDuration, enabled, serviceAvailabilities,
                maxCount);
    }
}