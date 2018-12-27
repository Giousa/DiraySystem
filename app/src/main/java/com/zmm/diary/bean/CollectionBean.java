package com.zmm.diary.bean;

import java.io.Serializable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/27
 * Email:65489469@qq.com
 */
public class CollectionBean implements Serializable{

    private String id;

    private String userid;

    private String hotspotd;

    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHotspotd() {
        return hotspotd;
    }

    public void setHotspotd(String hotspotd) {
        this.hotspotd = hotspotd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
