package com.zmm.diary.ui.activity;

import android.view.View;

import com.zmm.diary.R;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerUserComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.UserModule;
import com.zmm.diary.mvp.presenter.UserPresenter;
import com.zmm.diary.mvp.presenter.contract.UserContract;
import com.zmm.diary.ui.widget.CustomTitleItemView;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;
import com.zmm.diary.utils.config.CommonConfig;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/21
 * Email:65489469@qq.com
 */
public class SettingActivity extends BaseActivity<UserPresenter> implements UserContract.UserView,CustomTitleItemView.OnItemClickListener {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.custom_title_item_checkout)
    CustomTitleItemView mCustomTitleItemCheckout;
    @BindView(R.id.custom_title_item_signout)
    CustomTitleItemView mCustomTitleItemSignout;
    @BindView(R.id.custom_title_item_delete)
    CustomTitleItemView mCustomTitleItemDelete;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
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
        initToolBar();

        mCustomTitleItemCheckout.setOnItemClickListener(this);
        mCustomTitleItemSignout.setOnItemClickListener(this);
        mCustomTitleItemDelete.setOnItemClickListener(this);
    }

    private void initToolBar() {

        mTitleBar.setTitle("设置");
        mTitleBar.setLeftImageResource(R.drawable.icon_back);
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


        if (title.equals("切换账号")) {
            SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER, null);
            startActivity(LoginActivity.class, true);
        } else if(title.equals("退出登录")){
            SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER, null);
            removeAllActivity();
        } else {
            UserBean userBean = UIUtils.getUserBean();
            if(userBean != null){
                mPresenter.deleteUserById(userBean.getId());
            }
        }
    }

    @Override
    public void updateSuccess(UserBean userBean) {

    }

    @Override
    public void deleteSuccess() {
        ToastUtils.SimpleToast("账户删除成功");
        SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER, null);
        startActivity(LoginActivity.class, true);
    }
}
