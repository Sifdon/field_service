package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NiyatiR on 8/22/2016.
 */
public class ResponseStatusApproval implements Serializable{

    @SerializedName("result")
    private List<ModelStatusApproval> result;

    @SerializedName("status")
    private String status;

    @SerializedName("description")
    private String description;

    ////////////////////

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

    public List<ModelStatusApproval> getResult() {
        return result;
    }

    public void setResult(List<ModelStatusApproval> result) {
        this.result = result;
    }
}
