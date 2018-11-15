package com.zmm.diary.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.activity.DiaryInfoActivity;
import com.zmm.diary.ui.adapter.HomeAdapter;
import com.zmm.diary.ui.widget.ItemDecoration;
import com.zmm.diary.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class NoteFragment extends BaseFragment {

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

                mContext.startActivity(new Intent(mContext,DiaryInfoActivity.class));
            }
        });

    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRvList.addItemDecoration(new ItemDecoration(mContext,100));

        HomeAdapter homeAdapter = new HomeAdapter();
        mRvList.setAdapter(homeAdapter);

        List<NoteBean> noteBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NoteBean noteBean = new NoteBean();
            noteBean.setType("公");
            noteBean.setTitle("标题_"+i);
            noteBean.setContent("内容详情大致如下_"+i);

            noteBeanList.add(noteBean);
        }

        homeAdapter.setNewData(noteBeanList);

    }


}
