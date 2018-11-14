package com.zmm.diary.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zmm.diary.MyApplication;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerNoteComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.NoteModule;
import com.zmm.diary.mvp.presenter.NotePresenter;
import com.zmm.diary.mvp.presenter.contract.NoteContract;
import com.zmm.diary.ui.activity.AddDiaryActivity;
import com.zmm.diary.ui.activity.LoginActivity;
import com.zmm.diary.ui.activity.MainActivity;
import com.zmm.diary.ui.adapter.HomeAdapter;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class HomeFragment extends BaseFragment<NotePresenter> implements NoteContract.NoteView {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private Context mContext;

    @Inject
    HomeAdapter mHomeAdapter;

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerNoteComponent.builder()
                .httpComponent(httpComponent)
                .noteModule(new NoteModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void init() {

        mContext = getActivity();

        initToolBar();

        initRecyclerView();


        requestTodayNotes();

    }

    private void requestTodayNotes() {

        UserBean userBean = MyApplication.userBean;

        if(userBean != null){
            mPresenter.requestTodayNotes(userBean.getId());
        }
    }

    private void initToolBar() {

        mTitleBar.setTitle("今日备忘录");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.add) {
            @Override
            public void performAction(View view) {

                mContext.startActivity(new Intent(mContext,AddDiaryActivity.class));
            }
        });

    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

//        mHomeAdapter = new HomeAdapter(mContext);
        mRvList.setAdapter(mHomeAdapter);

//        List<NoteBean> noteBeans = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            NoteBean noteBean = new NoteBean();
//            noteBean.setTitle("标题_" + i);
//            noteBean.setContent("简略内容大致如下描述_" + i);
//            noteBeans.add(noteBean);
//        }
//
//        mHomeAdapter.setNewData(noteBeans);

//        homeAdapter.setEmptyView(R.layout.empty_content,mRvList);
    }

    @Override
    public void addSuccess() {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void findNoteSuccess(NoteBean noteBean) {

    }

    @Override
    public void findTodayNotesSuccess(List<NoteBean> noteBeanList) {
        mHomeAdapter.setNewData(noteBeanList);
    }

}
