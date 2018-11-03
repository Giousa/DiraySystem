package com.zmm.diary.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.zmm.diary.mvp.view.BaseView;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/6
 * Time:上午11:09
 */

public class BasePresenter<M,V extends BaseView> {

    protected M mModel;
    protected V mView;

    protected Context mContext;

    public BasePresenter(M model, V view) {
        mModel = model;
        mView = view;

        initContext();
    }

    private void initContext(){

        if(mView instanceof Fragment){
            mContext = ((Fragment) mView).getActivity();
        }else {
            mContext = (Activity) mView;
        }
    }
}
