package com.zmm.diary.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmm.diary.R;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.dagger.component.DaggerRecordComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.RecordModule;
import com.zmm.diary.mvp.presenter.RecordPresenter;
import com.zmm.diary.mvp.presenter.contract.RecordContract;
import com.zmm.diary.ui.activity.RecordInfoActivity;
import com.zmm.diary.ui.widget.TitleBar;

import java.util.List;

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

    private Context mContext;


    @Override
    protected int setLayout() {
        return R.layout.fragment_note;
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

        mContext = getActivity();

        initToolBar();

        initRecyclerView();

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

    }


    @Override
    public void addSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void findAllRecordsSuccess(List<RecordBean> recordBeanList) {

    }
}
