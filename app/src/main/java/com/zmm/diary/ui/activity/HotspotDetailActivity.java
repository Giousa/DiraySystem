package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/27
 * Email:65489469@qq.com
 */
public class HotspotDetailActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
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
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.easy_refresh_layout)
    EasyRefreshLayout mEasyRefreshLayout;


    private int mPage = 0;
    private int mSize = 4;

    @Override
    protected int setLayout() {
        return R.layout.activity_hotspot_detail;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {

        initToolBar();

        initRecyclerView();

        initRefresh();

    }

    private void initToolBar() {

        mTitleBar.setTitle("热点详情");
        mTitleBar.setLeftImageResource(R.drawable.icon_back);
        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                System.out.println("----onLoadMore----");

                mPage++;

//                mPresenter.findHotspotsByUId(UIUtils.getUserBean().getId(),mPage, mSize,0);

            }

            @Override
            public void onRefreshing() {
                System.out.println("----onRefreshing----");
                mPage = 0;

//                mPresenter.findHotspotsByUId(UIUtils.getUserBean().getId(),mPage, mSize,1);
            }
        });
    }

    @OnClick({R.id.ll_hotspot_comment, R.id.ll_hotspot_followers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_hotspot_comment:
                break;
            case R.id.ll_hotspot_followers:
                break;
        }
    }
}
