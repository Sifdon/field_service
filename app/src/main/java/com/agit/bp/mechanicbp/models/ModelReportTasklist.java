package com.agit.bp.mechanicbp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by NiyatiR on 10/26/2016.
 */
public class ModelReportTasklist implements Serializable {
    @SerializedName("tasklistId")
    private String tasklistId;

    @SerializedName("tasklist")
    private String tasklist;

    @SerializedName("tasklistStatus")
    private String tasklistStatus;

    @SerializedName("completedBy")
    private String completedBy;

    @SerializedName("timeDuration")
    private String timeDuration;

    public String getTasklistId() {
        return tasklistId;
    }

    public void setTasklistId(String tasklistId) {
        this.tasklistId = tasklistId;
    }

    public String getTasklist() {
        return tasklist;
    }

    public void setTasklist(String tasklist) {
        this.tasklist = tasklist;
    }

    public String getTasklistStatus() {
        return tasklistStatus;
    }

    public void setTasklistStatus(String tasklistStatus) {
        this.tasklistStatus = tasklistStatus;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }
}
