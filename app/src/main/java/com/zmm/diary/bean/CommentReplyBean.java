package com.zmm.diary.bean;

import java.io.Serializable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/2/14
 * Email:65489469@qq.com
 */
public class CommentReplyBean implements Serializable {

    private String id;

    private String commentId;

    private String fromName;

    private String toName;

    private String content;

    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CommentReplyBean{" +
                "id='" + id + '\'' +
                ", commentId='" + commentId + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
