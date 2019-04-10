package com.zmm.diary.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zmm.diary.R;
import com.zmm.diary.bean.CommentBean;
import com.zmm.diary.bean.CommentReplyBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.utils.DateUtils;
import com.zmm.diary.utils.GlideUtils;
import com.zmm.diary.utils.TimeUtils;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentBean> commentBeanList = new ArrayList<>();
    private Context context;

    public CommentExpandAdapter(Context context) {
        this.context = context;
    }

    public void setNewData(List<CommentBean> commentBeanList) {
        this.commentBeanList = commentBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentBeanList.get(i).getCommentReplyList() == null){
            return 0;
        }else {
            return commentBeanList.get(i).getCommentReplyList().size()>0 ? commentBeanList.get(i).getCommentReplyList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getCommentReplyList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }


        CommentBean commentBean = commentBeanList.get(groupPosition);
        UserBean fromUser = commentBean.getFromUser();

        GlideUtils.loadCircleImage(context, CommonConfig.BASE_PIC_URL + fromUser.getIcon(),groupHolder.logo);

        groupHolder.tv_name.setText(TextUtils.isEmpty(fromUser.getNickname()) ? fromUser.getUsername() : fromUser.getNickname());
        groupHolder.tv_content.setText(commentBean.getContent());

        //处理时间
        try {
            String timeFormatText = TimeUtils.getTimeFormatText(DateUtils.stringToDate(commentBean.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            groupHolder.tv_time.setText(timeFormatText);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;

        System.out.println("回复数据：：：：：：：");
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout,viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }


        CommentReplyBean commentReplyBean = commentBeanList.get(groupPosition).getCommentReplyList().get(childPosition);
        String fromName = commentReplyBean.getFromName();
        String toName = commentReplyBean.getToName();

        String replyUser = null;
        if(!TextUtils.isEmpty(fromName)){


            if(!TextUtils.isEmpty(toName)){
                replyUser = commentReplyBean.getFromName()+"@"+commentReplyBean.getToName();
            }else {
                replyUser = commentReplyBean.getFromName();
            }

//            if(!TextUtils.isEmpty(toName) && !fromName.equals(toName)){
//                replyUser = commentReplyBean.getFromName()+"@"+commentReplyBean.getToName();
//
//            }else {
//                replyUser = commentReplyBean.getFromName();
//
//            }
        }

        if(!TextUtils.isEmpty(replyUser)){
            childHolder.tv_name.setText(replyUser + ":");
        }else {
            childHolder.tv_name.setText("匿名"+":");
        }

//        childHolder.tv_content.setText(replyUser + ":"+commentReplyBean.getContent());
        String content = replyUser + ":" + commentReplyBean.getContent();
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new ForegroundColorSpan(UIUtils.getResources().getColor(R.color.md_light_green_500)),0,replyUser.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        childHolder.tv_content.setText(spannable);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }



    private class GroupHolder{
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        public GroupHolder(View view) {
            logo = view.findViewById(R.id.comment_item_logo);
            tv_content = view.findViewById(R.id.comment_item_content);
            tv_name = view.findViewById(R.id.comment_item_userName);
            tv_time = view.findViewById(R.id.comment_item_time);
        }
    }

    private class ChildHolder{
        private TextView tv_name, tv_content;
        public ChildHolder(View view) {
            tv_name = view.findViewById(R.id.reply_item_user);
            tv_content = view.findViewById(R.id.reply_item_content);
        }
    }


    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     * @param commentBean 新的评论数据
     */
    public void addTheCommentData(CommentBean commentBean){
        if(commentBean!=null){

            commentBeanList.add(commentBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(CommentReplyBean replyDetailBean, int groupPosition){
        if(replyDetailBean!=null){
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            if(commentBeanList.get(groupPosition).getCommentReplyList() != null ){
                commentBeanList.get(groupPosition).getCommentReplyList().add(replyDetailBean);
            }else {
                List<CommentReplyBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setCommentReplyList(replyList);
            }
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:添加和展示所有回复
     * @param replyBeanList 所有回复数据
     * @param groupPosition 当前的评论
     */
    private void addReplyList(List<CommentReplyBean> replyBeanList, int groupPosition){
        if(commentBeanList.get(groupPosition).getCommentReplyList() != null ){
            commentBeanList.get(groupPosition).getCommentReplyList().clear();
            commentBeanList.get(groupPosition).getCommentReplyList().addAll(replyBeanList);
        }else {

            commentBeanList.get(groupPosition).setCommentReplyList(replyBeanList);
        }

        notifyDataSetChanged();
    }

}
