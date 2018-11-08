package com.zmm.diary.mvp.model;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.presenter.contract.RegisterContract;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class RegisterModel implements RegisterContract.IRegisterModel{

    private ApiService mApiService;

    public RegisterModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<String>> getVerifyCode(String phone) {
        return mApiService.getVerifyCode(phone);
    }

    @Override
    public Observable<BaseBean<UserBean>> register(String phone, String password, String code) {
        return mApiService.register(phone,password,code);
    }

    @Override
    public Observable<BaseBean<String>> modifyPassword(String phone, String newPassword,String code) {
        return mApiService.modifyPassword(phone,newPassword,code);
    }
}
