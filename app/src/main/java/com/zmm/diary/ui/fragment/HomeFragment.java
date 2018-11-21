package com.zmm.diary.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmm.diary.MyApplication;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerNoteComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.NoteModule;
import com.zmm.diary.mvp.presenter.NotePresenter;
import com.zmm.diary.mvp.presenter.contract.NoteContract;
import com.zmm.diary.ui.activity.DiaryInfoActivity;
import com.zmm.diary.ui.adapter.HomeAdapter;
import com.zmm.diary.ui.dialog.SimpleConfirmDialog;
import com.zmm.diary.ui.widget.TitleBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class HomeFragment extends BaseFragment<NotePresenter> implements NoteContract.NoteView, HomeAdapter.OnRightMenuClickListener {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

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

        initToolBar();

        initRecyclerView();

        requestTodayNotes();

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("HomeFragment onResume");
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        System.out.println("HomeFragment onRefresh");

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

                mContext.startActivity(new Intent(mContext,DiaryInfoActivity.class));
            }
        });

    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mHomeAdapter);

        //适配器，设置空布局
        mHomeAdapter.setEmptyView(R.layout.empty_content,mRvList);


        mHomeAdapter.setOnRightMenuClickListener(this);

    }

    @Override
    public void addSuccess() {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void deleteSuccess() {
        requestTodayNotes();
    }

    @Override
    public void findNoteSuccess(NoteBean noteBean) {

    }

    @Override
    public void findNotesListSuccess(List<NoteBean> noteBeanList) {

        mHomeAdapter.setNewData(noteBeanList);

    }

    @Override
    public void onRightMenuDelete(final String id) {

        final SimpleConfirmDialog simpleConfirmDialog = new SimpleConfirmDialog(mContext,null);
        simpleConfirmDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simpleConfirmDialog.dismiss();
            }

            @Override
            public void onConfirm() {
                simpleConfirmDialog.dismiss();
                mPresenter.deleteNote(id);

            }
        });

        simpleConfirmDialog.show();

    }

    @Override
    public void onRightMenuUpdate(String id) {
        Intent intent = new Intent(mContext,DiaryInfoActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
