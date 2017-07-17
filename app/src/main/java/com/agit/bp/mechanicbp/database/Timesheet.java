package com.agit.bp.mechanicbp.database;

import java.io.Serializable;

/**
 * Created by Mushlihun on 19/08/2016.
 */
public class Timesheet implements Serializable {
	private String orderassigmentid;
    private String orderheaderid;
    private String sonumber;
    private String orderdate;
    private String startdate;
    private String enddate;
    private String customername;
    private String address;
    private String material;
    private String serialnumber;
    private String warranty;
    private String remark;
    private String workcenter;
    private String contactperson;
    private String phone;
    private String ordertype;
    private String estimatetime;
    private String sparepart;
    private String plant;
    private String orderstatus;
    private String orderstatusname;
    private String mechanicid;
    private String actiondate;
    
    
	public String getorderassigmentid() {
		return orderassigmentid;
	}
	public void setorderassigmentid(String orderassigmentid) {
		this.orderassigmentid = orderassigmentid;
	}
	public String getorderheaderid() {
		return orderheaderid;
	}
	public void setorderheaderid(String orderheaderid) {
		this.orderheaderid = orderheaderid;
	}
	public String getSonumber() {
		return sonumber;
	}
	public void setSonumber(String sonumber) {
		this.sonumber = sonumber;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setorderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
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
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String warranty) {
		this.serialnumber = serialnumber;
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
		return workcenter;
	}
	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getEstimatetime() {
		return estimatetime;
	}
	public void setEstimatetime(String estimatetime) {
		this.estimatetime = estimatetime;
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
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getOrderstatusname() {
		return orderstatusname;
	}
	public void setOrderstatusname(String orderstatusname) {
		this.orderstatusname = orderstatusname;
	}
	public String getMechanicid() {
		return mechanicid;
	}
	public void setMechanicid(String mechanicid) {
		this.mechanicid = mechanicid;
	}
	public String getActiondate() {
		return actiondate;
	}
	public void setActiondate(String actiondate) {
		this.actiondate = actiondate;
	}

    
}
