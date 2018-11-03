package com.zmm.diary.mvp.presenter.contract;


import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.mvp.view.BaseView;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public interface LoginContract {


    interface ILoginModel{
        Observable<BaseBean<UserBean>> login(String phone, String password);

    }


    interface LoginView extends BaseView {

        void loginSuccess(UserBean userBean);
    }

}
