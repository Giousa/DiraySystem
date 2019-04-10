package com.zmm.diary.ui.activity;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.zmm.diary.R;
import com.zmm.diary.bean.AuthorBean;
import com.zmm.diary.bean.CommentBean;
import com.zmm.diary.bean.CommentReplyBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerHotspotComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.HotspotModule;
import com.zmm.diary.mvp.presenter.HotspotPresenter;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;
import com.zmm.diary.ui.adapter.CommentExpandAdapter;
import com.zmm.diary.ui.widget.CommentExpandableListView;
import com.zmm.diary.utils.DateUtils;
import com.zmm.diary.utils.GlideUtils;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;
import com.zmm.diary.utils.VerificationUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/27
 * Email:65489469@qq.com
 */
public class HotspotDetailActivity extends BaseActivity<HotspotPresenter> implements HotspotContract.HotspotView {


    @BindView(R.id.iv_hotspot_pic)
    ImageView mIvHotspotPic;
    @BindView(R.id.tv_hotspot_content)
    TextView mTvHotspotContent;
    @BindView(R.id.iv_hotspot_appreciate)
    ImageView mIvHotspotAppreciate;
    @BindView(R.id.tv_hotspot_appreciate_count)
    TextView mTvHotspotAppreciateCount;
    @BindView(R.id.ll_hotspot_appreciate)
    LinearLayout mLlHotspotAppreciate;
    @BindView(R.id.iv_hotspot_collection)
    ImageView mIvHotspotCollection;
    @BindView(R.id.ll_hotspot_collection)
    LinearLayout mLlHotspotCollection;
    @BindView(R.id.ll_hotspot_comment)
    LinearLayout mLlHotspotComment;
    @BindView(R.id.iv_hotspot_author_icon)
    ImageView mIvHotspotAuthorIcon;
    @BindView(R.id.tv_hotspot_author_name)
    TextView mTvHotspotAuthorName;
    @BindView(R.id.tv_hotspot_create_time)
    TextView mTvHotspotCreateTime;
    @BindView(R.id.tv_hotspot_followers)
    TextView mTvHotspotFollowers;
    @BindView(R.id.ll_hotspot_followers)
    LinearLayout mLlHotspotFollowers;
    @BindView(R.id.ll_hotspot_author)
    LinearLayout mLlHotspotAuthor;
    @BindView(R.id.toolbar_hotspot)
    Toolbar mToolbarHotspot;
    @BindView(R.id.collapsing_toolbar_hotspot)
    CollapsingToolbarLayout mCollapsingToolbarHotspot;
    @BindView(R.id.appbar_hotspot)
    AppBarLayout mAppbarHotspot;
    @BindView(R.id.main_content_hotspot)
    CoordinatorLayout mMainContentHotspot;
    @BindView(R.id.tv_hotspot_comment_bottom)
    TextView mHotspotCommentBottom;
    @BindView(R.id.comment_list_view)
    CommentExpandableListView mCommentListView;
//    @BindView(R.id.easy_refresh_layout)
//    EasyRefreshLayout mEasyRefreshLayout;

    private int mPage = 0;
    private int mSize = 10;

    private String mHotspotId;
    private String mUserId;
    private String mAuthorId;

    private int mAppreciateCount = 0;
    private int mCollectionCount = 0;
    private BottomSheetDialog dialog;
    private CommentExpandAdapter mCommentExpandAdapter;

    private List<CommentBean> mCommentBeanList;


    @Override
    protected int setLayout() {
        return R.layout.activity_hotspot_detail;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerHotspotComponent.builder()
                .httpComponent(httpComponent)
                .hotspotModule(new HotspotModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mHotspotId = getIntent().getStringExtra("hotspotId");
        mUserId = UIUtils.getUserBean().getId();


        setSupportActionBar(mToolbarHotspot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mCommentBeanList = new ArrayList<>();

        mPresenter.findAllCommentsByHotspotId(mHotspotId, mPage, mSize);

        initExpandableListView();

        initLoadAndRefresh();

    }


    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.findHotspotById(mUserId, mHotspotId);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView() {

        mCommentListView.setGroupIndicator(null);
        //默认展开所有回复
        mCommentExpandAdapter = new CommentExpandAdapter(this);
        mCommentListView.setAdapter(mCommentExpandAdapter);
        mCommentListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
//                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
//                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(mCommentBeanList.get(groupPosition),0,false);
                return true;
            }
        });

        mCommentListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                showReplyDialog(mCommentBeanList.get(groupPosition),childPosition,true);

                return false;
            }
        });

//        mCommentListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                //toast("展开第"+groupPosition+"个分组");
//
//            }
//        });
    }



    /**
     * 下拉刷新和上拉加载
     */
    private void initLoadAndRefresh() {

//        mEasyRefreshLayout.setEnablePullToRefresh(false);
//
//        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
//            @Override
//            public void onLoadMore() {
//                System.out.println("----onLoadMore----");
//
//            }
//
//            @Override
//            public void onRefreshing() {
//                System.out.println("----onRefreshing----");
//            }
//        });
    }

    @OnClick({R.id.ll_hotspot_comment, R.id.ll_hotspot_followers, R.id.ll_hotspot_appreciate, R.id.ll_hotspot_collection, R.id.tv_hotspot_comment_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_hotspot_appreciate:
                mPresenter.appreciateHotspot(mUserId, mHotspotId);
                break;

            case R.id.ll_hotspot_collection:
                mPresenter.collectionHotspot(mUserId, mHotspotId);
                break;

            case R.id.ll_hotspot_comment:

                break;
            case R.id.ll_hotspot_followers:
                mPresenter.correlateAuthor(mUserId, mAuthorId);
                break;

            case R.id.tv_hotspot_comment_bottom:
                showCommentDialog();

                break;
        }
    }

    /**
     * 展示评论dialog
     */
    private void showCommentDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    dialog.dismiss();
//                    ToastUtils.SimpleToast("评论内容成功");
                    mPresenter.newComment(mHotspotId, mUserId, commentContent);
                } else {
                    ToastUtils.SimpleToast("评论内容不能为空");
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();

    }

    private void showReplyDialog(final CommentBean commentBean, final int childPosition, final boolean flag) {

        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);

        if(flag){
            commentText.setHint("回复 " + commentBean.getCommentReplyList().get(childPosition).getFromName() + " 的评论:");
        }else {
            commentText.setHint("回复 " + commentBean.getFromUser().getNickname() + " 的评论:");
        }
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();

                    UserBean userBean = UIUtils.getUserBean();
                    if(userBean != null){

                        if(flag){
                            mPresenter.replyComment(commentBean.getId(),
                                    userBean.getId(),
                                    commentBean.getCommentReplyList().get(childPosition).getFromId(),
                                    replyContent);
                        }else {
                            //因为是回复的主回复条目，不需要显示toName
                            mPresenter.replyComment(commentBean.getId(),
                                    userBean.getId(),
                                    null,
                                    replyContent);
                        }


                    }


                }else {
                    ToastUtils.SimpleToast("回复内容不能为空");
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }


    @Override
    public void addSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void appreciateOrCollectionOrAuthorStatus(String msg) {
        switch (msg) {

            case "appreciateConfirm":
//                ToastUtils.SimpleToast("点赞");
                mAppreciateCount++;
                mTvHotspotAppreciateCount.setText(mAppreciateCount + "");
                mIvHotspotAppreciate.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.icon_appreciate_selected));
                break;

            case "appreciateCancel":
//                ToastUtils.SimpleToast("取消点赞");
                mAppreciateCount--;
                mTvHotspotAppreciateCount.setText(mAppreciateCount + "");
                mIvHotspotAppreciate.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.icon_appreciate));
                break;

            case "collectionConfirm":
//                ToastUtils.SimpleToast("收藏");
                mIvHotspotCollection.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.my_collect_icon_selected));
                break;

            case "collectionCancel":
