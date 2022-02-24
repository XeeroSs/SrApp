
package com.xeross.srapp.network.src.responses.users;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("names")
    @Expose
    private Names names;
    @SerializedName("pronouns")
    @Expose
    private String pronouns;
    @SerializedName("weblink")
    @Expose
    private String weblink;
    @SerializedName("name-style")
    @Expose
    private NameStyle nameStyle;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("signup")
    @Expose
    private String signup;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("twitch")
    @Expose
    private Object twitch;
    @SerializedName("hitbox")
    @Expose
    private Object hitbox;
    @SerializedName("youtube")
    @Expose
    private Youtube youtube;
    @SerializedName("twitter")
    @Expose
    private Object twitter;
    @SerializedName("speedrunslive")
    @Expose
    private Object speedrunslive;
    @SerializedName("links")
    @Expose
    private List<Link> links = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Names getNames() {
        return names;
    }

    public void setNames(Names names) {
        this.names = names;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public NameStyle getNameStyle() {
        return nameStyle;
    }

    public void setNameStyle(NameStyle nameStyle) {
        this.nameStyle = nameStyle;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSignup() {
        return signup;
    }

    public void setSignup(String signup) {
        this.signup = signup;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Object getTwitch() {
        return twitch;
    }

    public void setTwitch(Object twitch) {
        this.twitch = twitch;
    }

    public Object getHitbox() {
        return hitbox;
    }

    public void setHitbox(Object hitbox) {
        this.hitbox = hitbox;
    }

    public Youtube getYoutube() {
        return youtube;
    }

    public void setYoutube(Youtube youtube) {
        this.youtube = youtube;
    }

    public Object getTwitter() {
        return twitter;
    }

    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

    public Object getSpeedrunslive() {
        return speedrunslive;
    }

    public void setSpeedrunslive(Object speedrunslive) {
        this.speedrunslive = speedrunslive;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
