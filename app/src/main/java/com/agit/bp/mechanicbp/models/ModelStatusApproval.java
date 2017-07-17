package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by NiyatiR on 8/22/2016.
 */
public class ModelStatusApproval implements Serializable{

    @SerializedName("orderHeaderId")
    private String orderHeaderId;

    @SerializedName("orderStatus")
    private int orderStatus;

    @SerializedName("orderStatusName")
    private String orderStatusName;

    public String getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(String orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
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
