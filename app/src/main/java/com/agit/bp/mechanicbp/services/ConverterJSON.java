package com.agit.bp.mechanicbp.services;

import com.agit.bp.mechanicbp.models.RequestStatusTasklist;
import com.agit.bp.mechanicbp.models.RequestStatusVOC;
import com.agit.bp.mechanicbp.models.RequestStatusWO;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by NiyatiR on 8/15/2016.
 */
public class ConverterJSON {

    /*
    contoh JSON yang mau dibuat :

    {"actionTime":"Aug 15, 2016 16:06:42",
    "latitude":-6.1843084,
    "longitude":106.844634,
    "mechanicId":"STF0001",
    "note":"",
    "orderHeaderId":"OH0000000056",
    "orderStatusName":"Assigned Work Order",
    "orderStatus":5070}

     */

    public String jsontoStringWO(RequestStatusWO data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public String jsontoStringTasklist(RequestStatusTasklist data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public String jsontoStringVOC(RequestStatusVOC data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public RequestStatusWO stringtoJsonWO(String JSON) {
        RequestStatusWO data = new RequestStatusWO();
        try {
            JSONObject result = new JSONObject(JSON);
            data.setOrderAssignmentId(result.getString("orderAssignmentId"));
            data.setOrderHeaderId(result.getString("orderHeaderId"));
            data.setLatitude(Double.parseDouble(result.getString("latitude")));
            data.setLongitude(Double.parseDouble(result.getString("longitude")));
            data.setOrderStatus(Integer.parseInt(result.getString("orderStatus")));
            data.setOrderStatusName(result.getString("orderStatusName"));
            data.setMechanicId(result.getString("mechanicId"));
            data.setActionTime(result.getString("actionTime"));
            data.setNote(result.getString("note"));

        } catch (Exception e) {
            data = null;
        }
        return data;
    }

    public RequestStatusTasklist stringtoJsonTasklist(String JSON) {
        RequestStatusTasklist data = new RequestStatusTasklist();
        try {
            JSONObject result = new JSONObject(JSON);
            data.setOrderAssignmentId(result.getString("orderAssignmentId"));
            data.setMechanicId(result.getString("mechanicId"));
            data.setOrderItemId(result.getString("orderItemId"));
            data.setLatitude(Double.parseDouble(result.getString("latitude")));
            data.setLongitude(Double.parseDouble(result.getString("longitude")));
            data.setActionTime(result.getString("actionTime"));
        } catch (Exception e) {
            data = null;
        }
        return data;
    }


    public RequestStatusVOC stringtoJsonVOC(String JSON) {
        RequestStatusVOC data = new RequestStatusVOC();
        try {
            JSONObject result = new JSONObject(JSON);
            data.setOrderAssignmentId(result.getString("orderAssignmentId"));
            data.setOrderHeaderId(result.getString("orderHeaderId"));
            data.setLatitude(Double.parseDouble(result.getString("latitude")));
            data.setLongitude(Double.parseDouble(result.getString("longitude")));
            data.setOrderStatus(Integer.parseInt(result.getString("orderStatus")));
            data.setOrderStatusName(result.getString("orderStatusName"));
            data.setMechanicId(result.getString("mechanicId"));
            data.setActionTime(result.getString("actionTime"));
            data.setNote(result.getString("note"));

            data.setIsSatisfyWO(result.getString("isSatisfyWO"));
            data.setIsDoneWO(result.getString("isDoneWO"));
            data.setMechanicScore(result.getString("mechanicScore"));
            data.setRecipientComment(result.getString("recipientComment"));
            data.setRecipientName(result.getString("recipientName"));
            data.setSignatureImage(result.getString("signatureImage"));

        } catch (Exception e) {
            data = null;
        }
        return data;
    }
}
