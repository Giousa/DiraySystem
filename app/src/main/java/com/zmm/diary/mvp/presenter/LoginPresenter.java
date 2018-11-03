package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.mvp.presenter.contract.LoginContract;
import com.zmm.diary.utils.MD5Utils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

        mModel.login(phone, MD5Utils.encode(password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<UserBean> userBeanBaseBean) {

                        int code = userBeanBaseBean.getCode();
                        if(code == 2000){
                            UserBean data = userBeanBaseBean.getData();
                            mView.loginSuccess(data);
                        }else {
                            mView.error(userBeanBaseBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.error("服务器异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
