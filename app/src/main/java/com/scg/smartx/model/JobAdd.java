package com.scg.smartx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobAdd {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
