package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NiyatiR on 7/31/2016.
 */
public class ModelWOTakslist {
    @SerializedName("orderiItemId")
    private String orderiItemId;

    @SerializedName("orderHeaderId")
    private String orderHeaderId;

    @SerializedName("taskList")
    private String taskList;

    @SerializedName("orderItemStatus")
    private int orderItemStatus;

    @SerializedName("orderItemStatusName")
    private String orderItemStatusName;

    @SerializedName("actionDate")
    private String actionDate;
    //private String workinghours;

    //////////////////////////////////


    public String getOrderiItemId() {
        return orderiItemId;
    }

    public void setOrderiItemId(String orderiItemId) {
        this.orderiItemId = orderiItemId;
    }

    public String getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(String orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public String getTaskList() {
        return taskList;
    }

    public void setTaskList(String taskList) {
        this.taskList = taskList;
    }

    public int getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(int orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public String getOrderItemStatusName() {
        return orderItemStatusName;
    }

    public void setOrderItemStatusName(String orderItemStatusName) {
        this.orderItemStatusName = orderItemStatusName;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }
}