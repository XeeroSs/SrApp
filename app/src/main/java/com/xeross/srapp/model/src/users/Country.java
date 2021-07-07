
package com.xeross.srapp.model.src.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("names")
    @Expose
    private Names__1 names;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Names__1 getNames() {
        return names;
    }

    public void setNames(Names__1 names) {
        this.names = names;
    }

}
