package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.zmm.diary.utils.VerificationUtils;

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
    private String mUserId;

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

                    mContext.startActivity(new Intent(mContext, CorrelateAllActivity.class));
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

        mCorrelateAdapter.setOnCorrelateStatusClickListener(new CorrelateAdapter.OnCorrelateStatusClickListener() {
            @Override
            public void OnCorrelateStatus(final String id,String username) {

                final SimpleRemoveConfirmDialog simpleConfirmDialog = new SimpleRemoveConfirmDialog(mContext,"是否取消关注？",username);
                simpleConfirmDialog.setOnClickListener(new SimpleRemoveConfirmDialog.OnClickListener() {
                    @Override
                    public void onCancel() {
                        simpleConfirmDialog.dismiss();
                    }

                    @Override
                    public void onConfirm() {
                        mPresenter.correlateAuthor(mUserId,id);
                        simpleConfirmDialog.dismiss();
                    }
                });

                simpleConfirmDialog.show();
            }
        });

        mCorrelateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CorrelateBean correlateBean = (CorrelateBean) adapter.getData().get(position);
                Intent intentHotspot = new Intent(mContext,HotspotActivity.class);
                intentHotspot.putExtra("type",0);
                intentHotspot.putExtra("followId",correlateBean.getId());
                if(TextUtils.isEmpty(correlateBean.getNickname())){
                    intentHotspot.putExtra("followName", VerificationUtils.hidePhoneNumber(correlateBean.getUsername()));
                }else {
                    intentHotspot.putExtra("followName",correlateBean.getNickname());
                }
                startActivity(intentHotspot);
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

                if(mType == 0){
                    mPresenter.findFollowersByUserId(UIUtils.getUserBean().getId(),mPage, mSize,0);

                }else {

                }
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
        if(msg.equals("correlateCancel")){
            ToastUtils.SimpleToast("取消关注成功");
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
