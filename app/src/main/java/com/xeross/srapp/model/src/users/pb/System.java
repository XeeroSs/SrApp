
package com.xeross.srapp.model.src.users.pb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class System {

    @SerializedName("platform")
    @Expose
    private String platform;
    @SerializedName("emulated")
    @Expose
    private Boolean emulated;
    @SerializedName("region")
    @Expose
    private Object region;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getEmulated() {
        return emulated;
    }

    public void setEmulated(Boolean emulated) {
        this.emulated = emulated;
    }

    public Object getRegion() {
        return region;
    }

    public void setRegion(Object region) {
        this.region = region;
    }

}
