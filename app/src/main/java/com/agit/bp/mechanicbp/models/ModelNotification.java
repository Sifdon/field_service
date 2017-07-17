package com.agit.bp.mechanicbp.models;

import java.io.Serializable;

/**
 * Created by NiyatiR on 10/9/2016.
 */
public class ModelNotification implements Serializable {

    private String id;
    private String orderHeaderId;
    private String soNumber;
    private String customerName;
    private String title;
    private int readNotif;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadNotif() {
        return readNotif;
    }

    public void setReadNotif(int readNotif) {
        this.readNotif = readNotif;
    }
}