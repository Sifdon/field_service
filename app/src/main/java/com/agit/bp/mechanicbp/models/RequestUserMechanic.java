package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by NiyatiR on 8/13/2016.
 */
public class RequestUserMechanic implements Serializable{

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("apiKey")
    private String apiKey;
    @SerializedName("imei")
    private String imei;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
