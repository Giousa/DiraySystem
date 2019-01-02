package com.zmm.diary.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/25
 * Email:65489469@qq.com
 */
public class HotspotBean implements Serializable{

    private String id;

    private AuthorBean author;

    private String pic;

    private String content;

    private int appreciate;

    private boolean hasCollect;

    private boolean hasAppreciate;

    private boolean hasCorrelate;

    private String createTime;

    private String updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAppreciate() {
        return appreciate;
    }

    public void setAppreciate(int appreciate) {
        this.appreciate = appreciate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public boolean isHasAppreciate() {
        return hasAppreciate;
    }

    public void setHasAppreciate(boolean hasAppreciate) {
        this.hasAppreciate = hasAppreciate;
    }

    public boolean isHasCorrelate() {
        return hasCorrelate;
    }

    public void setHasCorrelate(boolean hasCorrelate) {
        this.hasCorrelate = hasCorrelate;
    }
}
