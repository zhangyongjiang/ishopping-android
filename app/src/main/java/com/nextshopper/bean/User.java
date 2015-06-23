package com.nextshopper.bean;

/**
 * Created by siyiliu on 6/20/15.
 */
public class User {
    private String id;
    private UserBasicInfo info;
    private String ip;

    public UserBasicInfo getUserBasicInfo() {
        return info;
    }

    public void setUserBasicInfo(UserBasicInfo info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
