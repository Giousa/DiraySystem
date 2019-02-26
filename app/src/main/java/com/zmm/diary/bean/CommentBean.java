package com.zmm.diary.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/2/14
 * Email:65489469@qq.com
 */
public class CommentBean implements Serializable {

    private String id;

    private UserBean fromUser;

    private String hotspotId;

    private String content;

    //点赞数
    private int praiseCount;

    private String createTime;

    private List<CommentReplyBean> commentReplyList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserBean getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserBean fromUser) {
        this.fromUser = fromUser;
    }

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<CommentReplyBean> getCommentReplyList() {
        return commentReplyList;
    }

    public void setCommentReplyList(List<CommentReplyBean> commentReplyList) {
        this.commentReplyList = commentReplyList;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "id='" + id + '\'' +
                ", fromUser=" + fromUser +
                ", hotspotId='" + hotspotId + '\'' +
                ", content='" + content + '\'' +
                ", praiseCount=" + praiseCount +
                ", createTime=" + createTime +
                ", commentReplyList=" + commentReplyList +
                '}';
    }
}
