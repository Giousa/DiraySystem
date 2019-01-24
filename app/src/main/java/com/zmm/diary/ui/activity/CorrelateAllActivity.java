package com.zmm.diary.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.zmm.diary.R;
import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerCorrelateComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.CorrelateModule;
import com.zmm.diary.mvp.presenter.CorrelatePresenter;
import com.zmm.diary.mvp.presenter.contract.CorrelateContract;
import com.zmm.diary.ui.adapter.CorrelateAdapter;
import com.zmm.diary.ui.dialog.SimpleRemoveConfirmDialog;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description: 推荐关注
 * Author:zhangmengmeng
 * Date:2019/1/2
 * Email:65489469@qq.com
 */
public class CorrelateAllActivity extends BaseActivity<CorrelatePresenter> implements CorrelateContract.CorrelateView {


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
    private String mUserId;

    @Override
    protected int setLayout() {
        return R.layout.activity_follow;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerCorrelateComponent.builder()
                .httpComponent(httpComponent)
                .correlateModule(new CorrelateModule(this,1))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {


        UserBean userBean = UIUtils.getUserBean();
        mUserId = userBean.getId();

        initToolBar();

        initRecyclerView();

        initRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshData();
    }


    private void refreshData(){
        mPage = 0;

        mPresenter.findAllFollowers(UIUtils.getUserBean().getId(),mPage, mSize,1);

    }

    private void initToolBar() {

        mTitleBar.setTitle("推荐关注");

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

        mCorrelateAdapter.setOnCorrelateStatusClickListener(new CorrelateAdapter.OnCorrelateStatusClickListener() {
            @Override
            public void OnCorrelateStatus(final String id,String username,boolean attention) {

                if(!attention){
                    mPresenter.correlateAuthor(mUserId,id);
                }else {
                    ToastUtils.SimpleToast("当前用户，已关注");
                }


//                final SimpleRemoveConfirmDialog simpleConfirmDialog = new SimpleRemoveConfirmDialog(mContext,"是否取消关注？",username);
//                simpleConfirmDialog.setOnClickListener(new SimpleRemoveConfirmDialog.OnClickListener() {
//                    @Override
//                    public void onCancel() {
//                        simpleConfirmDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onConfirm() {
//                        mPresenter.correlateAuthor(mUserId,id);
//                        simpleConfirmDialog.dismiss();
//                    }
//                });
//
//                simpleConfirmDialog.show();
            }
        });

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
                mPresenter.findAllFollowers(UIUtils.getUserBean().getId(),mPage, mSize,0);

            }

            @Override
            public void onRefreshing() {
                System.out.println("----onRefreshing----");
                refreshData();
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
    public void correlateChangeSuccess(String msg) {
        if(msg.equals("correlateConfirm")){
            ToastUtils.SimpleToast("恭喜您，关注成功！");
            refreshData();
        }

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
