package com.zmm.diary.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmm.diary.MyApplication;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.mvp.presenter.BasePresenter;
import com.zmm.diary.mvp.view.BaseView;
import com.zmm.diary.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/24
 * Time:下午1:25
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {



    private Unbinder mUnbinder;
    private MyApplication mMyApplication;
    private View mRootView;


    protected Context mContext;

    @Inject
    T mPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(setLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);

        return mRootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMyApplication = (MyApplication) getActivity().getApplication();
        setupActivityComponent(mMyApplication.getHttpComponent());

        mContext = getActivity();

        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }

    //注意，Fragment切换时，走这个方法，不走onResume
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            //隐藏时调用
            System.out.println("Fragment Hide");
            onHide();
        }else {
            //再次显示时调用（第一次不会调用）
            System.out.println("Fragment show");
            onRefresh();
        }
    }


    protected void startActivity(Class activity) {

        Intent intent = new Intent(mContext, activity);
        startActivity(intent);
    }

    protected abstract int setLayout();

    protected abstract void setupActivityComponent(HttpComponent httpComponent);

    protected abstract void init();

    protected void onHide(){

    }

    protected void onRefresh(){

    }

}
