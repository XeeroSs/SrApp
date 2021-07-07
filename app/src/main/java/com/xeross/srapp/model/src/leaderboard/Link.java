
package com.xeross.srapp.model.src.leaderboard;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Link {

    @SerializedName("uri")
    @Expose
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
