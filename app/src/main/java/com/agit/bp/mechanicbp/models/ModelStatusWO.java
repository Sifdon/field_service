package com.agit.bp.mechanicbp.models;

/**
 * Created by NiyatiR on 8/7/2016.
 */
public class ModelStatusWO {

    private int status_sequence;
    private int status_id;
    private String status_name;
    private String status_buttonname;
    private String status_path;

    public ModelStatusWO(){
    }

    public ModelStatusWO(
            int status_sequence,
            int status_id,
            String status_name,
            String status_buttonname,
            String status_path
    ) {
        this.status_sequence = status_sequence;
        this.status_id = status_id;
        this.status_name = status_name;
        this.status_buttonname = status_buttonname;
        this.status_path = status_path;
    }

    public int getStatus_sequence() {
        return status_sequence;
    }

    public void setStatus_sequence(int status_sequence) {
        this.status_sequence = status_sequence;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getStatus_buttonname() {
        return status_buttonname;
    }

    public void setStatus_buttonname(String status_buttonname) {
        this.status_buttonname = status_buttonname;
    }

    public String getStatus_path() {
        return status_path;
    }

    public void setStatus_path(String status_path) {
        this.status_path = status_path;
    }
}
