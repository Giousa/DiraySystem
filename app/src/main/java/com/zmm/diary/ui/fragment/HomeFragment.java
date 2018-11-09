package com.zmm.diary.ui.fragment;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.adapter.HomeAdapter;
import com.zmm.diary.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
//    @BindView(R.id.empty)
//    RelativeLayout mEmpty;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

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
        mTitleBar.setCenterTitle("今天事务处理");

        mContext = getActivity();

        initRecyclerView();
    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));

        HomeAdapter homeAdapter = new HomeAdapter(mContext);
        mRvList.setAdapter(homeAdapter);

        List<NoteBean> noteBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NoteBean noteBean = new NoteBean();
            noteBean.setTitle("标题_"+i);
            noteBean.setContent("简略内容大致如下描述_"+i);
            noteBeans.add(noteBean);
        }

        homeAdapter.setNewData(noteBeans);
    }

}
