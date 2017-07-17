package com.agit.bp.mechanicbp.models;

import com.google.gson.Gson;
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

    public static String convertSentWO() {

        RequestStatusWO data = new RequestStatusWO();
        data.setActionTime("10 agustus 2005");
        data.setLatitude(0.00000057);
        data.setLongitude(0.87777676);
        data.setMechanicId("SF0001");
        data.setNote("lalalalala");
        data.setOrderHeaderId("OOOOHHHHH56");
        data.setOrderStatusName("Waiting Approval");
        data.setOrderStatus(5065);

        Gson gson = new Gson();

        //Type type = new TypeToken<RequestStatusWO>() {}.getType();
        //gson.toJson(data, type);
        return gson.toJson(data);
    }

    public static String convertSentTasklist(RequestStatusTasklist data) {
        return "";
    }

    public static String convertSentFeedback(RequestStatusWO data) {
        return "";
    }
}
