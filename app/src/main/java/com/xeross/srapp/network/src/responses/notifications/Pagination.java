
package com.xeross.srapp.network.src.responses.notifications;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Pagination {

    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("links")
    @Expose
    private List<Link__1> links = null;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Link__1> getLinks() {
        return links;
    }

    public void setLinks(List<Link__1> links) {
        this.links = links;
    }

}
