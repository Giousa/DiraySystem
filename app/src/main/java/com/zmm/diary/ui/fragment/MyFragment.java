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

    @BindView(R.id.iv_my_icon)
    ImageView mIvMyIcon;
    @BindView(R.id.tv_my_name)
    TextView mTvMyName;
    @BindView(R.id.tv_my_sign)
    TextView mTvMySign;
    @BindView(R.id.iv_my_info)
    ImageView mIvMyInfo;
    @BindView(R.id.tv_my_hotspot_count)
    TextView mTvMyHotspotCount;
    @BindView(R.id.tv_my_followers_count)
    TextView mTvMyFollowersCount;
    @BindView(R.id.tv_my_funs_count)
    TextView mTvMyFunsCount;


    @BindView(R.id.custom_item_collection)
    CustomItemView mCustomItemCollection;
    @BindView(R.id.custom_item_setting)
    CustomItemView mCustomItemSetting;
    @BindView(R.id.custom_item_record)
    CustomItemView mCustomItemRecord;


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


        mCustomItemSetting.setOnItemClickListener(this);
        mCustomItemRecord.setOnItemClickListener(this);
        mCustomItemCollection.setOnItemClickListener(this);

        String userJson = SharedPreferencesUtil.getString(CommonConfig.LOGIN_USER, null);

        if (!TextUtils.isEmpty(userJson)) {

            UserBean userBean = SharedPreferencesUtil.fromJson(userJson, UserBean.class);
            mUserId = userBean.getId();
            String icon = userBean.getIcon();

            if (!TextUtils.isEmpty(icon)) {
                Glide.with(mContext)
                        .load(CommonConfig.BASE_PIC_URL + userBean.getIcon())
                        .placeholder(R.drawable.default_my_icon)
                        .error(R.drawable.default_my_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into(mIvMyIcon);
            }

            mPresenter.findUserById(mUserId);
        }else {

            mTvMyName.setText("登录/注册");
            mTvMySign.setVisibility(View.INVISIBLE);
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


    @OnClick({R.id.iv_my_icon, R.id.iv_my_info, R.id.rel_my_hotspot, R.id.rel_my_followers, R.id.rel_my_funs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_my_icon:

                ImagePicker.getInstance().setMultiMode(false);
                ImagePicker.getInstance().setCrop(true);
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent, 100);

                break;
            case R.id.iv_my_info:

                ToastUtils.SimpleToast("个人信息");
                break;
            case R.id.rel_my_hotspot:

                ToastUtils.SimpleToast("热点界面");

                break;
            case R.id.rel_my_followers:
                ToastUtils.SimpleToast("关注界面");

                break;
            case R.id.rel_my_funs:
                ToastUtils.SimpleToast("粉丝界面");

                break;
        }
    }

    @Override
    public void OnItemClick(String title) {
        if (title.equals("热点收藏")) {
            ToastUtils.SimpleToast("热点收藏");
        } else if (title.equals("每日说说")) {
            startActivity(RecordActivity.class);
        } else if (title.equals("设置")) {
            startActivity(SettingActivity.class);
        }
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

            //头像
            String icon = userBean.getIcon();

            if (!TextUtils.isEmpty(icon)) {
                Glide.with(mContext)
                        .load(CommonConfig.BASE_PIC_URL + userBean.getIcon())
                        .placeholder(R.drawable.default_my_icon)
                        .error(R.drawable.default_my_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into(mIvMyIcon);
            }


            //名称
            if(TextUtils.isEmpty(userBean.getNickname())){
                mTvMyName.setText(userBean.getUsername());
            }else {
                mTvMyName.setText(userBean.getNickname());
            }

            //签名
            mTvMySign.setVisibility(View.VISIBLE);
            mTvMySign.setText(userBean.getSign());


            //发布热点数
            mTvMyHotspotCount.setText(userBean.getReleases() + "");

            //关注数
            mTvMyFollowersCount.setText(userBean.getFollowers()+"");

            //粉丝数
            mTvMyFunsCount.setText(userBean.getFuns() + "");


        }
    }


}
