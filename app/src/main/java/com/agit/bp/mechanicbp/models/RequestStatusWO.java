package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by knax on 6/20/16.
 */
public class RequestStatusWO {

    @SerializedName("orderAssignmentId")
    private String orderAssignmentId;

    @SerializedName("orderHeaderId")
    private String orderHeaderId;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("orderStatus")
    private int orderStatus;

    @SerializedName("orderStatusName")
    private String orderStatusName;

    @SerializedName("mechanicId")
    private String mechanicId;

    @SerializedName("actionTime")
    private String actionTime;

    @SerializedName("note")
    private String note;

    //////////////////////////////////////


    public String getOrderAssignmentId() {
        return orderAssignmentId;
    }

    public void setOrderAssignmentId(String orderAssignmentId) {
        this.orderAssignmentId = orderAssignmentId;
    }

    public String getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(String orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(String mechanicId) {
        this.mechanicId = mechanicId;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
