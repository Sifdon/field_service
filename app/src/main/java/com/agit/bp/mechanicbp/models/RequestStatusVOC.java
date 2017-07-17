package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NiyatiR on 8/17/2016.
 */
public class RequestStatusVOC {

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

    /////////////feedback///////////////////

    @SerializedName("isSatisfyWO")
    private String isSatisfyWO;

    @SerializedName("isDoneWO")
    private String isDoneWO;

    @SerializedName("mechanicScore")
    private String mechanicScore;

    @SerializedName("recipientComment")
    private String recipientComment;

    @SerializedName("recipientName")
    private String recipientName;

    @SerializedName("signatureImage")
    private String signatureImage;

    ///////////////////////////////


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

    public String getIsSatisfyWO() {
        return isSatisfyWO;
    }

    public void setIsSatisfyWO(String isSatisfyWO) {
        this.isSatisfyWO = isSatisfyWO;
    }

    public String getIsDoneWO() {
        return isDoneWO;
    }

    public void setIsDoneWO(String isDoneWO) {
        this.isDoneWO = isDoneWO;
    }

    public String getMechanicScore() {
        return mechanicScore;
    }

    public void setMechanicScore(String mechanicScore) {
        this.mechanicScore = mechanicScore;
    }

    public String getRecipientComment() {
        return recipientComment;
    }

    public void setRecipientComment(String recipientComment) {
        this.recipientComment = recipientComment;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }
}
