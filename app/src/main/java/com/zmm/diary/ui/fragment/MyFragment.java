package com.zmm.diary.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zmm.diary.MyApplication;
import com.zmm.diary.R;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerUserComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.UserModule;
import com.zmm.diary.mvp.presenter.UserPresenter;
import com.zmm.diary.mvp.presenter.contract.UserContract;
import com.zmm.diary.ui.widget.CustomItemView;
import com.zmm.diary.ui.widget.GlideCircleTransform;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.config.CommonConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class MyFragment extends BaseFragment<UserPresenter> implements CustomItemView.OnItemClickListener,UserContract.UserView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.iv_head_icon)
    ImageView mIvHeadIcon;
    @BindView(R.id.custom_item_setting)
    CustomItemView mCustomItemSetting;
    @BindView(R.id.custom_item_info)
    CustomItemView mCustomItemInfo;
    @BindView(R.id.custom_item_spend)
    CustomItemView mCustomItemSpend;

    @Override
    protected int setLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerUserComponent.builder()
                .httpComponent(httpComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void init() {

        mTitleBar.setTitle("我");


        mCustomItemInfo.setOnItemClickListener(this);
        mCustomItemSpend.setOnItemClickListener(this);
        mCustomItemSetting.setOnItemClickListener(this);

        UserBean userBean = MyApplication.userBean;

        if(userBean != null && TextUtils.isEmpty(userBean.getIcon())){
            Glide.with(mContext)
                    .load(CommonConfig.BASE_PIC_URL + userBean.getIcon())
                    .transform(new GlideCircleTransform(mContext))
                    .into(mIvHeadIcon);

        }

    }


    @Override
    public void OnItemClick(String title) {

    }

    @Override
    public void updateSuccess(UserBean userBean) {

    }
}
