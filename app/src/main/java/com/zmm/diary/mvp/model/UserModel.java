package com.zmm.diary.mvp.model;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.presenter.contract.UserContract;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public class UserModel implements UserContract.IUserModel {

    private ApiService mApiService;

    public UserModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<UserBean>> uploadIcon(String id, MultipartBody.Part file) {
        return mApiService.uploadIcon(id,file);
    }

    @Override
    public Observable<BaseBean<UserBean>> findUserById(String id) {
        return mApiService.findUserById(id);
    }
}
