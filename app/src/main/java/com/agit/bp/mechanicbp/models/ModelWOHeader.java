package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NiyatiR on 7/31/2016.
 */

public class ModelWOHeader implements Serializable {

    @SerializedName("orderAssignmentId")
    private String orderAssignmentId;

    @SerializedName("orderHeaderId")
    private String orderHeaderId;

    @SerializedName("soNumber")
    private String soNumber;

    //created date
    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("customerName")
    private String customerName;

    @SerializedName("address")
    private String address;

    @SerializedName("material")
    private String material;

    @SerializedName("serialNumber")
    private String serialNumber;

    @SerializedName("warranty")
    private String warranty;

    @SerializedName("remark")
    private String remark;

    @SerializedName("workCenter")
    private String workCenter;

    @SerializedName("contactPerson")
    private String contactPerson;

    @SerializedName("phone")
    private String phone;

    @SerializedName("orderType")
    private String orderType;

    //////////////
    @SerializedName("estimateTime")
    private String estimateTime;

    @SerializedName("sparepart")
    private String sparepart;

    @SerializedName("plant")
    private String plant;

    /////////////

    @SerializedName("orderStatus")
    private int orderStatus;

    @SerializedName("orderStatusName")
    private String orderStatusName;

    @SerializedName("mechanicId")
    private String mechanicId;

    @SerializedName("actionDate")
    private String actionDate;

    @SerializedName("orderDateString")
    private String orderDateString;

    @SerializedName("startDateString")
    private String startDateString;

    @SerializedName("endDateString")
    private String endDateString;

    @SerializedName("orderItemView")
    private List<ModelWOTakslist> orderItemView;

    //////////////////////////////////////////


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

    public String getSoNumber() {
        return soNumber;
    }

    public void setSoNumber(String soNumber) {
        this.soNumber = soNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getSparepart() {
        return sparepart;
    }

    public void setSparepart(String sparepart) {
        this.sparepart = sparepart;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
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

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getOrderDateString() {
        return orderDateString;
    }

    public void setOrderDateString(String orderDateString) {
        this.orderDateString = orderDateString;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public List<ModelWOTakslist> getOrderItemView() {
        return orderItemView;
    }

    public void setOrderItemView(List<ModelWOTakslist> orderItemView) {
        this.orderItemView = orderItemView;
    }
}