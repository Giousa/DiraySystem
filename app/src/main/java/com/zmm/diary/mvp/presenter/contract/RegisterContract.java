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
public interface RegisterContract {


    interface IRegisterModel{

        Observable<BaseBean<String>> getVerifyCode(String phone);

        Observable<BaseBean<UserBean>> register(String phone, String password,String code);

        Observable<BaseBean<String>> resetPassword(String phone,String newPassword,String code);


    }


    interface RegisterView extends BaseView {

        void getVerifyCode(String msg);

        void registerSuccess(UserBean userBean);

        void modifyPasswordSuccess(String msg);
    }

}
