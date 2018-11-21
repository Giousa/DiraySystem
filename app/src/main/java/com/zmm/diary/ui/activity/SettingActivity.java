package com.zmm.diary.ui.activity;

import android.view.View;

import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.widget.CustomTitleItemView;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.config.CommonConfig;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/21
 * Email:65489469@qq.com
 */
public class SettingActivity extends BaseActivity implements CustomTitleItemView.OnItemClickListener {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.custom_title_item_checkout)
    CustomTitleItemView mCustomTitleItemCheckout;
    @BindView(R.id.custom_title_item_signout)
    CustomTitleItemView mCustomTitleItemSignout;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {
        initToolBar();

        mCustomTitleItemCheckout.setOnItemClickListener(this);
        mCustomTitleItemSignout.setOnItemClickListener(this);
    }

    private void initToolBar() {

        mTitleBar.setTitle("设置");
        mTitleBar.setLeftImageResource(R.drawable.back);
        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnItemClick(String title) {


        if(title.equals("切换账号")){
            startActivity(LoginActivity.class,true);
        }else {
            SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER,null);
            removeAllActivity();
        }
    }
}
