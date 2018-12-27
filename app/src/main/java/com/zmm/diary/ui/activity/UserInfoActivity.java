package com.zmm.diary.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.zmm.diary.R;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerUserComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.UserModule;
import com.zmm.diary.mvp.presenter.UserPresenter;
import com.zmm.diary.mvp.presenter.contract.UserContract;
import com.zmm.diary.ui.dialog.SimpleInputDialog;
import com.zmm.diary.ui.widget.CustomItemView;
import com.zmm.diary.ui.widget.DateSelectView;
import com.zmm.diary.ui.widget.SingleSelectView;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/26
 * Email:65489469@qq.com
 */
public class UserInfoActivity extends BaseActivity<UserPresenter> implements UserContract.UserView,CustomItemView.OnItemClickListener {


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
    private List<String> mList = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_user_info;
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
                mPresenter.updateUser(mUserBean);
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

        mCustomItemNickname.setContent(mUserBean.getNickname());
        mCustomItemGender.setContent(mUserBean.getGender());
        mCustomItemSign.setContent(mUserBean.getSign());
        mCustomItemHeight.setContent((mUserBean.getHeight() == null) ? "":mUserBean.getHeight()+"");
        mCustomItemWeight.setContent((mUserBean.getWeight() == null) ? "":mUserBean.getWeight()+"");
        mCustomItemBirthday.setContent(mUserBean.getBirthday());

    }




    @Override
    public void OnItemClick(String title) {

        switch (title){

            case "昵称":
                inputString(title,"请输入昵称",mUserBean.getNickname());
                break;

            case "性别":

                mList.clear();
                mList.add("女");
                mList.add("男");

                String contentGender = mCustomItemGender.getContent();

                int indexGender = 0;
                if(!TextUtils.isEmpty(contentGender)){
                    indexGender = mList.indexOf(contentGender);
                }

                selectString(title, mList,indexGender);

                break;

            case "签名":
                inputString(title,"请输入签名",mUserBean.getSign());

                break;

            case "身高/cm":

                mList.clear();
                for (int i = 100; i <= 250; i+= 5) {
                    mList.add(i+"");
                }

                String contentHeight = mCustomItemHeight.getContent();

                int indexHeight = 0;
                if(!TextUtils.isEmpty(contentHeight)){
                    indexHeight = mList.indexOf(contentHeight);
                }

                selectString(title, mList,indexHeight);

                break;

            case "体重/kg":

                mList.clear();
                for (int i = 40; i <= 200; i+=5) {
                    mList.add(i+"");
                }

                String contentWeight = mCustomItemWeight.getContent();

                int indexWeight = 0;
                if(!TextUtils.isEmpty(contentWeight)){
                    indexWeight = mList.indexOf(contentWeight);
                }
                selectString(title, mList,indexWeight);

                break;

            case "生日":

                selectBirthday(mCustomItemBirthday.getContent());

                break;
        }
    }

    private void inputString(final String title, String hint, String name) {

        final SimpleInputDialog simpleInputDialog = new SimpleInputDialog(mContext,title,hint,name);

        simpleInputDialog.setOnClickListener(new SimpleInputDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simpleInputDialog.dismiss();
            }

            @Override
            public void onConfirm(String content) {

                switch (title){
                    case "昵称":
                        mUserBean.setNickname(content);
                        mCustomItemNickname.setContent(content);

                        break;

                    case "签名":
                        mUserBean.setSign(content);
                        mCustomItemSign.setContent(content);

                        break;
                }
                simpleInputDialog.dismiss();
            }
        });

        simpleInputDialog.show();

    }


    private void selectString(final String title, List<String> list,int index) {

        SingleSelectView singleSelectView = new SingleSelectView(mContext,mRootView,mScreenWidth,title,list,index);

        singleSelectView.setOnSelectClickListener(new SingleSelectView.OnSelectClickListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm(String content) {

                switch (title){
                    case "性别":

                        if(!TextUtils.isEmpty(content)){
                            mUserBean.setGender(content);
                            mCustomItemGender.setContent(content);
                        }
                        break;

                    case "身高/cm":

                        if(!TextUtils.isEmpty(content)){
                            mUserBean.setHeight(Integer.parseInt(content));
                            mCustomItemHeight.setContent(content+"");
                        }

                        break;

                    case "体重/kg":

                        if(!TextUtils.isEmpty(content)){
                            mUserBean.setWeight(Integer.parseInt(content));
                            mCustomItemWeight.setContent(content+"");
                        }


                        break;

                }

            }
        });

    }


    private void selectBirthday(String birthday) {

        DateSelectView dateSelectView = new DateSelectView(mContext,mRootView,mScreenWidth,birthday);

        dateSelectView.setOnDateClickListener(new DateSelectView.OnDateClickListener() {
            @Override
            public void onDateClick(String date) {
                mUserBean.setBirthday(date);
                mCustomItemBirthday.setContent(date);
            }
        });
    }


    @Override
    public void updateSuccess(UserBean userBean) {

        SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER,SharedPreferencesUtil.toJson(userBean));

        ToastUtils.SimpleToast("用户信息更新成功");

        finish();

    }
}
