package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NiyatiR on 8/2/2016.
 */
public class ResponseNewWO {

    @SerializedName("result")
    private List<ModelWOHeader> result;

    @SerializedName("status")
    private String status;

    @SerializedName("description")
    private String description;

    public List<ModelWOHeader> getResult() {
        return result;
    }

    public void setResult(List<ModelWOHeader> result) {
        this.result = result;
    }

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
}
