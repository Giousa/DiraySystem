package com.zmm.diary.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.zmm.diary.R;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerRecordComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.RecordModule;
import com.zmm.diary.mvp.presenter.RecordPresenter;
import com.zmm.diary.mvp.presenter.contract.RecordContract;
import com.zmm.diary.ui.activity.LoginActivity;
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

    @Inject
    RecordAdapter mRecordAdapter;

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

    }

    @Override
    public void onResume() {
        super.onResume();

        String userJson = SharedPreferencesUtil.getString(CommonConfig.LOGIN_USER, null);
        UserBean userBean = SharedPreferencesUtil.fromJson(userJson, UserBean.class);


        mPresenter.findAllRecords(userBean.getId(),0,10);
    }

    private void initToolBar() {

        mTitleBar.setTitle("笔记");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.add) {
            @Override
            public void performAction(View view) {

                mContext.startActivity(new Intent(mContext,RecordInfoActivity.class));
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
        mRecordAdapter.setEmptyView(R.layout.empty_content,mRvList);

    }


    @Override
    public void addSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void findAllRecordsSuccess(List<RecordBean> recordBeanList) {
        mRecordAdapter.setNewData(recordBeanList);
    }
}