//                ToastUtils.SimpleToast("取消收藏");
                mIvHotspotCollection.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.my_collect_icon));
                break;

            case "correlateConfirm":
                mTvHotspotFollowers.setText("取消关注");
                break;

            case "correlateCancel":
                mTvHotspotFollowers.setText("关注");
                break;
        }
    }

    @Override
    public void findHotspotSuccess(HotspotBean hotspotBean) {

        String icon = hotspotBean.getPic();

        if (!TextUtils.isEmpty(icon)) {

            GlideUtils.loadImage(mContext, CommonConfig.BASE_PIC_URL + icon, mIvHotspotPic);

        }

        mTvHotspotContent.setText(hotspotBean.getContent());

        //点赞数
        mAppreciateCount = hotspotBean.getAppreciate();
        mTvHotspotAppreciateCount.setText(mAppreciateCount + "");

        //是否点赞
        boolean hasAppreciate = hotspotBean.isHasAppreciate();
        if (hasAppreciate) {
            mIvHotspotAppreciate.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.icon_appreciate_selected));
        } else {
            mIvHotspotAppreciate.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.icon_appreciate));
        }

        //是否收藏
        boolean hasCollect = hotspotBean.isHasCollect();
        if (hasCollect) {
            mIvHotspotCollection.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.my_collect_icon_selected));
        } else {
            mIvHotspotCollection.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.my_collect_icon));
        }

        //发布时间
        String createTime = hotspotBean.getCreateTime();
        try {
            String time = DateUtils.dateToString(DateUtils.stringToDate(createTime, null), null);
            mTvHotspotCreateTime.setText(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //作者信息--倘若是本人，不显示关注信息
        AuthorBean author = hotspotBean.getAuthor();

        if (author.getUsername().equals(UIUtils.getUserBean().getUsername())) {
            mLlHotspotFollowers.setVisibility(View.INVISIBLE);
        } else {
            mAuthorId = author.getId();
            //关注状态
            boolean hasCorrelate = hotspotBean.isHasCorrelate();
            if (hasCorrelate) {
                mTvHotspotFollowers.setText("取消关注");
            } else {
                mTvHotspotFollowers.setText("关注");
            }

        }

        String authorIcon = author.getIcon();

        GlideUtils.loadCircleImage(mContext, CommonConfig.BASE_PIC_URL + authorIcon, mIvHotspotAuthorIcon);


        if (TextUtils.isEmpty(author.getNickname())) {
            mTvHotspotAuthorName.setText(VerificationUtils.hidePhoneNumber(author.getUsername()));
        } else {
            mTvHotspotAuthorName.setText(author.getNickname());
        }

    }

    @Override
    public void loadMoreHotspotSuccess(List<HotspotBean> hotspotBeanList) {

    }

    @Override
    public void refreshHotspotSuccess(List<HotspotBean> hotspotBeanList) {

    }

    @Override
    public void commentSuccess() {
        ToastUtils.SimpleToast("评论内容成功");
        mPresenter.findAllCommentsByHotspotId(mHotspotId, mPage, mSize);
    }

    @Override
    public void commentReplySuccess() {
        ToastUtils.SimpleToast("回复成功");
        mPresenter.findAllCommentsByHotspotId(mHotspotId, mPage, mSize);

    }

    @Override
    public void findAllCommentsSuccess(List<CommentBean> commentBeanList) {

        mCommentBeanList.clear();
        mCommentBeanList = commentBeanList;

        System.out.println("查询所有评论 ： "+commentBeanList);

//        if(commentBeanList != null && commentBeanList.size() > 0){
//            for (int i = 0; i < commentBeanList.size(); i++) {
//                List<CommentReplyBean> replyList = new ArrayList<>();
//
//                for (int j = 0; j < 4; j++) {
//                    CommentReplyBean commentReplyBean = new CommentReplyBean();
//                    commentReplyBean.setContent("测试回复_"+j);
//                    replyList.add(commentReplyBean);
//                }
//
//                commentBeanList.get(i).setCommentReplyList(replyList);
////                mCommentListView.expandGroup(i);
//
//
//            }
//        }
//
        mCommentExpandAdapter.setNewData(commentBeanList);
        for (int i = 0; i < commentBeanList.size(); i++) {
            mCommentListView.expandGroup(i);

        }

    }

}
