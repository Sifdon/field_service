package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by knax on 6/20/16.
 */
public class RequestStatusTasklist {

    @SerializedName("orderItemId")
    private String orderItemId;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("actionTime")
    private String actionTime;

    @SerializedName("orderAssignmentId")
    private String orderAssignmentId;

    @SerializedName("mechanicId")
    private String mechanicId;

    ////////////////////////////////


    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
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

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getOrderAssignmentId() {
        return orderAssignmentId;
    }

    public void setOrderAssignmentId(String orderAssignmentId) {
        this.orderAssignmentId = orderAssignmentId;
    }

    public String getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(String mechanicId) {
        this.mechanicId = mechanicId;
    }
}
