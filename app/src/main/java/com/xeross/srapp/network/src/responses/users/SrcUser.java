
package com.xeross.srapp.network.src.responses.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SrcUser {

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
