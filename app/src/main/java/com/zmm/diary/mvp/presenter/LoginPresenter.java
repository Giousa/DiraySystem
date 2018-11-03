package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.UserBean;
import com.zmm.diary.mvp.presenter.contract.LoginContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public class LoginPresenter extends BasePresenter<LoginContract.ILoginModel,LoginContract.LoginView> {

    @Inject
    public LoginPresenter(LoginContract.ILoginModel model, LoginContract.LoginView view) {
        super(model, view);
    }


    /**
     * 登录
     * @param phone
     * @param password
     */
    public void login(String phone, String password) {

        mModel.login(phone,password)
                .compose(RxHttpResponseCompat.<UserBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mView.loginSuccess(userBean);
                    }
                });
    }
}
