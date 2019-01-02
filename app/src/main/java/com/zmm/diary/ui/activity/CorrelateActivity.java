package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.zmm.diary.R;
import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.dagger.component.DaggerCorrelateComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.CorrelateModule;
import com.zmm.diary.mvp.presenter.CorrelatePresenter;
import com.zmm.diary.mvp.presenter.contract.CorrelateContract;
import com.zmm.diary.ui.adapter.CorrelateAdapter;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description: 关注和粉丝界面
 * Author:zhangmengmeng
 * Date:2019/1/2
 * Email:65489469@qq.com
 */
public class CorrelateActivity extends BaseActivity<CorrelatePresenter> implements CorrelateContract.CorrelateView {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.easy_refresh_layout)
    EasyRefreshLayout mEasyRefreshLayout;

    @Inject
    CorrelateAdapter mCorrelateAdapter;

    private int mPage = 0;
    private int mSize = 4;
    private int mType;//0:关注  1:粉丝

    @Override
    protected int setLayout() {
        return R.layout.activity_follow;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerCorrelateComponent.builder()
                .httpComponent(httpComponent)
                .correlateModule(new CorrelateModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        mType = getIntent().getIntExtra("type",0);

        initToolBar();

        initRecyclerView();

        initRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 0;

        if(mType == 0){
            mPresenter.findFollowersByUserId(UIUtils.getUserBean().getId(),mPage, mSize,1);

        }else {
        }
    }

    private void initToolBar() {

        if(mType == 0){
            mTitleBar.setTitle("关注成员");

            mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_add) {
                @Override
                public void performAction(View view) {

                    mContext.startActivity(new Intent(mContext, HotspotInfoActivity.class));
                }
            });
        }else {
            mTitleBar.setTitle("我的粉丝");
        }
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

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mCorrelateAdapter);

        //适配器，设置空布局
        mCorrelateAdapter.setEmptyView(R.layout.empty_content,mRvList);

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

                if(mType == 0){
                    mPresenter.findFollowersByUserId(UIUtils.getUserBean().getId(),mPage, mSize,0);

                }else {
                }
            }

            @Override
            public void onRefreshing() {
                System.out.println("----onRefreshing----");
                mPage = 0;
                if(mType == 0){
                    mPresenter.findFollowersByUserId(UIUtils.getUserBean().getId(),mPage, mSize,1);

                }else {
                }
            }
        });
    }

    @Override
    public void findFollowersSuccess(List<CorrelateBean> correlateBeanList) {

    }

    @Override
    public void findFunsSuccess(List<CorrelateBean> correlateBeanList) {

    }

    @Override
    public void deleteFollowerSuccess(String msg) {

    }

    @Override
    public void loadMoreCorrelateSuccess(List<CorrelateBean> correlateBeanList) {
        if(correlateBeanList.size() > 0){
            for (int i = 0; i < correlateBeanList.size(); i++) {

                try {
                    mCorrelateAdapter.addData(mPage*mSize+i ,correlateBeanList.get(i));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        mEasyRefreshLayout.loadMoreComplete();
    }

    @Override
    public void refreshCorrelateSuccess(List<CorrelateBean> correlateBeanList) {
        mCorrelateAdapter.setNewData(correlateBeanList);
        mEasyRefreshLayout.refreshComplete();
    }
}
