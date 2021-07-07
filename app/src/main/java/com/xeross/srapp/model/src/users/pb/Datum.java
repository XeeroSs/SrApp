
package com.xeross.srapp.model.src.users.pb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("place")
    @Expose
    private Integer place;
    @SerializedName("run")
    @Expose
    private Run run;

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

}
