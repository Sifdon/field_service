package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by knax on 6/20/16.
 */
public class ResponseStatusWO {
    @SerializedName("status")
    private String status;

    @SerializedName("description")
    private String description;

    @SerializedName("orderStatus")
    private int orderStatus;

    @SerializedName("orderStatusName")
    private String orderStatusName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}


