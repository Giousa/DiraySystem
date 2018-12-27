package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.zmm.diary.R;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.dagger.component.DaggerHotspotComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.HotspotModule;
import com.zmm.diary.mvp.presenter.HotspotPresenter;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;
import com.zmm.diary.ui.adapter.HotspotAdapter;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/27
 * Email:65489469@qq.com
 */
public class HotspotActivity extends BaseActivity<HotspotPresenter> implements HotspotContract.HotspotView {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.easy_refresh_layout)
    EasyRefreshLayout mEasyRefreshLayout;

    @Inject
    HotspotAdapter mHotspotAdapter;

    private int mPage = 0;
    private int mSize = 4;

    @Override
    protected int setLayout() {
        return R.layout.activity_hotspot;
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
        initToolBar();

        initRecyclerView();

        initRefresh();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 0;
        mPresenter.findHotspotsByUId(UIUtils.getUserBean().getId(),mPage, mSize,1);
    }

    private void initToolBar() {

        mTitleBar.setTitle("我的热点");
        mTitleBar.setLeftImageResource(R.drawable.icon_back);
        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_add) {
            @Override
            public void performAction(View view) {

                mContext.startActivity(new Intent(mContext, HotspotInfoActivity.class));
            }
        });

    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRvList.setAdapter(mHotspotAdapter);
        mHotspotAdapter.setEmptyView(R.layout.empty_content, mRvList);

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

                mPresenter.findHotspotsByUId(UIUtils.getUserBean().getId(),mPage, mSize,0);

            }

            @Override
            public void onRefreshing() {
                System.out.println("----onRefreshing----");
                mPage = 0;

                mPresenter.findHotspotsByUId(UIUtils.getUserBean().getId(),mPage, mSize,1);
            }
        });
    }

    @Override
    public void addSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void loadMoreHotspotSuccess(List<HotspotBean> hotspotBeanList) {
        if(hotspotBeanList.size() > 0){
            for (int i = 0; i < hotspotBeanList.size(); i++) {

                try {
                    mHotspotAdapter.addData(mPage*mSize+i ,hotspotBeanList.get(i));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        mEasyRefreshLayout.loadMoreComplete();
    }

    @Override
    public void refreshHotspotSuccess(List<HotspotBean> hotspotBeanList) {
        mHotspotAdapter.setNewData(hotspotBeanList);
        mEasyRefreshLayout.refreshComplete();
    }
}
