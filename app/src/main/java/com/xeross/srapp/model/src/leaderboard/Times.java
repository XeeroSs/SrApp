
package com.xeross.srapp.model.src.leaderboard;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Times {

    @SerializedName("primary")
    @Expose
    private String primary;
    @SerializedName("primary_t")
    @Expose
    private Double primaryT;
    @SerializedName("realtime")
    @Expose
    private Object realtime;
    @SerializedName("realtime_t")
    @Expose
    private Integer realtimeT;
    @SerializedName("realtime_noloads")
    @Expose
    private Object realtimeNoloads;
    @SerializedName("realtime_noloads_t")
    @Expose
    private Integer realtimeNoloadsT;
    @SerializedName("ingame")
    @Expose
    private String ingame;
    @SerializedName("ingame_t")
    @Expose
    private Double ingameT;

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public Double getPrimaryT() {
        return primaryT;
    }

    public void setPrimaryT(Double primaryT) {
        this.primaryT = primaryT;
    }

    public Object getRealtime() {
        return realtime;
    }

    public void setRealtime(Object realtime) {
        this.realtime = realtime;
    }

    public Integer getRealtimeT() {
        return realtimeT;
    }

    public void setRealtimeT(Integer realtimeT) {
        this.realtimeT = realtimeT;
    }

    public Object getRealtimeNoloads() {
        return realtimeNoloads;
    }

    public void setRealtimeNoloads(Object realtimeNoloads) {
        this.realtimeNoloads = realtimeNoloads;
    }

    public Integer getRealtimeNoloadsT() {
        return realtimeNoloadsT;
    }

    public void setRealtimeNoloadsT(Integer realtimeNoloadsT) {
        this.realtimeNoloadsT = realtimeNoloadsT;
    }

    public String getIngame() {
        return ingame;
    }

    public void setIngame(String ingame) {
        this.ingame = ingame;
    }

    public Double getIngameT() {
        return ingameT;
    }

    public void setIngameT(Double ingameT) {
        this.ingameT = ingameT;
    }

}
