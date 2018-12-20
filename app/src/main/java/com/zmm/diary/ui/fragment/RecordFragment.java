package com.zmm.diary.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.zmm.diary.R;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerRecordComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.RecordModule;
import com.zmm.diary.mvp.presenter.RecordPresenter;
import com.zmm.diary.mvp.presenter.contract.RecordContract;
import com.zmm.diary.ui.activity.RecordInfoActivity;
import com.zmm.diary.ui.adapter.RecordAdapter;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class RecordFragment extends BaseFragment<RecordPresenter> implements RecordContract.RecordView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.easy_refresh_layout)
    EasyRefreshLayout mEasyRefreshLayout;


    @Inject
    RecordAdapter mRecordAdapter;

    private int mPage = 0;
    private int mSize = 3;
    private String mUserId;

    @Override
    protected int setLayout() {
        return R.layout.fragment_record;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerRecordComponent.builder()
                .httpComponent(httpComponent)
                .recordModule(new RecordModule(this))
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
    public void onResume() {
        super.onResume();

        String userJson = SharedPreferencesUtil.getString(CommonConfig.LOGIN_USER, null);
        UserBean userBean = SharedPreferencesUtil.fromJson(userJson, UserBean.class);
        mUserId = userBean.getId();


        mPage = 0;
        mPresenter.findAllRecords(mUserId, mPage, mSize,1);
    }

    private void initToolBar() {

        mTitleBar.setTitle("笔记");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.add) {
            @Override
            public void performAction(View view) {

                mContext.startActivity(new Intent(mContext, RecordInfoActivity.class));
            }
        });

    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mRecordAdapter);

        //适配器，设置空布局
        mRecordAdapter.setEmptyView(R.layout.empty_content, mRvList);

        //


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

                mPresenter.findAllRecords(mUserId, mPage, mSize,0);

            }

            @Override
            public void onRefreshing() {
                System.out.println("----onRefreshing----");
                mPage = 0;

                mPresenter.findAllRecords(mUserId, mPage, mSize,1);
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
    public void loadMoreRecordsSuccess(List<RecordBean> recordBeanList) {

        if(recordBeanList.size() > 0){
            for (int i = 0; i < recordBeanList.size(); i++) {

                try {
                    mRecordAdapter.addData(mPage*mSize+i ,recordBeanList.get(i));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        mEasyRefreshLayout.loadMoreComplete();

    }

    @Override
    public void refreshRecordsSuccess(List<RecordBean> recordBeanList) {
        mRecordAdapter.setNewData(recordBeanList);
        mEasyRefreshLayout.refreshComplete();
    }


}
