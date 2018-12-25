package com.zmm.diary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zmm.diary.R;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerUserComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.UserModule;
import com.zmm.diary.mvp.presenter.UserPresenter;
import com.zmm.diary.mvp.presenter.contract.UserContract;
import com.zmm.diary.ui.activity.RecordActivity;
import com.zmm.diary.ui.activity.SettingActivity;
import com.zmm.diary.ui.widget.CustomItemView;
import com.zmm.diary.ui.widget.GlideCircleTransform;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class MyFragment extends BaseFragment<UserPresenter> implements CustomItemView.OnItemClickListener, UserContract.UserView {

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
    @BindView(R.id.custom_item_record)
    CustomItemView mCustomItemRecord;
    @BindView(R.id.custom_item_hotspot)
    CustomItemView mCustomItemHotspot;

    @BindView(R.id.tv_my_releses)
    TextView mTvMyReleses;
    @BindView(R.id.tv_my_funs)
    TextView mTvMyFuns;

    private ArrayList<ImageItem> mImages;
    private String mUserId;


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
        mCustomItemRecord.setOnItemClickListener(this);
        mCustomItemHotspot.setOnItemClickListener(this);

        String userJson = SharedPreferencesUtil.getString(CommonConfig.LOGIN_USER, null);

        if (!TextUtils.isEmpty(userJson)) {

            UserBean userBean = SharedPreferencesUtil.fromJson(userJson, UserBean.class);
            mUserId = userBean.getId();
            String icon = userBean.getIcon();

            if (!TextUtils.isEmpty(icon)) {
                Glide.with(mContext)
                        .load(CommonConfig.BASE_PIC_URL + userBean.getIcon())
                        .placeholder(R.drawable.default_bg)
                        .error(R.drawable.default_bg)
                        .transform(new GlideCircleTransform(mContext))
                        .into(mIvHeadIcon);
            }

            mPresenter.findUserById(mUserId);
        }

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected void onRefresh() {
        super.onRefresh();

        if (!TextUtils.isEmpty(mUserId)) {
            mPresenter.findUserById(mUserId);
        }

    }

    @Override
    public void OnItemClick(String title) {
        if (title.equals("个人详情")) {
            ToastUtils.SimpleToast("个人详情");
        } else if (title.equals("个人消费")) {
            ToastUtils.SimpleToast("个人消费");
        } else if (title.equals("点滴记录")) {
            startActivity(RecordActivity.class);
        } else if (title.equals("热点中心")) {
            ToastUtils.SimpleToast("热点中心");
        } else if (title.equals("设置")) {
            startActivity(SettingActivity.class);
        }
    }


    @OnClick(R.id.iv_head_icon)
    public void onViewClicked() {

        ImagePicker.getInstance().setMultiMode(false);
        ImagePicker.getInstance().setCrop(true);
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {

                mImages = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (mImages != null && mImages.size() > 0) {

                    System.out.println("选择图片：" + mImages.get(0).path);

                    if (!TextUtils.isEmpty(mUserId)) {
                        mPresenter.uploadPic(mUserId, mImages.get(0).path);
                    } else {
                        ToastUtils.SimpleToast("请登录");
                    }
                }

            } else {
                System.out.println("没有数据");
            }
        }
    }


    @Override
    public void updateSuccess(UserBean userBean) {

        if (userBean != null) {

            SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER, SharedPreferencesUtil.toJson(userBean));

            String icon = userBean.getIcon();

            if (!TextUtils.isEmpty(icon)) {
                Glide.with(mContext)
                        .load(CommonConfig.BASE_PIC_URL + userBean.getIcon())
                        .placeholder(R.drawable.default_bg)
                        .error(R.drawable.default_bg)
                        .transform(new GlideCircleTransform(mContext))
                        .into(mIvHeadIcon);
            }

            mTvMyReleses.setText(userBean.getReleases() + "");
            mTvMyFuns.setText(userBean.getFuns() + "");


        }
    }

}
