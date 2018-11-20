package com.zmm.diary.mvp.presenter;

import com.zmm.diary.mvp.presenter.contract.UserContract;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/20
 * Email:65489469@qq.com
 */
public class UserPresenter extends BasePresenter<UserContract.IUserModel,UserContract.UserView>{


    @Inject
    public UserPresenter(UserContract.IUserModel model, UserContract.UserView view) {
        super(model, view);
    }
}
