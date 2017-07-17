package com.agit.bp.mechanicbp.models;

/**
 * Created by NiyatiR on 9/5/2016.
 */
public class ModelDeletedWO {

    private String orderHeaderId;
    private boolean isExist;

    public String getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(String orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }
}
