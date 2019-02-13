package com.zmm.diary.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmm.diary.R;
import com.zmm.diary.bean.AuthorBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.dagger.component.DaggerHotspotComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.HotspotModule;
import com.zmm.diary.mvp.presenter.HotspotPresenter;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;
import com.zmm.diary.ui.widget.CommentExpandableListView;
import com.zmm.diary.utils.DateUtils;
import com.zmm.diary.utils.GlideUtils;
import com.zmm.diary.utils.UIUtils;
import com.zmm.diary.utils.VerificationUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.detail_page_do_comment)
    TextView mDetailPageDoComment;
    @BindView(R.id.comment_list_view)
    CommentExpandableListView mCommentListView;


    private int mPage = 0;
    private int mSize = 4;
    private String mHotspotId;
    private String mUserId;
    private String mAuthorId;

    private int mAppreciateCount = 0;
    private int mCollectionCount = 0;

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

        initRecyclerView();

        initRefresh();

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

    private void initRecyclerView() {

//        mRvList.setHasFixedSize(true);
//        mRvList.setLayoutManager(new GridLayoutManager(mContext, 2));
//        mRvList.setAdapter(mHotspotAdapter);
//        mHotspotAdapter.setEmptyView(R.layout.empty_content, mRvList);

    }

    /**
     * 下拉加载
     */
    private void initRefresh() {
//        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
//            @Override
//            public void onLoadMore() {
//                System.out.println("----onLoadMore----");
//
//                mPage++;
//
////                mPresenter.findHotspotsByUId(UIUtils.getUserBean().getId(),mPage, mSize,0);
//
//            }
//
//            @Override
//            public void onRefreshing() {
//                System.out.println("----onRefreshing----");
//                mPage = 0;
//
////                mPresenter.findHotspotsByUId(UIUtils.getUserBean().getId(),mPage, mSize,1);
//            }
//        });
    }

    @OnClick({R.id.ll_hotspot_comment, R.id.ll_hotspot_followers, R.id.ll_hotspot_appreciate, R.id.ll_hotspot_collection})
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
        }
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
//            Glide.with(mContext)
//                    .load(CommonConfig.BASE_PIC_URL + icon)
//                    .placeholder(R.drawable.default_bg)
//                    .error(R.drawable.default_bg)
//                    .into(mIvHotspotPic);

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

        if (author.getUsername().equals(VerificationUtils.hidePhoneNumber(UIUtils.getUserBean().getUsername()))) {
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

//        Glide.with(mContext)
//                .load(CommonConfig.BASE_PIC_URL + authorIcon)
//                .placeholder(R.drawable.default_my_icon)
//                .error(R.drawable.default_my_icon)
//                .transform(new GlideCircleTransform(mContext))
//                .into(mIvHotspotAuthorIcon);

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
}
