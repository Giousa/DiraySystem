package com.zmm.diary.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.widget.CustomItemView;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.custom_item_setting)
    CustomItemView mCustomItemSetting;

    @Override
    protected int setLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }


    @Override
    protected void init() {

        mTitleBar.setTitle("我");


        mCustomItemSetting.setOnItemClickListener(new CustomItemView.OnItemClickListener() {
            @Override
            public void OnItemClick(String title) {
                ToastUtils.SimpleToast(title);
            }
        });

    }
}
