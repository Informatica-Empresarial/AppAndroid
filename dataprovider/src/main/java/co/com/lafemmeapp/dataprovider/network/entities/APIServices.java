package co.com.lafemmeapp.dataprovider.network.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oscargallon on 5/2/17.
 */

public class APIServices {

    private  String uuid;

    private  String alias;

    private  String name;

    private  String description;

    private  List<String> items;

    private  List<String> tags;

    private  String imageUrl;

    private  String instruction;

    private  String currency;

    private  String price;

    @SerializedName("minutes_duration")
    private  String minutesDuration;

    private  boolean enabled;

    private List<APIServiceAvailability> serviceAvailabilities;

    private  String createdBy;

    private  String updatedBy;

    private  String createdAt;

    private  String updatedAt;

    private String accountUuid;

    private int maxCount;



    public APIServices(){

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMinutesDuration() {
        return minutesDuration;
    }

    public void setMinutesDuration(String minutesDuration) {
        this.minutesDuration = minutesDuration;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(String accountUuid) {
        this.accountUuid = accountUuid;
    }

    public List<APIServiceAvailability> getServiceAvailabilities() {
        return serviceAvailabilities;
    }

    public void setServiceAvailabilities(List<APIServiceAvailability> serviceAvailabilities) {
        this.serviceAvailabilities = serviceAvailabilities;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
