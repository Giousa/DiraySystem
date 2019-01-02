package com.zmm.diary.bean;

import java.io.Serializable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/2
 * Email:65489469@qq.com
 */
public class CorrelateBean implements Serializable{

    private String id;

    private String username;

    private String nickname;

    private String sign;

    private String icon;

    private int releases;

    private int funs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getReleases() {
        return releases;
    }

    public void setReleases(int releases) {
        this.releases = releases;
    }

    public int getFuns() {
        return funs;
    }

    public void setFuns(int funs) {
        this.funs = funs;
    }
}
