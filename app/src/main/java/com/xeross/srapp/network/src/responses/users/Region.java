
package com.xeross.srapp.network.src.responses.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Region {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("names")
    @Expose
    private Names__2 names;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Names__2 getNames() {
        return names;
    }

    public void setNames(Names__2 names) {
        this.names = names;
    }

}
