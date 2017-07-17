package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by NiyatiR on 8/13/2016.
 */
public class ResponseUserMechanic implements Serializable{

    @SerializedName("staffId")
    private String staffId;

    @SerializedName("staffName")
    private String staffName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("plantcode")
    private String plantcode;

    @SerializedName("statusToken")
    private String statusToken;

    @SerializedName("token")
    private String token;

    @SerializedName("dateTime")
    private String dateTime;

    private String username;
    private String password;
    private String imei;

    //////////////////////////////

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlantcode() {
        return plantcode;
    }

    public void setPlantcode(String plantcode) {
        this.plantcode = plantcode;
    }

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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatusToken() {
        return statusToken;
    }

    public void setStatusToken(String statusToken) {
        this.statusToken = statusToken;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
