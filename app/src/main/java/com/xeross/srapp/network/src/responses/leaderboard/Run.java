
package com.xeross.srapp.network.src.responses.leaderboard;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Run {

    @SerializedName("place")
    @Expose
    private Integer place;
    @SerializedName("run")
    @Expose
    private Run__1 run;

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Run__1 getRun() {
        return run;
    }

    public void setRun(Run__1 run) {
        this.run = run;
    }

}
