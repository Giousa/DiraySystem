package com.zmm.diary.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.activity.LoginActivity;
import com.zmm.diary.ui.activity.MainActivity;
import com.zmm.diary.ui.adapter.HomeAdapter;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.config.CommonConfig;

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
public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private Context mContext;

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
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

        mTitleBar.setTitle("今日备忘录");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.add) {
            @Override
            public void performAction(View view) {
                ToastUtils.SimpleToast("添加");
            }
        });

    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        HomeAdapter homeAdapter = new HomeAdapter(mContext);
        mRvList.setAdapter(homeAdapter);

        List<NoteBean> noteBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NoteBean noteBean = new NoteBean();
            noteBean.setTitle("标题_" + i);
            noteBean.setContent("简略内容大致如下描述_" + i);
            noteBeans.add(noteBean);
        }

        homeAdapter.setNewData(noteBeans);

//        homeAdapter.setEmptyView(R.layout.empty_content,mRvList);
    }
}
