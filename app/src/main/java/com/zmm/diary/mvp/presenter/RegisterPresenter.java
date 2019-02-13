package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.UserBean;
import com.zmm.diary.mvp.presenter.contract.RegisterContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterModel,RegisterContract.RegisterView>{

    @Inject
    public RegisterPresenter(RegisterContract.IRegisterModel model, RegisterContract.RegisterView view) {
        super(model, view);
    }

    public void getVerifyCode(String phoneStr) {

        mModel.getVerifyCode(phoneStr)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.getVerifyCode(s);
                    }
                });
    }

    public void register(String phone, String password, String verifyCode) {

        mModel.register(phone,password,verifyCode)
                .compose(RxHttpResponseCompat.<UserBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mView.registerSuccess(userBean);
                    }
                });
    }

    public void resetPassword(String phone, String password, String verifyCode) {

        mModel.resetPassword(phone,password,verifyCode)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.modifyPasswordSuccess(s);
                    }
                });
    }
}
