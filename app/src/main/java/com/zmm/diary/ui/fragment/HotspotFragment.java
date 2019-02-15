package com.zmm.diary.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmm.diary.R;
import com.zmm.diary.bean.CommentBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.dagger.component.DaggerHotspotComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.HotspotModule;
import com.zmm.diary.mvp.presenter.HotspotPresenter;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;
import com.zmm.diary.ui.activity.HotspotDetailActivity;
import com.zmm.diary.ui.activity.HotspotInfoActivity;
import com.zmm.diary.ui.adapter.HotspotAdapter;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class HotspotFragment extends BaseFragment<HotspotPresenter> implements HotspotContract.HotspotView{

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.easy_refresh_layout)
    EasyRefreshLayout mEasyRefreshLayout;


    @Inject
    HotspotAdapter mHotspotAdapter;

    private int mPage = 0;
    private int mSize = 4;

    @Override
    protected int setLayout() {
        return R.layout.fragment_hotspot;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerHotspotComponent.builder()
                .httpComponent(httpComponent)
                .hotspotModule(new HotspotModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void init() {

        initToolBar();

        initRecyclerView();

        initRefresh();

        initRefreshData();

    }

    private void initRefreshData(){
        mPage = 0;
        mPresenter.findAllHotspots(mPage, mSize,1);
    }


    private void initToolBar() {

        mTitleBar.setTitle("世界热点");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_add) {
            @Override
            public void performAction(View view) {

//                mContext.startActivity(new Intent(mContext, HotspotInfoActivity.class));
                startActivityForResult(new Intent(mContext, HotspotInfoActivity.class),1);
            }
        });

    }

    //判断，Only添加热点成功后返回，则刷新界面，否则皆不刷新
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("世界热点：：requestCode = "+requestCode+",resultCode = "+resultCode);

        if(requestCode == 1 && resultCode == 2){
            initRefreshData();
        }
    }

    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
//        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvList.setLayoutManager(new GridLayoutManager(mContext, 2));

        //添加分割线
//        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mHotspotAdapter);

        //适配器，设置空布局
        mHotspotAdapter.setEmptyView(R.layout.empty_content, mRvList);

        mHotspotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotspotBean hotspotBean = (HotspotBean) adapter.getData().get(position);

                Intent intent = new Intent(mContext, HotspotDetailActivity.class);
                intent.putExtra("hotspotId",hotspotBean.getId());
                startActivity(intent);
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

                mPresenter.findAllHotspots(mPage, mSize,0);

            }

            @Override
            public void onRefreshing() {
                System.out.println("----onRefreshing----");
                mPage = 0;

                mPresenter.findAllHotspots(mPage, mSize,1);
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
    public void appreciateOrCollectionOrAuthorStatus(String msg) {

    }

    @Override
    public void findHotspotSuccess(HotspotBean hotspotBean) {

    }

    @Override
    public void loadMoreHotspotSuccess(List<HotspotBean> hotspotBeanList) {

        if(hotspotBeanList.size() > 0){
            for (int i = 0; i < hotspotBeanList.size(); i++) {

                try {
                    mHotspotAdapter.addData(mPage*mSize+i ,hotspotBeanList.get(i));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        mEasyRefreshLayout.loadMoreComplete();
    }

    @Override
    public void refreshHotspotSuccess(List<HotspotBean> hotspotBeanList) {
        mHotspotAdapter.setNewData(hotspotBeanList);
        mEasyRefreshLayout.refreshComplete();

    }

    @Override
    public void commentSuccess() {

    }

    @Override
    public void commentReplySuccess() {

    }

    @Override
    public void findAllCommentsSuccess(List<CommentBean> commentBeanList) {

    }
}
