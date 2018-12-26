package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zmm.diary.R;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.widget.CustomItemView;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/26
 * Email:65489469@qq.com
 */
public class UserInfoActivity extends BaseActivity implements CustomItemView.OnItemClickListener {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.custom_item_nickname)
    CustomItemView mCustomItemNickname;
    @BindView(R.id.custom_item_gender)
    CustomItemView mCustomItemGender;
    @BindView(R.id.custom_item_sign)
    CustomItemView mCustomItemSign;
    @BindView(R.id.custom_item_height)
    CustomItemView mCustomItemHeight;
    @BindView(R.id.custom_item_weight)
    CustomItemView mCustomItemWeight;
    @BindView(R.id.custom_item_birthday)
    CustomItemView mCustomItemBirthday;
    @BindView(R.id.root_view)
    LinearLayout mRootView;


    private UserBean mUserBean;

    @Override
    protected int setLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {


        initToolBar();

        initCustomItemListener();

        initData();

    }


    private void initToolBar() {
        mTitleBar.setTitle("个人信息");
        mTitleBar.setLeftImageResource(R.drawable.icon_back);
        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftTextColor(UIUtils.getResources().getColor(R.color.white));
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleBar.setActionTextColor(UIUtils.getResources().getColor(R.color.white));
        mTitleBar.addAction(new TitleBar.TextAction("更新") {
            @Override
            public void performAction(View view) {
                ToastUtils.SimpleToast("更新消息");
            }
        });

    }

    private void initCustomItemListener() {
        mCustomItemNickname.setOnItemClickListener(this);
        mCustomItemGender.setOnItemClickListener(this);
        mCustomItemSign.setOnItemClickListener(this);
        mCustomItemHeight.setOnItemClickListener(this);
        mCustomItemWeight.setOnItemClickListener(this);
        mCustomItemBirthday.setOnItemClickListener(this);
    }


    private void initData() {

        mUserBean = UIUtils.getUserBean();

    }


    @Override
    public void OnItemClick(String title) {

        switch (title){

            case "昵称":
                inputString(title,"请输入昵称",mUserBean.getNickname());
                break;

            case "性别":

                break;

            case "签名":
                inputString(title,"请输入签名",mUserBean.getSign());

                break;

            case "身高":

                break;

            case "体重":

                break;

            case "生日":

                break;
        }
    }

    private void inputString(String title, String hint, String name) {

        

    }
}
