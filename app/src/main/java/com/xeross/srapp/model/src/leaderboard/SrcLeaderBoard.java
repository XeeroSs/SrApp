
package com.xeross.srapp.model.src.leaderboard;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SrcLeaderBoard {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
